package com.platventure.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
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

    private CustomContactListener ccl;
    private boolean destroyAllBodies;

    public Play(GameStateManager gsm) {
        super(gsm);

        world = new World(new Vector2(0, 10f), true);
        ccl = new CustomContactListener(this);
        world.setContactListener(ccl);

        box2DDebugRenderer = new Box2DDebugRenderer();

        joueur = new Joueur();
        joyaux = new ArrayList<>();
        levelManager = new LevelManager();
        destroyAllBodies = false;

        createBodies();
    }

    @Override
    public void handleInput() {
        if (InputManager.isPressed(InputManager.BUTTON_UP)) {
            if (ccl.isPlayerOnGround()) {
                joueur.applyForce(0f, -45f);
            }
        }
        if (InputManager.isDown(InputManager.BUTTON_RIGHT)) {
            if (ccl.isPlayerOnGround()) {
                joueur.applyForce(1.f, 0);
            }
        }
        if (InputManager.isDown(InputManager.BUTTON_LEFT)) {
            if (ccl.isPlayerOnGround()) {
                joueur.applyForce(-1.f, 0);
            }
        }
    }

    @Override
    public void update(float dt) {
        deleteAllBodies();

        handleInput();

        world.step(dt, 6, 2);

        //remove crystals
        Array<Body> joyaux1 = ccl.getJoyauxToRemove();
        for (Body joyau : joyaux1) {
            joueur.addScore(((Joyau) joyau.getUserData()).getScore());
            joyaux.remove((Joyau) joyau.getUserData());
            world.destroyBody(joyau);
            System.out.println(joueur.getScore());
        }
        joyaux1.clear();

        for (Joyau joyau : joyaux) {
            joyau.update(dt);
        }

        //set camera position
        /*camera.position.set(joueur.getPosition(), 0);
        if (camera.position.x + camera.viewportWidth/2f > (float) levelManager.getCurrentSize(0)) {
            camera.position.set((float) levelManager.getCurrentSize(0)-camera.viewportWidth/2f, camera.position.y, 0);
        } else if (camera.position.x - camera.viewportWidth/2f < 0) {
            camera.position.set(camera.viewportWidth/2f, camera.position.y, 0);
        }
        if (camera.position.y + camera.viewportHeight/2f > (float) levelManager.getCurrentSize(1)) {
            camera.position.set(camera.position.x,(float) levelManager.getCurrentSize(1)-camera.viewportHeight/2f, 0);
        } else if (camera.position.y - camera.viewportHeight/2f < 0) {
            camera.position.set(camera.position.x,camera.viewportHeight/2f, 0);
        }
        camera.update();*/

        hudCam.position.set(joueur.getPosition().x, levelManager.getCurrentSize(1)-joueur.getPosition().y, 0);
        if (hudCam.position.x + hudCam.viewportWidth/2f > (float) levelManager.getCurrentSize(0)) {
            hudCam.position.set((float) levelManager.getCurrentSize(0)-hudCam.viewportWidth/2f, hudCam.position.y, 0);
        } else if (hudCam.position.x - hudCam.viewportWidth/2f < 0) {
            hudCam.position.set(hudCam.viewportWidth/2f, hudCam.position.y, 0);
        }
        if (hudCam.position.y + hudCam.viewportHeight/2f > (float) levelManager.getCurrentSize(1)) {
            hudCam.position.set(hudCam.position.x,(float) levelManager.getCurrentSize(1)-hudCam.viewportHeight/2f, 0);
        } else if (hudCam.position.y - hudCam.viewportHeight/2f < 0) {
            hudCam.position.set(hudCam.position.x,hudCam.viewportHeight/2f, 0);
        }
        hudCam.update();
    }

    @Override
    public void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(hudCam.combined);
        sb.begin();

        //Draw background
        sb.draw(res.getTexture("back"),0, 0, levelManager.getCurrentSize(0), levelManager.getCurrentSize(1));

        //Draw tiles
        drawMap();

        //Draw joyaux
        for (Joyau joyau : joyaux) {
            sb.draw(joyau.getFrame(),
                    joyau.getBody().getPosition().x+0.3f,
                    levelManager.getCurrentSize(1)-0.7f-joyau.getBody().getPosition().y,
                    0.4f, 0.4f);
        }

        //Draw joyaux
        sb.draw(joueur.getTexture(), joueur.getPosition().x, levelManager.getCurrentSize(1)-0.86f-joueur.getPosition().y, 0.5f,0.86f);
        sb.end();

        //box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {

    }

    public void changeLevel() {
        destroyAllBodies = true;
        joyaux.clear();
        levelManager.changeLevel();
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

    public void createBodies() {
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
    }

    public void drawMap() {
        int yForCam;
        for (int x = 0; x < levelManager.getCurrentSize(0); x++) {
            for (int y = 0; y < levelManager.getCurrentSize(1); y++) {
                yForCam = levelManager.getCurrentSize(1)-1-y;
                switch (levelManager.getCurrentMap(y, x)) {
                    case 'A':
                        sb.draw(res.getTexture("brickA"), x, yForCam, 1, 1);
                        break;
                    case 'B':
                        sb.draw(res.getTexture("brickB"), x, yForCam, 1, 1);
                        break;
                    case 'C':
                        sb.draw(res.getTexture("brickC"), x, yForCam, 1, 1);
                        break;
                    case 'D':
                        sb.draw(res.getTexture("brickD"), x, yForCam, 1, 1);
                        break;
                    case 'E':
                        sb.draw(res.getTexture("brickE"), x, yForCam, 1, 1);
                        break;
                    case 'F':
                        sb.draw(res.getTexture("brickF"), x, yForCam, 1, 1);
                        break;
                    case 'G':
                        sb.draw(res.getTexture("brickG"), x, yForCam, 1, 1);
                        break;
                    case 'H':
                        sb.draw(res.getTexture("brickH"), x, yForCam, 1, 1);
                        break;
                    case 'I':
                        sb.draw(res.getTexture("brickI"), x, yForCam, 1, 1);
                        break;
                    case 'J':
                        sb.draw(res.getTexture("platJ"), x, yForCam, 1, 0.75f);
                        break;
                    case 'K':
                        sb.draw(res.getTexture("platK"), x, yForCam, 1, 0.75f);
                        break;
                    case 'L':
                        sb.draw(res.getTexture("platL"), x, yForCam, 1, 0.75f);
                        break;
                    case 'W':
                        sb.draw(res.getTexture("water"), x, yForCam, 1, 0.75f);
                        break;
                    case 'Z':
                        sb.draw(res.getTexture("sortie"), x, yForCam, 1, 1);
                        break;
                }
            }
        }
    }
}
