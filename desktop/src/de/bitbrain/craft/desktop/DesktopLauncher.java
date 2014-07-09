package de.bitbrain.craft.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.bitbrain.craft.CraftGame;
import de.bitbrain.craft.Settings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Settings.WIDTH;
		config.height = Settings.HEIGHT;
		config.title = Settings.NAME + " v. " + Settings.VERSION +  " (" + Settings.PHASE + ")";
		new LwjglApplication(new CraftGame(), config);
	}
}
