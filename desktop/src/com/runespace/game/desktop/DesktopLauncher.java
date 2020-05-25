package com.runespace.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.runespace.game.LaunchGame;
import com.runespace.game.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = Constants.WINDOW_HEIGHT;
		config.width = Constants.WINDOW_WIDTH;
		config.title = Constants.GAME_TITLE;
		new LwjglApplication(new LaunchGame(), config);
		
	}
}
