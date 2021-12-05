package com.platventure.game;

import static com.platventure.game.GlobalVariables.PPM;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.platventure.game.handlers.Content;
import com.platventure.game.handlers.GameStateManager;
import com.platventure.game.handlers.InputListener;
import com.platventure.game.handlers.InputManager;


//TODO Etape 0 :
// - Diagramme UML

//TODO Etape 1 : Affichages en mode DebugRenderer v
// - Definition camera du monde v
// - Initialisation du moteur physique v
// - Lecture du fichier de niveau avec constructeur de Body v
// - Construction du Joueur v
// - Controles du joueur v
// - Etat du jeu (score..) v
// - Detection des contacts v

//TODO Etape 2 : Affichages des infos du jeu et gestion de partie
// - Definition camera pour les textes
// - Affichage score et temps
// - Detection perte et victoire
// - Relance et chargement de niveau

//TODO Etape 3 : Habillage des objets
// - Texture des elements non animés
// - Animation des gems
// - Imaes spe pour le joueur selon action
// - Animation complete joueur


public class PlatVenture extends ApplicationAdapter {

	public static final String TITLE = "PlatVenture";
	public static final int V_WIDTH = 1024;
	public static final int V_HEIGHT = 768;
	public static final int SCALE = 1;

	public static final float STEP = 1/60.f;
	private float accum;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private OrthographicCamera hudCam;

	private GameStateManager gsm;

	private Content res;

	InputListener inputListener;

	@Override
	public void create () {

		inputListener = new InputListener();
		Gdx.input.setInputProcessor(inputListener);

		res = new Content();
		res.loadTexture("images/Back.png", "back");
		res.loadTexture("images/Brick_A.png", "brickA");
		res.loadTexture("images/Brick_B.png", "brickB");
		res.loadTexture("images/Brick_C.png", "brickC");
		res.loadTexture("images/Brick_D.png", "brickD");
		res.loadTexture("images/Brick_E.png", "brickE");
		res.loadTexture("images/Brick_F.png", "brickF");
		res.loadTexture("images/Brick_G.png", "brickG");
		res.loadTexture("images/Brick_H.png", "brickH");
		res.loadTexture("images/Brick_I.png", "brickI");
		res.loadTexture("images/Platform_J.png", "platJ");
		res.loadTexture("images/Platform_K.png", "platK");
		res.loadTexture("images/Platform_L.png", "platL");
		res.loadTexture("images/Water.png", "water");
		res.loadTexture("images/Exit_Z.png", "sortie");
		res.loadTexture("images/Gem_1.png", "gem1");
		res.loadTexture("images/Gem_2.png", "gem2");

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, V_WIDTH/PPM, V_HEIGHT/PPM);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH/PPM, V_HEIGHT/PPM);

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
	}

	public SpriteBatch getBatch() { return batch; }
	public OrthographicCamera getCamera() { return camera; }
	public OrthographicCamera getHudCam() { return hudCam; }
	public Content getRes() { return res; }
}
