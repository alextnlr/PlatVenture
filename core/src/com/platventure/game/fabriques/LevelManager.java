package com.platventure.game.fabriques;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.platventure.game.Level;
import com.platventure.game.fabriques.FabriqueObjetPhysique;

import java.util.HashMap;

public class LevelManager {
    private HashMap<Integer, Level> levels;
    private int currentLevel;

    public LevelManager() {
        currentLevel = 1;

        levels = new HashMap<>();

        levels.put(1, new Level("levels/level_001.txt"));
        levels.put(2, new Level("levels/level_002.txt"));
        levels.put(3, new Level("levels/level_003.txt"));
    }

    public char getCurrentMap(int x, int y) {
        return levels.get(currentLevel).getMap()[x][y];
    }

    public int getCurrentSize(int x) {
        return levels.get(currentLevel).getSize()[x];
    }

    public int getCurrentTime() {
        return levels.get(currentLevel).getTime();
    }

    public String getCurrentBackground() {
        return levels.get(currentLevel).getBackground();
    }
}
