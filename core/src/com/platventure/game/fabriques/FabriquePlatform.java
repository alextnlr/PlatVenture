package com.platventure.game.fabriques;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public abstract class FabriquePlatform {
    protected FixtureDef fixtureDef;

    FabriquePlatform() {
        fixtureDef = new FixtureDef();

        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.friction = 0.25f;
    }

    public abstract Body createBody(World world, int x, int y);
}
