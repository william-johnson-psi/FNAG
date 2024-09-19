package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.*;
import com.mygdx.game.Main;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60); 
                config.setWindowedMode(1080, 720);
                config.setTitle("Five Nights at Gully's");
                config.setWindowIcon(Files.FileType.Internal, "favicon.png");
		new Lwjgl3Application(new Main(), config);    
	}
}


// The Tablet That shows up when I hover my mouse at the bottom will be the transition to the camera mode,
// Cameras will show different Room Classes with various information the player can
// Grab Information From.