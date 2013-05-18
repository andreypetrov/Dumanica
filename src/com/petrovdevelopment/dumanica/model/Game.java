package com.petrovdevelopment.dumanica.model;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.petrovdevelopment.dumanica.utils.Preferences;
import com.petrovdevelopment.dumanica.viewmodel.WordVM;

/**
 * Model representing the state of the game word
 * @author andrey
 *
 */
public class Game {
	private Preferences mPreferences;	
	
	private int mWordsLeft; //when it reaches 0 the game is over
	
	private List<WordVM> mWordViewModels; //models representing every word
	
	//difficulty
	//TODO add a ViewModel for the current word screen 
	
	public Game(Preferences preferences){
		mPreferences = preferences;
		initWordsList();
	}

	private void initWordsList() {
		mWordViewModels = new ArrayList<WordVM>();
	}
	
	public WordVM getWordVM(int index) {
		return mWordViewModels.get(index);
	}

	public void initFromCursor(Cursor mWordsCursor) {
		//clear the words list
		initWordsList();
		//and populate it with new words
		do {
			Word word = new Word(mWordsCursor.getString(1), mWordsCursor.getString(2), mWordsCursor.getString(3));
			mWordViewModels.add(new WordVM(this, word));			
		} while (mWordsCursor.moveToNext());
		
		mWordsLeft =  mWordViewModels.size();
	}

	public int getWordsLeft() {
		return mWordsLeft;
	}
	
	/**
	 * Get the words count from the preferences
	 * @return
	 */
	public int getWordsCount() {
		return mPreferences.getWordsCount();
	}
	
	/**
	 * Get the attempts count per word from the preferences
	 * @return
	 */
	public int getAttemptsCount() {
		return mPreferences.getAttemptsCount();
	}

}
