package com.platventure.game.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class Content {
    private HashMap<String, Texture> textures;

    public Content() {
        textures = new HashMap<>();
    }

    public void loadTexture(String path, String key) {
        Texture texture = new Texture(Gdx.files.internal(path));
        textures.put(key, texture);
    }

    public Texture getTexture(String key) {
        return textures.get(key);
    }

    public void disposeTexture() {
        for (Texture textureRegion : textures.values()) {
            textureRegion.dispose();
        }
    }
}
