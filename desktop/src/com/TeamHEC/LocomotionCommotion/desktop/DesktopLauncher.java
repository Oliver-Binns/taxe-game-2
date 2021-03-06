package com.TeamHEC.LocomotionCommotion.desktop;

import com.TeamHEC.LocomotionCommotion.GameData;
import com.TeamHEC.LocomotionCommotion.LocomotionCommotion;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(LocomotionCommotion.getInstance(), config);
		config.height= 1050;
		config.width = 1680;
		config.fullscreen = false;
		config.samples = 4;
		config.vSyncEnabled = true;
		config.addIcon("Icon.png", Files.FileType.Internal);
	}
}