package com.platventure.game.fabriques;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

public class FabriqueJoyau extends FabriqueObjetPhysique {
    public FabriqueJoyau() {
        super();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.2f);
        shape.setPosition(new Vector2(0.5f, 0.5f));
        fixtureDef.shape = shape;

        fixtureDef.isSensor = true; //Traversable
    }

    @Override
    public Body createBody(World world, int x, int y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        return body;
    }
}
