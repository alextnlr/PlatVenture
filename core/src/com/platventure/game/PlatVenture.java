package com.platventure.game;

import static com.platventure.game.GlobalVariables.PPM;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platventure.game.handlers.ContentSound;
import com.platventure.game.handlers.ContentTexture;
import com.platventure.game.handlers.GameStateManager;
import com.platventure.game.handlers.InputListener;
import com.platventure.game.handlers.InputManager;


//TODO Etape 0 :
// - Diagramme UML

//TODO Etape 3 : Habillage des objets
// - Texture des elements non animés v
// - Animation des gems v
// - Images spe pour le joueur selon action
// - Animation complete joueur


public class PlatVenture extends ApplicationAdapter {

	public static final String TITLE = "PlatVenture";
	//public static final int V_WIDTH = Gdx.graphics.getWidth();
	//public static final int V_HEIGHT = Gdx.graphics.getHeight();
	public static final int SCALE = 1;

	public static final float STEP = 1/60.f;
	private float accum;

	private SpriteBatch batch;
	private SpriteBatch batchHud;
	private OrthographicCamera camera;
	private OrthographicCamera camTexture;
	private OrthographicCamera camHud;

	private GameStateManager gsm;

	public static ContentTexture res;
	public static ContentSound resSound;

	public static InputListener inputListener;

	@Override
	public void create () {

		inputListener = new InputListener();
		Gdx.input.setInputProcessor(inputListener);

		loadTextures();
		loadSound();

		batch = new SpriteBatch();
		batchHud = new SpriteBatch();

		if (GlobalVariables.DEBUG) {
			camera = new OrthographicCamera();
			camera.setToOrtho(true, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
		}

		camTexture = new OrthographicCamera();
		camTexture.setToOrtho(false, Gdx.graphics.getWidth()/PPM, Gdx.graphics.getHeight()/PPM);
		camHud = new OrthographicCamera();
		camHud.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		gsm = new GameStateManager(this);
	}

	@Override
	public void render () {
		accum += Gdx.graphics.getDeltaTime();

		while (accum >= STEP) {
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
			InputManager.update();
		}

	}

	@Override
	public void dispose () {
		batch.dispose();
		res.disposeResources();
		resSound.disposeResources();
	}

	public void goToPlay() { gsm.setState(GameStateManager.PLAY); }
	public SpriteBatch getBatch() { return batch; }
	public OrthographicCamera getCamera() { return camera; }
	public OrthographicCamera getCamTexture() { return camTexture; }
	public OrthographicCamera getCamHud() { return camHud; }
	public SpriteBatch getBatchHud() { return batchHud;	}

	public ContentTexture getRes() { return res; }
	public ContentSound getResSound() { return resSound; }

	private void loadTextures() {
		res = new ContentTexture();

		res.loadResource("images/Back.png", "back");
		res.loadResource("images/Brick_A.png", "brickA");
		res.loadResource("images/Brick_B.png", "brickB");
		res.loadResource("images/Brick_C.png", "brickC");
		res.loadResource("images/Brick_D.png", "brickD");
		res.loadResource("images/Brick_E.png", "brickE");
		res.loadResource("images/Brick_F.png", "brickF");
		res.loadResource("images/Brick_G.png", "brickG");
		res.loadResource("images/Brick_H.png", "brickH");
		res.loadResource("images/Brick_I.png", "brickI");
		res.loadResource("images/Platform_J.png", "platJ");
		res.loadResource("images/Platform_K.png", "platK");
		res.loadResource("images/Platform_L.png", "platL");
		res.loadResource("images/Water.png", "water");
		res.loadResource("images/Exit_Z.png", "sortie");
		res.loadResource("images/Gem_1.png", "gem1");
		res.loadResource("images/Gem_2.png", "gem2");
		res.loadResource("images/Intro.png", "intro");
	}

	private void loadSound() {
		resSound = new ContentSound();

		resSound.loadResource("sounds/alert.ogg", "alert");
		resSound.loadResource("sounds/collision.ogg", "collision");
		resSound.loadResource("sounds/gem.ogg", "gem");
		resSound.loadResource("sounds/loose.ogg", "loose");
		resSound.loadResource("sounds/plouf.ogg", "plouf");
		resSound.loadResource("sounds/win.ogg", "win");
	}
}
