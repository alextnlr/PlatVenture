package com.platventure.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.platventure.game.GlobalVariables;
import com.platventure.game.handlers.Content;

public class Joueur {

    private Body body;

    private int score;
    private Content res;
    private boolean direction[];
    private boolean death;

    public Joueur() {
        score = 0;
        res = new Content();

        direction = new boolean[3];
        death = false;

        loadTextures();
    }

    public void transport(float x, float y) {
        body.setTransform(new Vector2(x+0.25f, y), 0);
    }

    public void applyForce(float x, float y) {
        body.applyForceToCenter(x, y, true);
    }



    public void addScore(int score) { this.score += score; }

    public void resetScore() { this.score = 0; }

    public int getScore() {
        return score;
    }

    public void changeDirection(int dir, boolean active) {
        direction[dir] = active;
    }

    public void update(float dt) {
        if (direction[0]) {
            applyForce(0, -50f);
        }
        if (direction[1]) {
            applyForce(1f, 0);
        }
        if (direction[2]) {
            applyForce(-1f, 0);
        }
    }

    public Body getBody() { return body; }

    public void setDeath(boolean death) { this.death = death; }

    public boolean isDeath() {
        return death;
    }

    public void render() {

    }

    public Vector2 getPosition() { return body.getPosition(); }

    public Texture getTexture() {
        return res.getTexture("idle0");
    }

    private void loadTextures() {
        res.loadTexture("images/Idle__000.png", "idle0");
    }

    public void createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        body = world.createBody(bodyDef);
        body.setUserData(this);

        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.density = 0.5f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.friction = 0.5f;
        PolygonShape shapeDiamond = new PolygonShape();
        Vector2[] ptsDiamond = {new Vector2(0.25f, 0), new Vector2(0.5f, 0.86f*0.5f),
                new Vector2(0.25f, 0.86f*0.75f), new Vector2(0, 0.86f*0.5f)};
        shapeDiamond.set(ptsDiamond);
        fixtureDef.shape = shapeDiamond;
        fixtureDef.filter.categoryBits = GlobalVariables.BIT_PLAYER;
        fixtureDef.filter.maskBits = GlobalVariables.BIT_GROUND;

        body.createFixture(fixtureDef).setUserData("player");

        shapeDiamond.dispose();

        fixtureDef.density = 0.5f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.friction = 0.5f;
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.86f*0.125f);
        circleShape.setPosition(new Vector2(0.25f, 0.875f*0.86f));

        fixtureDef.shape = circleShape;
        fixtureDef.filter.categoryBits = GlobalVariables.BIT_PLAYER;
        fixtureDef.filter.maskBits = GlobalVariables.BIT_GROUND;

        body.createFixture(fixtureDef).setUserData("player");

        circleShape.setRadius(0.75f*0.125f);
        circleShape.setPosition(new Vector2(0.25f, 0.86f));
        fixtureDef.shape = circleShape;
        fixtureDef.filter.categoryBits = GlobalVariables.BIT_PLAYER;
        fixtureDef.filter.maskBits = GlobalVariables.BIT_GROUND;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef).setUserData("foot");

        circleShape.dispose();
    }
}
