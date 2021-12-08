package com.platventure.game.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.platventure.game.entities.Joueur;
import com.platventure.game.states.Play;

public class CustomContactListener implements ContactListener {

    private int playerIsOnGround;
    private Array<Body> joyauxToRemove;
    private boolean changeLevel;
    private boolean touchExit;

    public CustomContactListener() {
        super();

        changeLevel = false;
        touchExit = false;
        joyauxToRemove = new Array<>();
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
            ((Joueur) fb.getBody().getUserData()).setDeath(true);
        }
        if(fb.getUserData() != null && fb.getUserData().equals("water")) {
            ((Joueur) fa.getBody().getUserData()).setDeath(true);
        }

        if(fa.getUserData() != null && fa.getUserData().equals("joyau")) {
            if(!joyauxToRemove.contains(fa.getBody(), true))
                joyauxToRemove.add(fa.getBody());
        }
        if(fb.getUserData() != null && fb.getUserData().equals("joyau")) {
            if(!joyauxToRemove.contains(fb.getBody(), true))
                joyauxToRemove.add(fb.getBody());
        }

        if(fa.getUserData() != null && fa.getUserData().equals("sortie")) {
            touchExit = true;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("sortie")) {
            touchExit = true;
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
            changeLevel = true;
            touchExit = false;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("sortie")) {
            changeLevel = true;
            touchExit = false;
        }
    }

    public boolean isPlayerOnGround() {
        boolean onGround = true;
        if (playerIsOnGround == 0)
            onGround = false;
        return onGround;
    }

    public Array<Body> getJoyauxToRemove() {
        return joyauxToRemove;
    }

    public boolean changeLevel() { return changeLevel; }

    public boolean isTouchExit() {
        return touchExit;
    }

    public void finishChangeLevel() { changeLevel=false; }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
