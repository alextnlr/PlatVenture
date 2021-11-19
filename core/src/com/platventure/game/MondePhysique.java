package com.platventure.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class MondePhysique {
    private int[] tailleLevel;
    private int timeLevel;

    private char[][] mapLevel;
    private String background;

    private World world;

    public MondePhysique() {
        world = new World(new Vector2(0, -10f), true);

        getLevelInfos("level_001.txt");

        for (int i = 0; i < tailleLevel[1]; i++) {
            for (int j = 0; j < tailleLevel[0]; j++) {

            }
        }
    }

    private void getLevelInfos(String level) {
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

    public World getWorld() {
        return world;
    }
}
