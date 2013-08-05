package com.petrovdevelopment.dumanica.dialogs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.petrovdevelopment.dumanica.MainApplication;
import com.petrovdevelopment.dumanica.R;
import com.petrovdevelopment.dumanica.model.Preferences;

/**
 * Dialog giving access to the options the game
 * @author andrey
 * 
 */
public class OptionsDialog extends ConfirmDialog{
	Preferences mPreferences;
	
	//public static final String WORDS_COUNT = "words_count";
	//public static final String ATTEMPTS_COUNT = "attempts_count";
	//public static final int DEFAULT_WORDS_COUNT = 4;
	//public static final int DEFAULT_ATTEMPTS_COUNT = 6;
	
	RadioGroup mWordsCount;
	RadioGroup mAttemptsCount;
	
	View word1;
	View word2;
	View word3;
	View word4;
	
	View attempt1;
	View attempt2;
	View attempt3;
	View attempt4;
	
	@Override
	protected View inflateLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dialog_options, container, false);
	}
	

	/**
	 * A method to be overwritten to provide settings behavior
	 * 
	 * @param view
	 */
	protected void customizeView(View view) {
		mPreferences = ((MainApplication) getActivity().getApplication()).getPreferences();

		mWordsCount = (RadioGroup) view.findViewById(R.id.wordsCount);		
		mAttemptsCount = (RadioGroup) view.findViewById(R.id.attemptsCount);
		
		//init the radio buttons
		int wordsCount = mPreferences.getWordsCount();
		int attemptsCount = mPreferences.getAttemptsCount();

		setWordsRadioButton(wordsCount);
		setAttemptsRadioButton(attemptsCount);

		setWordsCountHandler(view);
		setAttemptsCountHandler(view);
	}
	
	private void setWordsRadioButton(int count) {
		switch (count) {
			case 4:
				mWordsCount.check(R.id.word1);
				break;
			case 5:
				mWordsCount.check(R.id.word2);
				break;
			case 6:
				mWordsCount.check(R.id.word3);
				break;
			case 7: 
				mWordsCount.check(R.id.word4);
		}	
	}
	
	private void setAttemptsRadioButton(int count) {
		switch (count) {
		case 4:
			mAttemptsCount.check(R.id.attempt1);
			break;
		case 5:
			mAttemptsCount.check(R.id.attempt2);
			break;
		case 6:
			mAttemptsCount.check(R.id.attempt3);
			break;
		case 7: 
			mAttemptsCount.check(R.id.attempt4);
			break;
		}
	}

	/**
	 * Add the click listener to all the words radio buttons 
	 * @param view
	 */
	private void setWordsCountHandler(View view) {
		WordsClickListener wordsClickListener = new WordsClickListener();
		word1 = view.findViewById(R.id.word1);
		word1.setOnClickListener(wordsClickListener);
		word2 = view.findViewById(R.id.word2);
		word2.setOnClickListener(wordsClickListener);
		word3 = view.findViewById(R.id.word3);
		word3.setOnClickListener(wordsClickListener);
		word4 = view.findViewById(R.id.word4);
		word4.setOnClickListener(wordsClickListener);
	}
 
		/**
	 * Add the click listener to all the attempts radio buttons
	 * @param view
	 */
	private void setAttemptsCountHandler(View view) {
		AttemptsClickListener attemptsClickListener = new AttemptsClickListener();
		word1 = view.findViewById(R.id.attempt1);
		word1.setOnClickListener(attemptsClickListener);
		word2 = view.findViewById(R.id.attempt2);
		word2.setOnClickListener(attemptsClickListener);
		word3 = view.findViewById(R.id.attempt3);
		word3.setOnClickListener(attemptsClickListener);
		word4 = view.findViewById(R.id.attempt4);
		word4.setOnClickListener(attemptsClickListener);
	}
	
	/**
	 * Handler of the words count radio button clicks 
	 * @author andrey
	 *
	 */
	private class WordsClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int wordsCount = 4;
			switch(v.getId()) {
			case R.id.word1: 
				wordsCount = 4;
				Log.i(this.toString(), "4");
				break;
			case R.id.word2:
				wordsCount = 5;
				Log.i(this.toString(), "5");
				break;
			case R.id.word3:
				wordsCount = 6;
				Log.i(this.toString(), "6");
				break;
			case R.id.word4:
				wordsCount = 7;
				Log.i(this.toString(), "7");
				break;
			}
			mPreferences.setWordsCount(wordsCount);
		}
		
	}
	/**
	 * Handler of the attempts count radio button clicks 
	 * @author andrey
	 *
	 */
	private class AttemptsClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int attemptsCount = 4; 
			switch(v.getId()) {
			case R.id.attempt1: 
				attemptsCount = 4;
				Log.i(this.toString(), "4");
				break;
			case R.id.attempt2:
				attemptsCount = 5;
				Log.i(this.toString(), "5");
				break;
			case R.id.attempt3:
				attemptsCount = 6;
				Log.i(this.toString(), "6");
				break;
			case R.id.attempt4:
				attemptsCount = 7;
				Log.i(this.toString(), "7");
				break;
			}
			mPreferences.setAttemptsCount(attemptsCount);
		}
	}
}
