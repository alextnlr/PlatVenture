package com.platventure.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.platventure.game.fabriques.FabriqueBrique;
import com.platventure.game.fabriques.FabriquePlatCentre;
import com.platventure.game.fabriques.FabriquePlatDroite;
import com.platventure.game.fabriques.FabriquePlatGauche;


//TODO Etape 0 :
// - Diagramme UML

//TODO Etape 1 : Affichages en mode DebugRenderer
// - Definition camera du monde v
// - Initialisation du moteur physique v
// - Lecture du fichier de niveau avec constructeur de Body v
// - Construction du Joueur v
// - Controles du joueur
// - Etat du jeu (score..)
// - Detection des contacts

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
	SpriteBatch batch;
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;
	private MondePhysique mondePhysique;
	Joueur joueur;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 0);

		debugRenderer = new Box2DDebugRenderer();

		mondePhysique = new MondePhysique("level_001.txt");

		joueur = new Joueur();

		mondePhysique.drawWorld(joueur);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		mondePhysique.getWorld().step(Gdx.graphics.getDeltaTime(), 6, 2);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			joueur.getBody().applyLinearImpulse(new Vector2(joueur.getBody().getMass()*SizeUnit.getUnit(), 0f), joueur.getBody().getWorldCenter(), true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			joueur.getBody().applyLinearImpulse(new Vector2(-joueur.getBody().getMass()*SizeUnit.getUnit(), 0f), joueur.getBody().getWorldCenter(), true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			joueur.getBody().applyForceToCenter(new Vector2(0f, -40f*joueur.getBody().getMass()*SizeUnit.getUnit()), true);
		}

		batch.begin();
		debugRenderer.render(mondePhysique.getWorld(), camera.combined);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
