package com.petrovdevelopment.dumanica;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.petrovdevelopment.dumanica.dialogs.ConfirmDialog;
import com.petrovdevelopment.dumanica.model.Game;
import com.petrovdevelopment.dumanica.views.Keyboard;
import com.petrovdevelopment.dumanica.views.Keyboard.OnButtonClickedListener;
import com.petrovdevelopment.dumanica.views.PlaceholdersBar;
import com.petrovdevelopment.dumanica.views.Statistics;

public class GameActivity extends Activity {
	Game mGame;
	Keyboard mKeyboard;
	PlaceholdersBar mPlaceholdersBar;
	TextView mHint;
	Statistics mStatistics;
	TextView mPointsView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mGame = ((MainApplication) getApplication()).getGame();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);	

		mGame.start();
		initUi();
		
	}
	
	private void initUi() {
		initPoints();
		initStatistics();
		initPlaceholdersBar();
		initHints();
		initKeyboard();
	}
	


	/**
	 * Execute on every letter guess
	 * If there are animations, they should be executed async
	 * @param letter
	 */
	private void updateUi(String letter) {
		if(mGame.isFinished()) endGame();
		else if (mGame.isNewRound()) resetRoundUi();
		else updateRoundUi(letter);
	}


	private void initPoints() {
		mPointsView = (TextView) findViewById(R.id.points);
		mPointsView.setText(String.valueOf(0));
	}
	
	private void initKeyboard() {
		mKeyboard = (Keyboard) findViewById(R.id.keyboard);
		mKeyboard.setOnButtonClickedListener(new OnButtonClickedListener() {
			@Override
			public void onButtonClicked(Button button,	String letter) {
				//endGame();
				mGame.update(letter);
				updateUi(letter);
			}
		});
	}
	
	private void initStatistics() {
		mStatistics = (Statistics) findViewById(R.id.statistics);
		mStatistics.init(mGame.getAttemptsCountPerWord(), mGame.getWordsCountPerGame());
	}

	//execute every time a new round begins
	private void resetRoundUi () {
		mKeyboard.reset();
		mStatistics.updateWordsAndResetAttempts();
		initPlaceholdersBar();
		initHints();
		updatePoints();
	}

	private void initPlaceholdersBar() {
		mPlaceholdersBar = (PlaceholdersBar) findViewById(R.id.placeholdersRow);
		mPlaceholdersBar.initFromWord(mGame.getCurrentWordVM().getWord());
	}

	private void initHints() {
		mHint = (TextView) findViewById(R.id.hint);
		mHint.setText(mGame.getCurrentWordVM().getHint());
	}

	private void updateRoundUi(String letter) {
		updatePoints();
		if(!mGame.isLastLetterGuessCorrect()) mStatistics.updateAttempts();
		mPlaceholdersBar.update(letter);
	}

	private void updatePoints() {
		mPointsView.setText(String.valueOf(mGame.getPoints()));
	}

	private void endGame() {
		Intent intent = new Intent(this, EndGameActivity.class);
		intent.putExtra(EndGameActivity.FINAL_POINTS_EXTRA, mGame.getPoints());
		//intent.putExtra(EndGameActivity.FINAL_POINTS_EXTRA, 50);
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
