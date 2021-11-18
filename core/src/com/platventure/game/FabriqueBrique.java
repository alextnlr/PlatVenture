package com.platventure.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class FabriqueBrique {
    private static FabriqueBrique instance = new FabriqueBrique();
    private FixtureDef fixtureDef;

    private FabriqueBrique() {
        fixtureDef = new FixtureDef();

        Vector2[] pts = {new Vector2(0, 0), new Vector2(SizeUnit.getUnit(), 0),
                new Vector2(SizeUnit.getUnit(), SizeUnit.getUnit()), new Vector2(0, SizeUnit.getUnit())};
        PolygonShape shape = new PolygonShape();
        shape.set(pts);
        fixtureDef.shape = shape;

        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.friction = 0.25f;
    }

    public static void getBody(World world, int x, int y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x*SizeUnit.getUnit(), y*SizeUnit.getUnit());
        Body body = world.createBody(bodyDef);
        body.createFixture(instance.fixtureDef);
    }
}
