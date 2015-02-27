package com.TeamHEC.LocomotionCommotion.Mocking;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;


public class MockFileHandler implements Files {
	
	@Override
	public FileHandle getFileHandle(String path, FileType type) {
		throw new NotImplementedException();
	}

	@Override
	public FileHandle classpath(String path) {
		throw new NotImplementedException();
	}

	@Override
	public FileHandle internal(String path) {
		return new LwjglFileHandle("assets/" + path, Files.FileType.Internal);
	}

	@Override
	public FileHandle external(String path) {
		throw new NotImplementedException();
	}

	@Override
	public FileHandle absolute(String path) {
		throw new NotImplementedException();
	}

	@Override
	public FileHandle local(String path) {
		throw new NotImplementedException();
	}

	@Override
	public String getExternalStoragePath() {
		throw new NotImplementedException();
	}

	@Override
	public boolean isExternalStorageAvailable() {
		throw new NotImplementedException();		
	}

	@Override
	public String getLocalStoragePath() {
		throw new NotImplementedException();
	}

	@Override
	public boolean isLocalStorageAvailable() {
		throw new NotImplementedException();		
	}

}
