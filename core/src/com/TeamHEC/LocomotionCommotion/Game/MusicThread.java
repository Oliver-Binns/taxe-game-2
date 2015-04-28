package com.TeamHEC.LocomotionCommotion.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicThread extends Thread {
	private Music mainMusic;
	
	public MusicThread(String filepath) {
		mainMusic = Gdx.audio.newMusic(Gdx.files.internal(filepath));
		mainMusic.setLooping(true);
	}
	
	public void start() {
		mainMusic.play();
	}
}
