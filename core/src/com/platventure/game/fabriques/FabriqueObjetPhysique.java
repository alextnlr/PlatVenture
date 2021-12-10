package com.platventure.game.fabriques;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.platventure.game.GlobalVariables;

public class FabriqueObjetPhysique {
    private FixtureDef fixtureDef;
    private static FabriqueObjetPhysique instance;

    private FabriqueObjetPhysique() {
        fixtureDef = new FixtureDef();
    }

    public static FixtureDef getFixture(char t) {
        if (instance == null) {
            instance = new FabriqueObjetPhysique();
        }

        switch (t) {
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
                createFixtureBrique();
                break;
            case 'J':
                createFixturePlatGauche();
                break;
            case 'K':
                createFixturePlatCentre();
                break;
            case 'L':
                createFixturePlatDroite();
                break;
            case 'W':
                createFixtureWater();
                break;
            case '1':
            case '2':
                createFixtureJoyaux();
                break;
            case 'Z':
                createFixtureSortie();
                break;
        }

        return instance.fixtureDef;
    }

    public static FixtureDef createFixtureBrique() {
        initFixture();

        Vector2[] pts = {new Vector2(0, 0), new Vector2(1.f, 0f),
                new Vector2(1.f, 1f), new Vector2(0f, 1f)};
        PolygonShape shape = new PolygonShape();
        shape.set(pts);
        instance.fixtureDef.shape = shape;

        instance.fixtureDef.isSensor = false;

        return instance.fixtureDef;
    }

    public static FixtureDef createFixturePlatGauche() {
        initFixture();

        Vector2[] pts = {new Vector2(0, 0.25f), new Vector2(1.f, 0.25f),
                new Vector2(1.f, 1f),new Vector2(0.5f, 1f) , new Vector2(0, 0.75f)};
        PolygonShape shape = new PolygonShape();
        shape.set(pts);
        instance.fixtureDef.shape = shape;

        instance.fixtureDef.isSensor = false;

        return instance.fixtureDef;
    }

    public static FixtureDef createFixturePlatCentre() {
        initFixture();

        Vector2[] pts = {new Vector2(0, 0.25f), new Vector2(1.f, 0.25f),
                new Vector2(1.f, 1f), new Vector2(0, 1f)};
        PolygonShape shape = new PolygonShape();
        shape.set(pts);
        instance.fixtureDef.shape = shape;

        instance.fixtureDef.isSensor = false;

        return instance.fixtureDef;
    }

    public static FixtureDef createFixturePlatDroite() {
        initFixture();

        Vector2[] pts = {new Vector2(0, 0.25f), new Vector2(1.f, 0.25f),
                new Vector2(1.f, 0.75f),new Vector2(0.5f, 1f) , new Vector2(0, 1f)};
        PolygonShape shape = new PolygonShape();
        shape.set(pts);
        instance.fixtureDef.shape = shape;

        instance.fixtureDef.isSensor = false;

        return instance.fixtureDef;
    }

    public static FixtureDef createFixtureWater() {
        initFixture();

        Vector2[] pts = {new Vector2(0, 0.25f), new Vector2(1.f, 0.25f),
                new Vector2(1.f, 1.f), new Vector2(0, 1.f)};
        PolygonShape shape = new PolygonShape();
        shape.set(pts);
        instance.fixtureDef.shape = shape;

        instance.fixtureDef.isSensor = true;

        return instance.fixtureDef;
    }

    public static FixtureDef createFixtureJoyaux() {
        initFixture();

        CircleShape shape = new CircleShape();
        shape.setRadius(0.2f);
        shape.setPosition(new Vector2(0.5f, 0.5f));
        instance.fixtureDef.shape = shape;

        instance.fixtureDef.isSensor = true;

        return instance.fixtureDef;
    }

    public static FixtureDef createFixtureSortie() {
        initFixture();

        Vector2[] pts = {new Vector2(0, 0), new Vector2(1.f, 0f),
                new Vector2(1.f, 1f), new Vector2(0f, 1f)};
        PolygonShape shape = new PolygonShape();
        shape.set(pts);
        instance.fixtureDef.shape = shape;

        instance.fixtureDef.isSensor = true;

        return instance.fixtureDef;
    }

    private static void initFixture() {
        if (instance == null) {
            instance = new FabriqueObjetPhysique();
        }

        instance.fixtureDef.density = 1f;
        instance.fixtureDef.restitution = 0.1f;
        instance.fixtureDef.friction = 0.25f;
        instance.fixtureDef.filter.categoryBits = GlobalVariables.BIT_GROUND;
        instance.fixtureDef.filter.maskBits = GlobalVariables.BIT_PLAYER;
    }
}
