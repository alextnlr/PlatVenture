package com.platventure.game.states;

import static com.platventure.game.PlatVenture.V_HEIGHT;
import static com.platventure.game.PlatVenture.V_WIDTH;
import static com.platventure.game.GlobalVariables.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.platventure.game.GlobalVariables;
import com.platventure.game.Joueur;
import com.platventure.game.fabriques.FabriqueObjetPhysique;
import com.platventure.game.fabriques.LevelManager;
import com.platventure.game.handlers.CustomContactListener;
import com.platventure.game.handlers.GameStateManager;
import com.platventure.game.handlers.InputManager;

public class Play extends GameState {

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private Joueur joueur;
    private LevelManager levelManager;

    private CustomContactListener ccl;

    public Play(GameStateManager gsm) {
        super(gsm);

        world = new World(new Vector2(0, 10f), true);
        ccl = new CustomContactListener();
        world.setContactListener(ccl);

        box2DDebugRenderer = new Box2DDebugRenderer();

        joueur = new Joueur(world);
        levelManager = new LevelManager();

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
        handleInput();

        world.step(dt, 6, 2);

        //remove crystals
        Array<Body> joyaux1 = ccl.getJoyaux1ToRemove();
        for (Body joyau : joyaux1) {
            world.destroyBody(joyau);
            joueur.addScore(1);
            System.out.println(joueur.getScore());
        }
        joyaux1.clear();
        Array<Body> joyaux2 = ccl.getJoyaux2ToRemove();
        for (Body joyau : joyaux2) {
            world.destroyBody(joyau);
            joueur.addScore(2);
            System.out.println(joueur.getScore());
        }
        joyaux2.clear();

        //set camera position
        camera.position.set(joueur.getPosition(), 0);
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
        camera.update();
    }

    @Override
    public void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(hudCam.combined);
        sb.begin();
        sb.draw(res.getTexture("back"),0, 0, levelManager.getCurrentSize(0), levelManager.getCurrentSize(1));
        drawMap();
        sb.draw(joueur.getTexture(), joueur.getPosition().x, levelManager.getCurrentSize(1) -1- joueur.getPosition().y, 0.5f,0.86f);
        sb.end();

        box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {

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
                        joueur.transport(x, y);
                        break;
                    case 'W':
                        body.createFixture(FabriqueObjetPhysique.createFixtureWater()).setUserData("water");
                        break;
                    case '1':
                        body.createFixture(FabriqueObjetPhysique.createFixtureJoyaux()).setUserData("joyau1");
                        break;
                    case '2':
                        body.createFixture(FabriqueObjetPhysique.createFixtureJoyaux()).setUserData("joyau2");
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
                    case '1':
                        sb.draw(res.getTexture("gem1"), x, yForCam, 1, 1);
                        break;
                    case '2':
                        sb.draw(res.getTexture("gem2"), x, yForCam, 1, 1);
                        break;
                    case 'Z':
                        sb.draw(res.getTexture("sortie"), x, yForCam, 1, 1);
                        break;
                }
            }
        }
    }
}
