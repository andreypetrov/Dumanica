package com.petrovdevelopment.dumanica;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.petrovdevelopment.dumanica.dialogs.ConfirmDialog;
import com.petrovdevelopment.dumanica.model.Game;
import com.petrovdevelopment.dumanica.utils.U;
import com.petrovdevelopment.dumanica.views.Keyboard;
import com.petrovdevelopment.dumanica.views.Keyboard.OnButtonClickedListener;

public class GameActivity extends Activity {
	Game mGame;
	Keyboard mKeyboard;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mGame = ((MainApplication) getApplication()).getGame();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);	
		initKeyboard();
		initGame();
	}

	private void initKeyboard() {
		mKeyboard = (Keyboard) findViewById(R.id.keyboard);
		mKeyboard.setOnButtonClickedListener(new OnButtonClickedListener() {
			@Override
			public void onButtonClicked(Button button,	String letter) {
				//endGame();
				mGame.update(letter);
				updateUi();
				U.log(this, mGame.getPercentageSuccess());
			}
		});
	}

	/**
	 * Execute only once, when the activity has been created
	 */
	private void initGame() {
		mGame.start();
		updateUi();
	}

	private void initWordBar() {
		LinearLayout wordBar = (LinearLayout) findViewById(R.id.wordBar);
		wordBar.removeAllViews(); //clear existing views
		
		int lettersCount = mGame.getCurrentWordVM().getWord().length();

		for (int i = 0; i < lettersCount; i++){
		   //create a view and add it to the wordBar
			//TODO remove the letters from here, it should be empty
			wordBar.addView(createWordBarLetter(String.valueOf(mGame.getCurrentWordVM().getWord().charAt(i))));
			//wordBar.addView(createWordBarLetter(""));
		}
	}
	
	//create every letter for the main hangman hidden word
	private TextView createWordBarLetter(String label) {
		TextView textView = new TextView(this);
		textView.setBackgroundResource(R.drawable.green_circle);
		textView.setText(label);
		return textView;
	}

	
	
	//Execute in the beginning and then on every letter guess
	//If there are animations, they should be executed async
	public void updateUi() {
		if(mGame.isFinished()) endGame();
		else if (mGame.isNewRound()) initRoundUi();
		else updateRoundUi();
	}
	
	//execute every time a new round begins
	public void initRoundUi () {
		mKeyboard.reset();
		//TODO extract next three UI methods in separate views that will have animations
		initWordBar();
		updateWordsLeftCount(mGame.getWordsLeft());
		updateAttemptsLeftCount(mGame.getCurrentWordVM().getAttemptsLeft());
	}


	
	private void updateRoundUi() {
		updateAttemptsLeftCount(mGame.getCurrentWordVM().getAttemptsLeft());
		updatePoints();
		updateWordBar();
	}

	private void updatePoints() {
		TextView pointsView = (TextView) findViewById(R.id.points);
		pointsView.setText(String.valueOf(mGame.getPoints()));
	}
	
	private void updateWordBar() {
		// TODO Auto-generated method stub
		
	}


	public void updateAttemptsLeftCount(int count) {
		TextView attempts = (TextView) findViewById(R.id.attemptsLeftCount);
		attempts.setText(getString(R.string.attemptsLeftCount) + count);
	}
	
    public void updateWordsLeftCount(int count) {
    	TextView words = (TextView) findViewById(R.id.wordsLeftCount);
    	words.setText(getString(R.string.wordsLeftCount) + count);
	}
    
	private void endGame() {
		Intent intent = new Intent(this, EndGameActivity.class);
		//intent.putExtra(EndGameActivity.FINAL_POINTS_EXTRA, mGame.getPoints());
		intent.putExtra(EndGameActivity.FINAL_POINTS_EXTRA, 50);
		intent.putExtra(EndGameActivity.FINAL_PERCENT_EXTRA, mGame.getPercentageSuccess());
		startActivity(intent);
		finish();
	}

	@Override
	public void onBackPressed() {
		ConfirmDialog confirmDialog = new ConfirmDialog();
		confirmDialog.show(getFragmentManager(), MainApplication.DIALOG);
	}

}
