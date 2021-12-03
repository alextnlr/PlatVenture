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

    Body body;

    boolean m_right, m_left, m_up;

    Vector2 horizontalImpulse, verticalImpulse;

    public Joueur() {
        fixtureDefDiamond = new FixtureDef();

        fixtureDefDiamond.density = 0.5f;
        fixtureDefDiamond.restitution = 0.1f;
        fixtureDefDiamond.friction = 0.5f;

        PolygonShape shapeDiamond = new PolygonShape();
        Vector2[] ptsDiamond = {new Vector2(0.25f, 0), new Vector2(0.5f, 0.86f*0.5f),
                new Vector2(0.25f, 0.86f*0.75f), new Vector2(0, 0.86f*0.5f)};
        shapeDiamond.set(ptsDiamond);

        fixtureDefDiamond.shape = shapeDiamond;

        //shapeDiamond.dispose();

        fixtureDefRond = new FixtureDef();

        fixtureDefRond.density = 0.5f;
        fixtureDefRond.restitution = 0.1f;
        fixtureDefRond.friction = 0.5f;

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.86f*0.125f);
        circleShape.setPosition(new Vector2(0.25f, 0.875f*0.86f));

        fixtureDefRond.shape = circleShape;

        m_right = false;
        m_left = false;
        m_up = false;

        //shapeDiamond.dispose();

        horizontalImpulse = new Vector2(0f, 0f);
        verticalImpulse = new Vector2(0f, 0f);
    }

    public Body createBody(World world, int x, int y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDefRond);
        body.createFixture(fixtureDefDiamond);
        return body;
    }

    public void changeMRight(boolean change)
    {
        if (change)
        {
            horizontalImpulse.add(1.f, 0f);
        } else {
            horizontalImpulse.add(-1.f, 0.f);
        }
        //m_right = change;
    }

    public void changeMLeft(boolean change)
    {
        if(change) {
            horizontalImpulse.add(-1.f, 0f);
        } else {
            horizontalImpulse.add(1.f, 0f);
        }
        //m_left = change;
    }

    public void changeMUp(boolean change)
    {
        if (change) {
            verticalImpulse.add(0.f, -40.f*body.getMass());
        } else {
            verticalImpulse.add(0.f, 40.f*body.getMass());
        }
        //m_up = change;
    }

    public Vector2 getHorizontalImpulse()
    {
        return horizontalImpulse;
    }

    public Vector2 getVerticalImpulse()
    {
        return verticalImpulse;
    }

    /*public void updateForces()
    {
        Vector2 impulse = new Vector2(0f, 0f);
        if (m_right) {
            impulse.add(1.f, 0.f);
        }
        if (m_left) {
            impulse.add(-1.f, 0.f);
        }
        if (m_up) {
            impulse.add(0.f, -40.f);
        }

        //impulse.nor();
        impulse.scl(SizeUnit.getUnit());

        body.setLinearVelocity(impulse);
        System.out.println(body.getLinearVelocity().x/SizeUnit.getUnit() +", "+ body.getLinearVelocity().y/SizeUnit.getUnit());
    }*/

    public Body getBody() {
        return body;
    }
}
