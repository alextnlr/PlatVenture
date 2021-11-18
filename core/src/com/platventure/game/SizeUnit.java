package com.platventure.game;

import com.badlogic.gdx.Gdx;

public class SizeUnit {
    private static SizeUnit instance = new SizeUnit();
    private float unit;

    private SizeUnit() {
        unit = Gdx.graphics.getWidth()/16f;
    }

    public static float getUnit() {
        return instance.unit;
    }
}
