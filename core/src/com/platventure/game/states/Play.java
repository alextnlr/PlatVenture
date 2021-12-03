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
import com.platventure.game.handlers.CustomContactListener;
import com.platventure.game.handlers.GameStateManager;
import com.platventure.game.handlers.InputManager;

public class Play extends GameState {

    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private OrthographicCamera b2dcam;

    private Body playerBody;

    private CustomContactListener ccl;

    public Play(GameStateManager gsm) {
        super(gsm);

        world = new World(new Vector2(0, 10f), true);
        ccl = new CustomContactListener();
        world.setContactListener(ccl);

        box2DDebugRenderer = new Box2DDebugRenderer();

        //Test Platform
        BodyDef bdef = new BodyDef();
        bdef.position.set(500/PPM, 500/PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bdef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(500/PPM, 5/PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GlobalVariables.BIT_GROUND;
        fixtureDef.filter.maskBits = GlobalVariables.BIT_PLAYER;
        body.createFixture(fixtureDef);

        //Test perso
        bdef.position.set(500/PPM, 200/PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.fixedRotation = true;
        playerBody = world.createBody(bdef);

        fixtureDef.density = 0.5f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.friction = 0.5f;
        PolygonShape shapeDiamond = new PolygonShape();
        Vector2[] ptsDiamond = {new Vector2(0.25f, 0), new Vector2(0.5f, 0.86f*0.5f),
                new Vector2(0.25f, 0.86f*0.75f), new Vector2(0, 0.86f*0.5f)};
        shapeDiamond.set(ptsDiamond);
        fixtureDef.shape = shapeDiamond;
        fixtureDef.filter.categoryBits = GlobalVariables.BIT_PLAYER;
        fixtureDef.filter.maskBits = GlobalVariables.BIT_GROUND;
        playerBody.createFixture(fixtureDef).setUserData("player");


        fixtureDef.density = 0.5f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.friction = 0.5f;
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.86f*0.125f);
        circleShape.setPosition(new Vector2(0.25f, 0.875f*0.86f));

        fixtureDef.shape = circleShape;
        fixtureDef.filter.categoryBits = GlobalVariables.BIT_PLAYER;
        fixtureDef.filter.maskBits = GlobalVariables.BIT_GROUND;
        playerBody.createFixture(fixtureDef).setUserData("player");

        circleShape.setRadius(0.5f*0.125f);
        circleShape.setPosition(new Vector2(0.25f, 0.86f));
        fixtureDef.shape = circleShape;
        fixtureDef.filter.categoryBits = GlobalVariables.BIT_PLAYER;
        fixtureDef.filter.maskBits = GlobalVariables.BIT_GROUND;
        fixtureDef.isSensor = true;
        playerBody.createFixture(fixtureDef).setUserData("foot");

        b2dcam = new OrthographicCamera();
        b2dcam.setToOrtho(true, V_WIDTH / PPM, V_HEIGHT / PPM);
    }

    @Override
    public void handleInput() {
        if (InputManager.isPressed(InputManager.BUTTON_UP)) {
            if (ccl.isPlayerOnGround()) {
                playerBody.applyForceToCenter(0, -40.f, true);
            }
        }
        if (InputManager.isDown(InputManager.BUTTON_RIGHT)) {
            if (ccl.isPlayerOnGround()) {
                playerBody.applyForceToCenter(1.f, 0, true);
            }
        }
        if (InputManager.isDown(InputManager.BUTTON_LEFT)) {
            if (ccl.isPlayerOnGround()) {
            playerBody.applyForceToCenter(-1.f, 0, true);
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
