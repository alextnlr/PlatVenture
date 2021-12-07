package com.platventure.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Level {
    private char[][] map;
    private int[] size;
    private int time;
    private String background;

    public Level(String path) {
        if (Gdx.files.internal(path).exists()) {
            FileHandle file = Gdx.files.internal(path);
            String fileString = file.readString();
            String[] fileStringArray = fileString.split("\\r?\\n");

            size = new int[2];
            size[0] = Integer.parseInt(fileStringArray[0].split(" ")[0]);
            size[1] = Integer.parseInt(fileStringArray[0].split(" ")[1]);
            time = Integer.parseInt(fileStringArray[0].split(" ")[2]);

            map = new char[size[1]][size[0]];
            for (int i = 1; i <= size[1]; i++) {
                for (int j = 0; j < size[0]; j++) {
                    map[i-1][j] = fileStringArray[i].charAt(j);
                }
            }

            background = fileStringArray[size[1]+1];
        }
    }

    public char[][] getMap() {
        return map;
    }

    public int getTime() {
        return time;
    }

    public int[] getSize() {
        return size;
    }

    public String getBackground() {
        return background;
    }
}
