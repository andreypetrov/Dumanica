package com.petrovdevelopment.dumanica;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.petrovdevelopment.dumanica.model.Game;
import com.petrovdevelopment.dumanica.model.Word;
import com.petrovdevelopment.dumanica.viewmodel.WordVM;

public class GameActivity extends Activity {
	Game mGame;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mGame = ((MainApplication) getApplication()).getGame();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		//TextView testTextView = (TextView) findViewById(R.id.testTextView);
		
		//WordVM firstWord = mGame.getWordVM(0);
		//testTextView.setText(firstWord.getWord());
	}
}
