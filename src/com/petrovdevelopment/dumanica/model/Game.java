package com.petrovdevelopment.dumanica.model;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.petrovdevelopment.dumanica.viewmodels.WordVM;

/**
 * Model representing the state of the game word
 * @author andrey
 *
 */
public class Game {
	public static final int POINTS_PER_LETTER = 10;
	private int mPoints;
	
	private Preferences mPreferences;	//the user input preferences i.e. "настройки"
	private List<WordVM> mWordViewModels; //models representing every word
	private WordVM mCurrentWordVM;
	
	private boolean mNewRound;
	private boolean mGameFinished;

	private int mGuessedWordsCount;
	
	public Game(Preferences preferences){
		mPreferences = preferences;
	}

	public void initFromCursor(Cursor mWordsCursor) {
		//clear the words list
		mWordViewModels = new ArrayList<WordVM>();
		
		mNewRound = true;
		mGameFinished = false;
		mPoints = 0;
		mGuessedWordsCount = 0;
		
		//and populate it with new words
		do {
			Word word = new Word(mWordsCursor.getString(1), mWordsCursor.getString(2), mWordsCursor.getString(3));
			mWordViewModels.add(new WordVM(this, word));			
		} while (mWordsCursor.moveToNext());
		
	}

	//step the world state ahead
	public void update(String guessedLetter) {
		getCurrentWordVM().update(guessedLetter);
		mPoints = mPoints + getCurrentWordVM().getPointsForLastGuess();
		
		if(getCurrentWordVM().isFinished()){
			if(getCurrentWordVM().isSuccess()) mGuessedWordsCount++;
			
			if(goNextRound()) mNewRound = true;
			else mGameFinished = true;
		} else mNewRound = false;
	}
	
	
	public void start() {
		goNextRound();
	}
	
	/**
	 * Returns true if you can go to the next round or false if there are no more words
	 * @return
	 */
	private boolean goNextRound() {
		mCurrentWordVM = removeNextWordVM();
		if(mCurrentWordVM == null) return false;
		else return true;
	}

	/**
	 * Removes and returns the first word in the list or returns null if the list is empty
	 * @return
	 */
	private WordVM removeNextWordVM() {
		if (getWordsLeft() > 0) return mWordViewModels.remove(0);
		else return null;
	}

	
	
	
	///
	/// GETTERS
	///
	
	public int getPercentageSuccess() {
		double percent = ((double) mGuessedWordsCount) / getWordsCountPerGame();
		return (int) (percent * 100);
	}
	
	
	/**
	 * When words left reaches 0 the game is over
	 * @return
	 */
	public int getWordsLeft() {
		return mWordViewModels.size();
	}
	
	/**
	 * Get the words count from the preferences. Can be called before the words have been loaded.
	 * @return
	 */
	public int getWordsCountPerGame() {
		return mPreferences.getWordsCount();
	}
	
	/**
	 * Get the attempts count per word from the preferences. Can be called before the words have been loaded.
	 * @return
	 */
	public int getAttemptsCountPerWord() {
		return mPreferences.getAttemptsCount();
	}

	public int getPoints() {
		return mPoints;
	}

	public WordVM getCurrentWordVM() {
		return mCurrentWordVM;
	}

	public boolean isNewRound() {
		return mNewRound;
	}
	public boolean isFinished() {
		return mGameFinished;
	}

}
