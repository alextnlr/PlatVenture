package com.platventure.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.platventure.game.fabriques.FabriqueObjetPhysique;
import com.platventure.game.handlers.Animation;

public abstract class Joyau {
    protected Body body;
    protected Animation animation;

    public Joyau(World world, int x, int y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        body.createFixture(FabriqueObjetPhysique.createFixtureJoyaux()).setUserData("joyau");
        body.setUserData(this);
    }

    public void update(float dt) {
        animation.update(dt);
    }

    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(animation.getFrame(),
                body.getPosition().x+0.3f,
                body.getPosition().y+0.3f,
                0.4f, 0.4f);
        sb.end();
    }

    public Body getBody() {
        return body;
    }

    public TextureRegion getFrame() { return animation.getFrame(); }

    public abstract int getScore();
}
