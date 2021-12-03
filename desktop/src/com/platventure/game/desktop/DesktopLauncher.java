package com.platventure.game.desktop;

import static com.platventure.game.PlatVenture.SCALE;
import static com.platventure.game.PlatVenture.TITLE;
import static com.platventure.game.PlatVenture.V_HEIGHT;
import static com.platventure.game.PlatVenture.V_WIDTH;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.platventure.game.PlatVenture;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = TITLE;
		config.width = V_WIDTH*SCALE;
		config.height = V_HEIGHT*SCALE;
		new LwjglApplication(new PlatVenture(), config);
	}
}
