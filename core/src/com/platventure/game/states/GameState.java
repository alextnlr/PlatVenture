package com.platventure.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platventure.game.PlatVenture;
import com.platventure.game.handlers.GameStateManager;

public abstract class GameState {
    protected GameStateManager gsm;
    protected PlatVenture platVenture;

    protected SpriteBatch sb;
    protected OrthographicCamera camera;
    protected OrthographicCamera hudCam;

    protected GameState(GameStateManager gsm) {
        this.gsm = gsm;
        platVenture = gsm.getPlatVenture();
        sb = platVenture.getBatch();
        camera = platVenture.getCamera();
        hudCam = platVenture.getHubCam();
    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();
}
