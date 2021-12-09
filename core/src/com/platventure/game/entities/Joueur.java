package com.platventure.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.platventure.game.GlobalVariables;
import com.platventure.game.handlers.Animation;
import com.platventure.game.handlers.Content;

import java.util.HashMap;

public class Joueur {

    private Body body;

    private int score;
    private Content res;
    private boolean direction[];
    private boolean death;
    private HashMap<String, Animation> animations;

    public Joueur() {
        score = 0;
        res = new Content();

        direction = new boolean[3];
        death = false;

        loadTextures();
        animations = new HashMap<>();
        animations.put("idle", new Animation(createTextureRegion("idle", false)));
        animations.put("jumpRight", new Animation(createTextureRegion("jump", false)));
        animations.put("jumpLeft", new Animation(createTextureRegion("jump", true)));
        animations.put("runRight", new Animation(createTextureRegion("run", false)));
        animations.put("runLeft", new Animation(createTextureRegion("run", true)));
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

        if (body.getLinearVelocity().y != 0) {
            if (body.getLinearVelocity().x >= 0) {
                if (animations.get("jumpRight").getTimesPlayed() < 1)
                animations.get("jumpRight").update(dt);
            } else {
                if (animations.get("jumpLeft").getTimesPlayed() < 1)
                animations.get("jumpLeft").update(dt);
            }
            animations.get("runRight").reset();
            animations.get("runLeft").reset();
        } else if (body.getLinearVelocity().x > 0) {
            animations.get("runRight").update(dt);
            animations.get("runLeft").reset();
            animations.get("jumpRight").reset();
            animations.get("jumpLeft").reset();
        } else if (body.getLinearVelocity().x < 0) {
            animations.get("runLeft").update(dt);
            animations.get("runRight").reset();
            animations.get("jumpRight").reset();
            animations.get("jumpLeft").reset();
        } else {
            animations.get("idle").update(dt);
            animations.get("runLeft").reset();
            animations.get("runRight").reset();
            animations.get("jumpRight").reset();
            animations.get("jumpLeft").reset();
        }
    }

    public Body getBody() { return body; }

    public void setDeath(boolean death) { this.death = death; }

    public boolean isDeath() {
        return death;
    }

    public void render(SpriteBatch sb, int ySize) {
        sb.begin();

        sb.draw(getTexture(), getPosition().x, ySize - 0.86f - getPosition().y, 0.5f, 0.86f);

        sb.end();
    }

    public Vector2 getPosition() { return body.getPosition(); }

    public TextureRegion getTexture() {
        TextureRegion tex;
        if (body.getLinearVelocity().y != 0) {
            if (body.getLinearVelocity().x >= 0) {
                tex = animations.get("jumpRight").getFrame();
            } else {
                tex = animations.get("jumpLeft").getFrame();
            }
        } else if (body.getLinearVelocity().x > 0) {
            tex = animations.get("runRight").getFrame();
        } else if (body.getLinearVelocity().x < 0) {
            tex = animations.get("runLeft").getFrame();
        } else {
            tex = animations.get("idle").getFrame();
        }
        return tex;
    }

    private void loadTextures() {
        for (int i = 0; i < 10; i++) {
            res.loadTexture("images/Idle__00"+i+".png", "idle"+i);

            res.loadTexture("images/Jump__00"+i+".png", "jump"+i);

            res.loadTexture("images/Run__00"+i+".png", "run"+i);
        }
    }

    private TextureRegion[] createTextureRegion(String key, boolean flip) {
        TextureRegion[] texReg = new TextureRegion[10];
        for (int i = 0; i < 10; i++) {
            texReg[i] = new TextureRegion(res.getTexture(key+i));
            texReg[i].flip(flip, false);
        }
        return texReg;
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

    public void dispose() {
        res.disposeTexture();
    }
}
