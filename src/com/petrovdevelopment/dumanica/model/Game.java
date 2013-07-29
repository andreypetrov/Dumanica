package com.petrovdevelopment.dumanica.model;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.petrovdevelopment.dumanica.viewmodels.WordVM;

/**
 * Model representing the state of the game word
 * @author andrey
 *
 */
public class Game {
	private Preferences mPreferences;	//the user input preferences ("настройки")
	private List<WordVM> mWordViewModels; //models representing every word
	private int points;
	
	//difficulty
	//TODO add a ViewModel for the current word screen 
	
	public Game(Preferences preferences){
		mPreferences = preferences;
	}

	public void initFromCursor(Cursor mWordsCursor) {
		//clear the words list
		mWordViewModels = new ArrayList<WordVM>();
		//set points back to 0
		setPoints(0);
		
		//and populate it with new words
		do {
			Word word = new Word(mWordsCursor.getString(1), mWordsCursor.getString(2), mWordsCursor.getString(3));
			mWordViewModels.add(new WordVM(this, word));			
		} while (mWordsCursor.moveToNext());
		
	}

	//when it reaches 0 the game is over
	public int getWordsLeft() {
		return mWordViewModels.size();
	}
	
	/**
	 * Get the words count from the preferences. Can be called before the words have been loaded.
	 * @return
	 */
	public int getWordsCount() {
		return mPreferences.getWordsCount();
	}
	
	/**
	 * Get the attempts count per word from the preferences. Can be called before the words have been loaded.
	 * @return
	 */
	public int getAttemptsCount() {
		return mPreferences.getAttemptsCount();
	}

	/**
	 * Removes and returns the first word in the list or returns null if the list is empty
	 * @return
	 */
	public WordVM removeNextWordVM() {
		if (getWordsLeft() > 0) return mWordViewModels.remove(0);
		else return null;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

}
