package com.platventure.game;

import static com.platventure.game.GlobalVariables.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Joueur {

    Body body;

    public Joueur(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();

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

        body.createFixture(fixtureDef).setUserData("player");

        fixtureDef.density = 0.5f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.friction = 0.5f;
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.86f*0.125f);
        circleShape.setPosition(new Vector2(0.25f, 0.875f*0.86f));

        fixtureDef.shape = circleShape;
        fixtureDef.filter.categoryBits = GlobalVariables.BIT_PLAYER;
        fixtureDef.filter.maskBits = GlobalVariables.BIT_GROUND;

        body.createFixture(fixtureDef).setUserData("player");

        circleShape.setRadius(0.5f*0.125f);
        circleShape.setPosition(new Vector2(0.25f, 0.86f));
        fixtureDef.shape = circleShape;
        fixtureDef.filter.categoryBits = GlobalVariables.BIT_PLAYER;
        fixtureDef.filter.maskBits = GlobalVariables.BIT_GROUND;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef).setUserData("foot");
    }

    public void transport(float x, float y) {
        body.setTransform(new Vector2(x, y), 0);
    }

    public void applyForce(float x, float y) {
        body.applyForceToCenter(x, y, true);
    }
}
