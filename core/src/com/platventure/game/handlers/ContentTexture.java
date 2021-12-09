package com.platventure.game.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class ContentTexture implements Content{
    private final HashMap<String, Texture> content;

    public ContentTexture() {
        content = new HashMap<>();
    }

    @Override
    public void loadResource(String path, String key) {
        Texture texture = new Texture(Gdx.files.internal(path));
        content.put(key, texture);
    }

    public Texture getResource(String key) {
        return content.get(key);
    }

    @Override
    public void disposeResources() {
        for (Texture textureRegion : content.values()) {
            textureRegion.dispose();
        }
    }
}
