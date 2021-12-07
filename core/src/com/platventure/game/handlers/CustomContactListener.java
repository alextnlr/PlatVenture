package com.platventure.game.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.platventure.game.states.Play;

public class CustomContactListener implements ContactListener {

    private int playerIsOnGround;
    private Array<Body> joyauxToRemove;
    private Play play;

    public CustomContactListener(Play play) {
        super();

        this.play = play;
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
            System.out.println("water");
        }
        if(fb.getUserData() != null && fb.getUserData().equals("water")) {
            System.out.println("water");
        }

        if(fa.getUserData() != null && fa.getUserData().equals("joyau")) {
            System.out.println("gem");
            joyauxToRemove.add(fa.getBody());
        }
        if(fb.getUserData() != null && fb.getUserData().equals("joyau")) {
            System.out.println("gem");
            joyauxToRemove.add(fb.getBody());
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
            play.changeLevel();
        }
        if(fb.getUserData() != null && fb.getUserData().equals("sortie")) {
            System.out.println("sortie");
            play.changeLevel();
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

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
