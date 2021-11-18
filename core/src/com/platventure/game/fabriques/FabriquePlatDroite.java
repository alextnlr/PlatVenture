package com.platventure.game.fabriques;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.platventure.game.SizeUnit;

public class FabriquePlatDroite extends FabriquePlatform {
    private static FabriquePlatDroite instance = new FabriquePlatDroite();

    private FabriquePlatDroite() {
        Vector2[] pts = {new Vector2(0, 0), new Vector2(SizeUnit.getUnit(), 0),
                new Vector2(SizeUnit.getUnit(), SizeUnit.getUnit()*0.5f),new Vector2(SizeUnit.getUnit()*0.5f, SizeUnit.getUnit()*0.75f) , new Vector2(0, SizeUnit.getUnit()*0.75f)};
        PolygonShape shape = new PolygonShape();
        shape.set(pts);
        fixtureDef.shape = shape;
    }

    public static Body createBody(World world, int x, int y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x*SizeUnit.getUnit(), y*SizeUnit.getUnit());
        Body body = world.createBody(bodyDef);
        body.createFixture(instance.fixtureDef);
        return body;
    }
}
