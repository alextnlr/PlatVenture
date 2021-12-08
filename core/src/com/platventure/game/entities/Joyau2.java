package com.platventure.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.platventure.game.PlatVenture;
import com.platventure.game.handlers.Animation;

public class Joyau2 extends Joyau {
    public Joyau2(World world, int x, int y) {
        super(world, x, y);

        Texture tex = PlatVenture.res.getTexture("gem2");
        TextureRegion[] sprites = new TextureRegion[6];
        for (int i = 0; i < 6; i++) {
            sprites[i] = TextureRegion.split(tex, 56, 56)[i][0];
        }
        animation = new Animation(sprites, 1/6f);
    }

    @Override
    public int getScore() {
        return 2;
    }
}
