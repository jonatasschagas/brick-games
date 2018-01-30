package com.jc.software.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jc.software.BrickGames;
import com.jc.software.GameConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameConfiguration.SCREEN_WIDTH;
		config.height = GameConfiguration.SCREEN_HEIGHT;
		new LwjglApplication(new BrickGames(), config);
	}
}
