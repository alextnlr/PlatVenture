package com.platventure.game.handlers;

import com.platventure.game.PlatVenture;
import com.platventure.game.states.GameState;
import com.platventure.game.states.Play;
import com.platventure.game.states.StartScreen;

import java.util.Stack;

public class GameStateManager {

    private PlatVenture platVenture;
    private Stack<GameState> gameStates;

    public static final int PLAY = 42;
    public static final int START = 69;

    public GameStateManager(PlatVenture platVenture) {
        this.platVenture = platVenture;
        this.gameStates = new Stack<GameState>();
        pushState(PLAY);
        pushState(START);
    }

    public PlatVenture getPlatVenture() { return platVenture; }

    public void update(float dt) {
        gameStates.peek().update(dt);
    }

    public void render() {
        gameStates.peek().render();
    }

    private GameState getState(int state) {
        if (state == PLAY) return new Play(this);
        if (state == START) return new StartScreen(this);
        return null;
    }

    public void setState(int state) {
        popState();
        pushState(state);
    }

    public void pushState(int state) {
        gameStates.push(getState(state));
    }

    public void popState() {
        GameState g = gameStates.pop();
        g.dispose();
    }
}
