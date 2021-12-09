package com.platventure.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Timer;
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
            if(timer==0) {
                long id = resSound.getResource("win").play();
                System.out.println(id);
                resSound.getResource("win").setVolume(id, 1f);
            }
            timer += dt;
        }
    }

    @Override
    public void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.setProjectionMatrix(cameraHud.combined);
        sb.begin();
        sb.draw(res.getResource("intro"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.end();
    }

    @Override
    public void dispose() {
    }
}
