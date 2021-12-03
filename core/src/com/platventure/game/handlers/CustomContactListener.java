package com.platventure.game.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CustomContactListener implements ContactListener {

    private boolean playerIsOnGround;

    //Appelé lorsque 2 fixtures rentre en contact
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
            playerIsOnGround = true;
        }
        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            playerIsOnGround = true;
        }
    }

    //Appelé lorsque 2 fixtures ne sont plus en contact
    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
            playerIsOnGround = false;
        }
        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            playerIsOnGround = false;
        }
    }

    public boolean isPlayerOnGround() { return playerIsOnGround; }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
