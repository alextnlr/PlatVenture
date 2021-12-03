package com.platventure.game.fabriques;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.platventure.game.fabriques.FabriqueObjetPhysique;

public class LevelManager {
    private int[] tailleLevel;
    private int timeLevel;

    private char[][] mapLevel;
    private String background;

    public LevelManager(String level) {
        if (Gdx.files.internal(level).exists()) {
            FileHandle file = Gdx.files.internal(level);
            String fileString = file.readString();
            String[] fileStringArray = fileString.split("\\r?\\n");

            tailleLevel = new int[2];
            tailleLevel[0] = Integer.parseInt(fileStringArray[0].split(" ")[0]);
            tailleLevel[1] = Integer.parseInt(fileStringArray[0].split(" ")[1]);
            timeLevel = Integer.parseInt(fileStringArray[0].split(" ")[2]);

            mapLevel = new char[tailleLevel[1]][tailleLevel[0]];
            for (int i = 1; i <= tailleLevel[1]; i++) {
                for (int j = 0; j < tailleLevel[0]; j++) {
                    mapLevel[i-1][j] = fileStringArray[i].charAt(j);
                }
            }

            background = fileStringArray[tailleLevel[1]+1];
        }
    }

    public char[][] getLevelInfos() {
        return mapLevel;
    }

    public int[] getTailleLevel() {
        return tailleLevel;
    }

    public int getTimeLevel() {
        return timeLevel;
    }
}
