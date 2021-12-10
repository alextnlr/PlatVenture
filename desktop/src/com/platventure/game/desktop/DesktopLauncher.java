package com.platventure.game.desktop;

import static com.platventure.game.PlatVenture.TITLE;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.platventure.game.PlatVenture;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = TITLE;
		config.width = 1024;
		config.height = 768;
		new LwjglApplication(new PlatVenture(), config);
	}
}
