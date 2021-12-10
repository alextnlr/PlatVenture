package com.platventure.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platventure.game.GlobalVariables;
import com.platventure.game.PlatVenture;
import com.platventure.game.handlers.ContentSound;
import com.platventure.game.handlers.ContentTexture;
import com.platventure.game.handlers.GameStateManager;

public abstract class GameState {
    protected GameStateManager gsm;
    protected PlatVenture platVenture;

    protected SpriteBatch sb;
    protected SpriteBatch sbHud;
    protected OrthographicCamera cameraDebug;
    protected OrthographicCamera cameraTexture;
    protected OrthographicCamera cameraHud;
    protected ContentTexture res;
    protected ContentSound resSound;

    protected GameState(GameStateManager gsm) {
        this.gsm = gsm;
        platVenture = gsm.getPlatVenture();

        sb = platVenture.getBatch();
        sbHud = platVenture.getBatchHud();

        if (GlobalVariables.DEBUG)
            cameraDebug = platVenture.getCamera();
        cameraTexture = platVenture.getCamTexture();
        cameraHud = platVenture.getCamHud();

        res = platVenture.getRes();
        resSound = platVenture.getResSound();
    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();
}
