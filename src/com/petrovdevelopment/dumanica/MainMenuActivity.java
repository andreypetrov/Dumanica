package com.petrovdevelopment.dumanica;

import com.petrovdevelopment.dumanica.MainApplication;
import com.petrovdevelopment.dumanica.dialogs.*;
import com.petrovdevelopment.dumanica.threads.LoadModelCaller;
import com.petrovdevelopment.dumanica.threads.LoadModelTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * The main menu screen with options for a new game, instructions, etc.
 * Handles the result from the database loading of words.
 * @author andrey
 *
 */
public class MainMenuActivity extends Activity implements LoadModelCaller {
	
	View mNewGameButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		//Initialize the preferences, which is needed for the Options dialog and for the initializing the game state
		((MainApplication) getApplication()).getPreferences().init();
	}

	/**
	 * Start a new game
	 * @param view
	 */
	public void onNewGameClick(View view) {
		mNewGameButton = view;
		toggleAllButtons();
		(new LoadModelTask(this)).execute(); 
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
		toggleAllButtons();
	}
	
	@Override
	public void onBackPressed() {
		ConfirmDialog confirmDialog = new ConfirmDialog();
		confirmDialog.show(getFragmentManager(), MainApplication.DIALOG);
	}
	
	//TODO do this for all buttons, not only for mNewGameButton
	private void toggleAllButtons() {
		mNewGameButton.setEnabled(!mNewGameButton.isEnabled());
	}
	
}
