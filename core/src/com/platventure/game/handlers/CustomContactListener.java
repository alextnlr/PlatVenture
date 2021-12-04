package com.platventure.game.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class CustomContactListener implements ContactListener {

    private int playerIsOnGround;
    private Array<Body> joyaux1ToRemove, joyaux2ToRemove;

    public CustomContactListener() {
        super();

        joyaux1ToRemove = new Array<>();
        joyaux2ToRemove = new Array<>();
    }

    //Appelé lorsque 2 fixtures rentre en contact
    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
            playerIsOnGround += 1;
        }
        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            playerIsOnGround += 1;
        }

        if(fa.getUserData() != null && fa.getUserData().equals("water")) {
            System.out.println("water");
        }
        if(fb.getUserData() != null && fb.getUserData().equals("water")) {
            System.out.println("water");
        }

        if(fa.getUserData() != null && fa.getUserData().equals("joyau1")) {
            joyaux1ToRemove.add(fa.getBody());
        }
        if(fb.getUserData() != null && fb.getUserData().equals("joyau1")) {
            joyaux1ToRemove.add(fb.getBody());
        }

        if(fa.getUserData() != null && fa.getUserData().equals("joyau2")) {
            joyaux2ToRemove.add(fa.getBody());
        }
        if(fb.getUserData() != null && fb.getUserData().equals("joyau2")) {
            joyaux2ToRemove.add(fb.getBody());
        }
    }

    //Appelé lorsque 2 fixtures ne sont plus en contact
    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getUserData() != null && fa.getUserData().equals("foot")) {
            playerIsOnGround -= 1;
        }
        if (fb.getUserData() != null && fb.getUserData().equals("foot")) {
            playerIsOnGround -= 1;
        }

        if(fa.getUserData() != null && fa.getUserData().equals("sortie")) {
            System.out.println("sortie");
        }
        if(fb.getUserData() != null && fb.getUserData().equals("sortie")) {
            System.out.println("sortie");
        }
    }

    public boolean isPlayerOnGround() {
        boolean onGround = true;
        if (playerIsOnGround == 0)
            onGround = false;
        return onGround;
    }

    public Array<Body> getJoyaux1ToRemove() {
        return joyaux1ToRemove;
    }

    public Array<Body> getJoyaux2ToRemove() {
        return joyaux2ToRemove;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
