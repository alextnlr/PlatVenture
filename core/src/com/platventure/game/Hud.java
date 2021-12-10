package com.platventure.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Hud {
    private BitmapFont font;
    private int time;
    private int score;
    private int etat;

    public Hud() {
        score = 0;
        etat = 0;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Comic_Sans_MS_Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (60*((int) Gdx.graphics.getWidth()/1024f));
        Color yellow = new Color();
        yellow.set(255, 255, 0, 0.75f);
        parameter.color = yellow;
        parameter.borderWidth = (int) (3*((int) Gdx.graphics.getWidth()/1024f));
        parameter.borderColor = Color.BLACK;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    public void update(int time, int score, int etat) {
        this.time = time;
        this.score = score;
        this.etat = etat;
    }

    public void render(SpriteBatch sb) {
        sb.begin();
        font.draw(sb, ""+time, Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight());
        GlyphLayout layout = new GlyphLayout(font, "Score: "+score);
        font.draw(sb, "Score: "+score, Gdx.graphics.getWidth()-layout.width, Gdx.graphics.getHeight());
        if (etat == 1) {
            GlyphLayout layout1 = new GlyphLayout(font, "Dommage :/");
            font.draw(sb, "Dommage :/", (Gdx.graphics.getWidth()- layout1.width)/2f, Gdx.graphics.getHeight()/2f);
        } else if (etat == 2) {
            GlyphLayout layout1 = new GlyphLayout(font, "Bravo :)");
            font.draw(sb, "Bravo :)", (Gdx.graphics.getWidth()- layout1.width)/2f, Gdx.graphics.getHeight()/2f);
        }
        sb.end();
    }
}
