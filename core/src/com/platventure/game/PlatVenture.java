package com.platventure.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Polygon;


//TODO Etape 0 :
// - Diagramme UML

//TODO Etape 1 : Affichages en mode DebugRenderer
// - Definition camera du monde
// - Initialisation du moteur physique
// - Lecture du fichier de niveau avec constructeur de Body
// - Construction du Joueur
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
	Texture img;
	private OrthographicCamera camera;
	private Body body, platform;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		world = new World(new Vector2(0, -10f), true);

		debugRenderer = new Box2DDebugRenderer();

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(0, 100);
		body = world.createBody(bodyDef);
		FixtureDef fixtureDef = new FixtureDef();
		//Vector2[] pts = {new Vector2(0f, 0f), new Vector2(0, 200f), new Vector2(200f, 0)};
		CircleShape shape = new CircleShape();
		shape.setRadius(20f);
		//shape.set(pts);
		fixtureDef.shape = shape;
		fixtureDef.density = 40f;
		fixtureDef.restitution = 0.1f;
		fixtureDef.friction = 0.5f;
		body.createFixture(fixtureDef);
		shape.dispose();

		BodyDef platDef = new BodyDef();
		platDef.type = BodyDef.BodyType.StaticBody;
		platDef.position.set(-100f, -200f);
		platform = world.createBody(platDef);
		Vector2[] pts2 = {new Vector2(0, 0), new Vector2(0, 100f), new Vector2(200f, 0)};
		PolygonShape shape2 = new PolygonShape();
		shape2.set(pts2);
		FixtureDef platFix = new FixtureDef();
		platFix.shape = shape2;
		platFix.friction = 0.8f;
		platform.createFixture(platFix);
		shape2.dispose();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		world.step(Gdx.graphics.getDeltaTime(), 6, 2);

		camera.position.set(body.getPosition().x, body.getPosition().y, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		debugRenderer.render(world, camera.combined);
		//batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
