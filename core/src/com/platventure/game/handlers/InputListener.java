package com.platventure.game.handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.platventure.game.Joueur;

public class InputListener extends InputAdapter {

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
}
