package com.platventure.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.platventure.game.GlobalVariables;
import com.platventure.game.Hud;
import com.platventure.game.PlatVenture;
import com.platventure.game.entities.Joueur;
import com.platventure.game.entities.Joyau;
import com.platventure.game.entities.Joyau1;
import com.platventure.game.entities.Joyau2;
import com.platventure.game.fabriques.FabriqueObjetPhysique;
import com.platventure.game.fabriques.LevelManager;
import com.platventure.game.handlers.CustomContactListener;
import com.platventure.game.handlers.GameStateManager;
import com.platventure.game.handlers.InputManager;

import java.util.ArrayList;

public class Play extends GameState {

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private Joueur joueur;
    private ArrayList<Joyau> joyaux;
    private LevelManager levelManager;

    private Hud hud;

    private float time;

    private float[] positionJoueur;
    private float deathTime;
    private float winTime;
    private boolean win;

    private CustomContactListener ccl;
    private boolean destroyAllBodies;

    public Play(GameStateManager gsm) {
        super(gsm);

        hud = new Hud();

        world = new World(new Vector2(0, 10f), true);
        ccl = new CustomContactListener();
        world.setContactListener(ccl);

        if (GlobalVariables.DEBUG)
            box2DDebugRenderer = new Box2DDebugRenderer();

        joueur = new Joueur();
        joyaux = new ArrayList<>();
        levelManager = new LevelManager();
        destroyAllBodies = false;

        deathTime = 0;
        win = false;
        winTime = 0;
        positionJoueur = new float[2];
        positionJoueur[0] = 0;
        positionJoueur[1] = 0;
        time = (float) levelManager.getCurrentTime();

        createBodies();
    }

    @Override
    public void handleInput() {
        if (ccl.isPlayerOnGround()) {
            joueur.changeDirection(0, InputManager.isPressed(InputManager.BUTTON_UP));
            joueur.changeDirection(1, InputManager.isDown(InputManager.BUTTON_RIGHT));
            joueur.changeDirection(2, InputManager.isDown(InputManager.BUTTON_LEFT));
        } else {
            joueur.changeDirection(0, false);
            joueur.changeDirection(1, false);
            joueur.changeDirection(2, false);
        }
    }

    @Override
    public void update(float dt) {

        world.step(dt, 6, 2);

        //Verification du temps
        if (time <= 0) {
            joueur.setDeath(true);
        }

        //Verification de l'etat joueur (Mort, Victoire, neutre)
        if (joueur.isDeath()) {
            deathSequence(dt); //Si Mort
        } else if (win) {
            winSequence(dt); //Si victoire
        } else {
            //Mise a jour du timer
            time -= dt;

            //Mise a jour du InputListener (utile seulement pour mobile)
            PlatVenture.inputListener.update(dt);

            //Verification si le personnage est en dehors de l'écran
            checkOutOfScreen();

            //Update du hud
            hud.update((int) time, joueur.getScore(), 0);

            handleInput();

            //Update du joueur
            joueur.update(dt);

            //remove crystals
            manageGems(dt);

            if (Gdx.graphics.getHeight() < levelManager.getCurrentSize(1)*GlobalVariables.PPM) {
                //set camera position
                if (GlobalVariables.DEBUG)
                    setCamToPlayer(cameraDebug, false);
                setCamToPlayer(cameraTexture, true);
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT); //Clear screen
        sb.setProjectionMatrix(cameraTexture.combined);

        sb.begin();

        //Draw background
        sb.draw(res.getResource("back"),0, 0, levelManager.getCurrentSize(0), levelManager.getCurrentSize(1));

        //Draw tiles
        drawMap();

        //Draw joyaux
        for (Joyau joyau : joyaux) {
            sb.draw(joyau.getFrame(),
                    joyau.getBody().getPosition().x+0.3f,
                    levelManager.getCurrentSize(1)-0.7f-joyau.getBody().getPosition().y,
                    0.4f, 0.4f);
        }
        sb.end();

        //Draw Joueur
        joueur.render(sb, levelManager.getCurrentSize(1));

        if (GlobalVariables.DEBUG)
            box2DDebugRenderer.render(world, cameraDebug.combined);

        sbHud.setProjectionMatrix(cameraHud.combined);

        hud.render(sbHud);
    }

    @Override
    public void dispose() {

    }

    private void changeLevel() {
        destroyAllBodies = true;
        joyaux.clear();
        levelManager.changeLevel();
    }

    private void winSequence(float dt) {
        hud.update((int) time, joueur.getScore(), 2);
        if (winTime >= 2f) {
            changeLevel();
            deleteAllBodies();
            time = levelManager.getCurrentTime();
            winTime = 0;
            win = false;

            if (GlobalVariables.DEBUG)
                setCamToPlayer(cameraDebug, false);
            setCamToPlayer(cameraTexture, true);
        } else {
            if (winTime == 0)
                resSound.getResource("win").play();
            winTime += dt;
        }
    }

    private void deathSequence(float dt) {
        hud.update((int) time, joueur.getScore(), 1);
        if (deathTime == 0) {
            world.destroyBody(joueur.getBody());
            resSound.getResource("loose").play();
        }
        if (deathTime >= 2f) {
            deathTime = 0;
            joueur.setDeath(false);
            joueur.createBody(world);
            joueur.transport(positionJoueur[0], positionJoueur[1]);
            joueur.resetScore();
            time = levelManager.getCurrentTime();
        } else {
            deathTime += dt;
        }
    }

    private void checkOutOfScreen() {
        if (ccl.changeLevel()) {
            if (joueur.getPosition().x < 0 || joueur.getPosition().x > levelManager.getCurrentSize(0)-1) {
                if (winTime == 0) {
                    win = true;
                }
            }
            ccl.finishChangeLevel();
        } else if (!ccl.isTouchExit()) {
            if (joueur.getPosition().x < -0.5 || joueur.getPosition().x > levelManager.getCurrentSize(0)) {
                joueur.setDeath(true);
            }
        }
    }

    private void manageGems(float dt) {
        Array<Body> joyaux1 = ccl.getJoyauxToRemove();
        for (Body joyau : joyaux1) {
            joueur.addScore(((Joyau) joyau.getUserData()).getScore());
            joyaux.remove((Joyau) joyau.getUserData());
            world.destroyBody(joyau);
            resSound.getResource("gem").play();
        }
        joyaux1.clear();

        for (Joyau joyau : joyaux) {
            joyau.update(dt);
        }
    }

    private void setCamToPlayer(OrthographicCamera camera, boolean yDown) {
        //Set the center of the cam on the player
        if (yDown) {
            camera.position.set(joueur.getPosition().x, levelManager.getCurrentSize(1) - joueur.getPosition().y, 0);
        } else {
            camera.position.set(joueur.getPosition().x, joueur.getPosition().y, 0);
        }

        if (camera.position.x + camera.viewportWidth / 2f > (float) levelManager.getCurrentSize(0)) {
            camera.position.set((float) levelManager.getCurrentSize(0) - camera.viewportWidth / 2f, camera.position.y, 0);
        } else if (camera.position.x - camera.viewportWidth / 2f < 0) {
            camera.position.set(camera.viewportWidth / 2f, camera.position.y, 0);
        }
        if (camera.position.y + camera.viewportHeight / 2f > (float) levelManager.getCurrentSize(1)) {
            camera.position.set(camera.position.x, (float) levelManager.getCurrentSize(1) - camera.viewportHeight / 2f, 0);
        } else if (camera.position.y - camera.viewportHeight / 2f < 0) {
            camera.position.set(camera.position.x, camera.viewportHeight / 2f, 0);
        }
        camera.update();
    }

    private void deleteAllBodies() {
        if(destroyAllBodies) {
            Array<Body> bodies = new Array<>();
            world.getBodies(bodies);
            for (int i = 0; i < bodies.size; i++) {
                if (!world.isLocked())
                    world.destroyBody(bodies.get(i));
            }

            createBodies();

            destroyAllBodies = false;
        }
    }

    private void createBodies() {
        BodyDef bodyDef = new BodyDef();
        for (int x = 0; x < levelManager.getCurrentSize(0); x++) {
            for (int y = 0; y < levelManager.getCurrentSize(1); y++) {
                bodyDef.position.set(x, y);
                bodyDef.type = BodyDef.BodyType.StaticBody;
                Body body = world.createBody(bodyDef);
                switch (levelManager.getCurrentMap(y, x)) {
                    case 'A':
                    case 'B':
                    case 'C':
                    case 'D':
                    case 'E':
                    case 'F':
                    case 'G':
                    case 'H':
                    case 'I':
                        body.createFixture(FabriqueObjetPhysique.createFixtureBrique());
                        break;
                    case 'J':
                        body.createFixture(FabriqueObjetPhysique.createFixturePlatGauche());
                        break;
                    case 'K':
                        body.createFixture(FabriqueObjetPhysique.createFixturePlatCentre());
                        break;
                    case 'L':
                        body.createFixture(FabriqueObjetPhysique.createFixturePlatDroite());
                        break;
                    case 'P':
                        positionJoueur[0] = x;
                        positionJoueur[1] = y;
                        joueur.createBody(world);
                        joueur.transport(x, y);
                        break;
                    case 'W':
                        body.createFixture(FabriqueObjetPhysique.createFixtureWater()).setUserData("water");
                        break;
                    case '1':
                        joyaux.add(new Joyau1(world, x, y));
                        break;
                    case '2':
                        joyaux.add(new Joyau2(world, x, y));
                        break;
                    case 'Z':
                        body.createFixture(FabriqueObjetPhysique.createFixtureSortie()).setUserData("sortie");
                        break;
                }
            }
        }

        if (Gdx.graphics.getHeight() < levelManager.getCurrentSize(1)*GlobalVariables.PPM) {
            //set camera position
            if (GlobalVariables.DEBUG)
                setCamToPlayer(cameraDebug, false);
            setCamToPlayer(cameraTexture, true);
        }
    }

    private void drawMap() {
        int yForCam;
        for (int x = 0; x < levelManager.getCurrentSize(0); x++) {
            for (int y = 0; y < levelManager.getCurrentSize(1); y++) {
                yForCam = levelManager.getCurrentSize(1)-1-y;
                switch (levelManager.getCurrentMap(y, x)) {
                    case 'A':
                        sb.draw(res.getResource("brickA"), x, yForCam, 1, 1);
                        break;
                    case 'B':
                        sb.draw(res.getResource("brickB"), x, yForCam, 1, 1);
                        break;
                    case 'C':
                        sb.draw(res.getResource("brickC"), x, yForCam, 1, 1);
                        break;
                    case 'D':
                        sb.draw(res.getResource("brickD"), x, yForCam, 1, 1);
                        break;
                    case 'E':
                        sb.draw(res.getResource("brickE"), x, yForCam, 1, 1);
                        break;
                    case 'F':
                        sb.draw(res.getResource("brickF"), x, yForCam, 1, 1);
                        break;
                    case 'G':
                        sb.draw(res.getResource("brickG"), x, yForCam, 1, 1);
                        break;
                    case 'H':
                        sb.draw(res.getResource("brickH"), x, yForCam, 1, 1);
                        break;
                    case 'I':
                        sb.draw(res.getResource("brickI"), x, yForCam, 1, 1);
                        break;
                    case 'J':
                        sb.draw(res.getResource("platJ"), x, yForCam, 1, 0.75f);
                        break;
                    case 'K':
                        sb.draw(res.getResource("platK"), x, yForCam, 1, 0.75f);
                        break;
                    case 'L':
                        sb.draw(res.getResource("platL"), x, yForCam, 1, 0.75f);
                        break;
                    case 'W':
                        sb.draw(res.getResource("water"), x, yForCam, 1, 0.75f);
                        break;
                    case 'Z':
                        sb.draw(res.getResource("sortie"), x, yForCam, 1, 1);
                        break;
                }
            }
        }
    }
}
