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

    private OrthographicCamera b2dcam;

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
        levelManager = new LevelManager("level_001.txt");

        char[][] map = levelManager.getLevelInfos();
        int[] mapSize = levelManager.getTailleLevel();
        BodyDef bodyDef = new BodyDef();
        for (int x = 0; x < mapSize[0]; x++) {
            for (int y = 0; y < mapSize[1]; y++) {
                bodyDef.position.set(x, y);
                bodyDef.type = BodyDef.BodyType.StaticBody;
                Body body = world.createBody(bodyDef);
                switch (map[y][x]) {
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
                        joueur.transport(y, x);
                        break;
                    case 'W':
                        body.createFixture(FabriqueObjetPhysique.createFixtureWater());
                        break;
                    case '1':
                    case '2':
                        body.createFixture(FabriqueObjetPhysique.createFixtureJoyaux());
                        break;
                    case 'Z':
                        body.createFixture(FabriqueObjetPhysique.createFixtureSortie());
                        break;
                }
            }
        }

        b2dcam = new OrthographicCamera();
        b2dcam.setToOrtho(true, V_WIDTH / PPM, V_HEIGHT / PPM);
    }

    @Override
    public void handleInput() {
        if (InputManager.isPressed(InputManager.BUTTON_UP)) {
            if (ccl.isPlayerOnGround()) {
                joueur.applyForce(0f, -40f);
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
    }

    @Override
    public void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        box2DDebugRenderer.render(world, b2dcam.combined);
        /*sb.setProjectionMatrix(camera.combined);
        sb.begin();

        sb.end();*/
    }

    @Override
    public void dispose() {

    }
}
