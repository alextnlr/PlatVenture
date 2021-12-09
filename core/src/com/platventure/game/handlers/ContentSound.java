package com.platventure.game.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public class ContentSound implements Content{
    private final HashMap<String, Sound> content;

    public ContentSound() {
        this.content = new HashMap<>();
    }

    public Sound getResource(String key) {
        return content.get(key);
    }

    @Override
    public void loadResource(String path, String key) {
        Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
        content.put(key, sound);
    }

    @Override
    public void disposeResources() {
        for (Sound sound : content.values()) {
            sound.dispose();
        }
    }
}
