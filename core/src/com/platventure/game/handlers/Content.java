package com.platventure.game.handlers;

public interface Content {
    void loadResource(String path, String key);
    void disposeResources();
}
