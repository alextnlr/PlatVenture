package com.platventure.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.platventure.game.handlers.Content;
import com.platventure.game.handlers.GameStateManager;

public class StartScreen extends GameState{

    private float timer;

    public StartScreen(GameStateManager gsm) {
        super(gsm);

        timer = 0;
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
        if (timer > 3f) {
            timer = 0;
            platVenture.goToPlay();
        } else {
            timer += dt;
        }
    }

    @Override
    public void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cameraHud.combined);
        sb.begin();
        sb.draw(res.getTexture("intro"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.end();
    }

    @Override
    public void dispose() {
    }
}
