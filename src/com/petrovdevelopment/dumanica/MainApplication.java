package com.petrovdevelopment.dumanica;

import android.app.Application;

import com.petrovdevelopment.dumanica.model.Game;
import com.petrovdevelopment.dumanica.utils.Preferences;

/**
 * Main application class. 
 * Extending Application to load the custom font asset
 * @author andrey
 *
 */
public class MainApplication extends Application{
	public static Game mGame;
	public static Preferences mPreferences;
	public static final String DIALOG = "dialog"; //title of the dialog fragment
	//public static final String PREFERENCES = "preferences";
	
	public MainApplication() {
		mPreferences = new Preferences(this);
		mGame = new Game(mPreferences);
	}
	
	public Game getGame() {
		return mGame;
	}
	
	public Preferences getPreferences() {
		return mPreferences;
	}
} 
