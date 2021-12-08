package com.platventure.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platventure.game.PlatVenture;
import com.platventure.game.handlers.Content;
import com.platventure.game.handlers.GameStateManager;

public abstract class GameState {
    protected GameStateManager gsm;
    protected PlatVenture platVenture;

    protected SpriteBatch sb;
    protected SpriteBatch sbHud;
    protected OrthographicCamera cameraDebug;
    protected OrthographicCamera cameraTexture;
    protected OrthographicCamera cameraHud;
    protected Content res;

    protected GameState(GameStateManager gsm) {
        this.gsm = gsm;
        platVenture = gsm.getPlatVenture();

        sb = platVenture.getBatch();
        sbHud = platVenture.getBatchHud();

        cameraDebug = platVenture.getCamera();
        cameraTexture = platVenture.getCamTexture();
        cameraHud = platVenture.getCamHud();

        res = platVenture.getRes();
    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();
}
