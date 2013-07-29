package com.petrovdevelopment.dumanica;

import com.petrovdevelopment.dumanica.MainApplication;
import com.petrovdevelopment.dumanica.dialogs.*;
import com.petrovdevelopment.dumanica.model.Game;
import com.petrovdevelopment.dumanica.model.Preferences;
import com.petrovdevelopment.dumanica.threads.LoadModelCaller;
import com.petrovdevelopment.dumanica.threads.LoadModelTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

//import android.view.Menu;

/**
 * The main menu screen with options for a new game, instructions, etc.
 * Handles the result from the database loading of words.
 * @author andrey
 *
 */
public class MainMenuActivity extends Activity implements LoadModelCaller {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MainApplication app = ((MainApplication) getApplication());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		//Initialize the preferences, which is needed for the Options dialog and for the initializing the game state
		((MainApplication) getApplication()).getPreferences().init();
	}

	public void onStartClick(View view) {
		Log.i(this.getClass().getSimpleName(), "clicked start");
	}

	/**
	 * Start a new game
	 * @param view
	 */
	public void onNewGameClick(View view) {
		(new LoadModelTask()).execute(this);
	}
	
	public void onOptionsClick(View view) {
		OptionsDialog optionsDialog = new OptionsDialog();
		optionsDialog.show(getFragmentManager(), MainApplication.DIALOG);
	}
	
	public void onExitClick(View view) {
		onBackPressed();
	}

	/**
	 * Start the game once the model has been loaded asynchronously
	 */
	@Override
	public void onPostLoadModelExecute() {
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {
		ConfirmDialog confirmDialog = new ConfirmDialog();
		confirmDialog.show(getFragmentManager(), MainApplication.DIALOG);
	}
	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.main_menu, menu); return true; }
	 */
	
}
