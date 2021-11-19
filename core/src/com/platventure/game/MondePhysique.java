package com.platventure.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.platventure.game.fabriques.FabriqueBrique;
import com.platventure.game.fabriques.FabriqueEau;
import com.platventure.game.fabriques.FabriqueJoyau;
import com.platventure.game.fabriques.FabriquePlatCentre;
import com.platventure.game.fabriques.FabriquePlatDroite;
import com.platventure.game.fabriques.FabriquePlatGauche;
import com.platventure.game.fabriques.FabriqueObjetPhysique;
import com.platventure.game.fabriques.FabriqueSortie;

public class MondePhysique {
    private int[] tailleLevel;
    private int timeLevel;

    private char[][] mapLevel;
    private String background;

    private final FabriqueObjetPhysique[] fabriques;
    private Joueur joueur;

    private final World world;

    public MondePhysique(String level) {
        world = new World(new Vector2(0, 10f*SizeUnit.getUnit()), true);

        getLevelInfos(level);

        fabriques = new FabriqueObjetPhysique[7];
        fabriques[0] = new FabriqueBrique();
        fabriques[1] = new FabriquePlatGauche();
        fabriques[2] = new FabriquePlatCentre();
        fabriques[3] = new FabriquePlatDroite();
        fabriques[4] = new FabriqueEau();
        fabriques[5] = new FabriqueJoyau();
        fabriques[6] = new FabriqueSortie();

        joueur = new Joueur();

        for (int i = 0; i < tailleLevel[1]; i++) {
            for (int j = 0; j < tailleLevel[0]; j++) {
                createUnit(mapLevel[i][j], j, i);
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

    private void createUnit(char t, int x, int y) {
        switch (t) {
            case 'J':
                fabriques[1].createBody(world, x, y);
                break;

            case 'K':
                fabriques[2].createBody(world, x, y);
                break;

            case 'L':
                fabriques[3].createBody(world, x, y);
                break;

            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'I':
                fabriques[0].createBody(world, x, y);
                break;

            case 'P':
                joueur.createBody(world, x, y);
                break;

            case 'W':
                fabriques[4].createBody(world, x, y);
                break;

            case '1':
            case '2':
                fabriques[5].createBody(world, x, y);
                break;

            case 'Z':
                fabriques[6].createBody(world, x, y);
                break;
        }
    }
}
