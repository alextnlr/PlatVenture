package com.platventure.game.fabriques;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.platventure.game.SizeUnit;

public class FabriqueJoyau extends FabriqueObjetPhysique {
    public FabriqueJoyau() {
        super();
        CircleShape shape = new CircleShape();
        shape.setRadius(0.2f*SizeUnit.getUnit());
        shape.setPosition(new Vector2(0.5f*SizeUnit.getUnit(), 0.5f*SizeUnit.getUnit()));
        fixtureDef.shape = shape;

        fixtureDef.isSensor = true; //Traversable
    }

    @Override
    public Body createBody(World world, int x, int y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x*SizeUnit.getUnit(), y*SizeUnit.getUnit());
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        return body;
    }
}
