package com.platventure.game.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class InputListener extends InputAdapter {

    private int doubleTapSide;
    private float timerDblTap;

    public InputListener() {
        super();

        doubleTapSide = 0;
        timerDblTap = 0f;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode)
        {
            case Input.Keys.RIGHT:
                InputManager.setKey(InputManager.BUTTON_RIGHT, true);
                break;
            case Input.Keys.LEFT:
                InputManager.setKey(InputManager.BUTTON_LEFT, true);
                break;
            case Input.Keys.UP:
                InputManager.setKey(InputManager.BUTTON_UP, true);
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode)
        {
            case Input.Keys.RIGHT:
                InputManager.setKey(InputManager.BUTTON_RIGHT, false);
                break;
            case Input.Keys.LEFT:
                InputManager.setKey(InputManager.BUTTON_LEFT, false);
                break;
            case Input.Keys.UP:
                InputManager.setKey(InputManager.BUTTON_UP, false);
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (timerDblTap < 0.3f) {
            if (screenX < Gdx.graphics.getWidth()/2f && doubleTapSide == 0)
                InputManager.setKey(InputManager.BUTTON_UP, true);
            else if (screenX >= Gdx.graphics.getWidth()/2f && doubleTapSide == 1)
                InputManager.setKey(InputManager.BUTTON_UP, true);
        }
        if (screenX < Gdx.graphics.getWidth()/2f) {
            InputManager.setKey(InputManager.BUTTON_LEFT, true);
            doubleTapSide = 0;
        } else {
            InputManager.setKey(InputManager.BUTTON_RIGHT, true);
            doubleTapSide = 1;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        timerDblTap = 0f;
        InputManager.setKey(InputManager.BUTTON_UP, false);
        InputManager.setKey(InputManager.BUTTON_LEFT, false);
        InputManager.setKey(InputManager.BUTTON_RIGHT, false);
        return false;
    }

    public void update(float dt) {
        timerDblTap += dt;
    }
}
