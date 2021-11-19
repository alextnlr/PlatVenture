package com.platventure.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Joueur {

    FixtureDef fixtureDefDiamond, fixtureDefRond;

    public Joueur() {
        fixtureDefDiamond = new FixtureDef();

        fixtureDefDiamond.density = 0.5f;
        fixtureDefDiamond.restitution = 0.1f;
        fixtureDefDiamond.friction = 0.5f;

        PolygonShape shapeDiamond = new PolygonShape();
        Vector2[] ptsDiamond = {new Vector2(0.25f*SizeUnit.getUnit(), 0), new Vector2(0.5f*SizeUnit.getUnit(), 0.86f*0.5f*SizeUnit.getUnit()),
                new Vector2(SizeUnit.getUnit()*0.25f, 0.86f*0.75f*SizeUnit.getUnit()), new Vector2(0, 0.86f*0.5f*SizeUnit.getUnit())};
        shapeDiamond.set(ptsDiamond);

        fixtureDefDiamond.shape = shapeDiamond;

        fixtureDefRond = new FixtureDef();

        fixtureDefRond.density = 0.5f;
        fixtureDefRond.restitution = 0.1f;
        fixtureDefRond.friction = 0.5f;

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.86f*0.125f*SizeUnit.getUnit());
        circleShape.setPosition(new Vector2(0.25f*SizeUnit.getUnit(), 0.875f*0.86f*SizeUnit.getUnit()));

        fixtureDefRond.shape = circleShape;
    }

    public Body createBody(World world, int x, int y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x*SizeUnit.getUnit()+0.25f*SizeUnit.getUnit(), y*SizeUnit.getUnit()+0.13f*SizeUnit.getUnit());
        Body body = world.createBody(bodyDef);
        body.createFixture(fixtureDefRond);
        body.createFixture(fixtureDefDiamond);
        return body;
    }
}
