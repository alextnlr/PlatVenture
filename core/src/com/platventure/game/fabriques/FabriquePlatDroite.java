package com.platventure.game.fabriques;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class FabriquePlatDroite extends FabriqueObjetPhysique {

    public FabriquePlatDroite() {
        Vector2[] pts = {new Vector2(0, 0), new Vector2(1.f, 0),
                new Vector2(1.f, 0.5f),new Vector2(0.5f, 0.75f) , new Vector2(0, 0.75f)};
        PolygonShape shape = new PolygonShape();
        shape.set(pts);
        fixtureDef.shape = shape;
    }

    public Body createBody(World world, int x, int y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        return body;
    }
}
