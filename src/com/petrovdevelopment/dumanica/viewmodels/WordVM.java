package com.petrovdevelopment.dumanica.viewmodels;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.petrovdevelopment.dumanica.model.Game;
import com.petrovdevelopment.dumanica.model.Word;

/**
 * View model (following the c# pattern MVVM)
 * except that it really provides only the data with no event handlers
 * Represents the data formatted in the way it is required to visualize a Word on the screen.
 * Corresponds to a Word object
 * @author andrey
 *
 */
public class WordVM {
	private static final int NUMBER_OF_HINTS = 3;
	
	private int mAttemptsLeft;
	private int mCharactersCount;
	private boolean mFinished;
	private boolean mLastLetterGuessCorrect;

	
	private boolean mSuccess;
	
	private Game mGame;
	
	private int mPointsForLastGuess;
	
	private String mWord; //the string of the actual word
	
	//user gradually reveals the masked word, and when finally it matches the original mWord then the user wins
	//e.g. цена and "****". Then user guesses "е" and then mMasketWordBuilder becomes "*е**" and so on
	//we use StringBuilder instead of string just for easier character replacement
	private StringBuilder mMaskedWordBuilder;
	private String mHint;
	
	private List<String> mGuessedLetters;

	
	
	public WordVM(Game game, Word word) {
		
		mGame = game;
		mGuessedLetters = new ArrayList<String>();
		mPointsForLastGuess = 0;
		mAttemptsLeft = mGame.getAttemptsCountPerWord();
		mWord = word.getWord();
		mMaskedWordBuilder = buildMaskedWord(mWord);
		
		mFinished = false;
		mLastLetterGuessCorrect = false;
		mSuccess = false;
		
		List<String> synonymHints = chooseSynonymHints(word.getSynonyms());
		mHint = buildHint(word.getCategory(), synonymHints);
	}

	/**
	 * Build a masked word by keeping the spaces and replacing the rest of the letters with an asterisk
	 * @param word
	 * @return
	 */
	private StringBuilder buildMaskedWord(String word) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<word.length(); i++){	
			if(word.charAt(i) == ' ') sb.append(' ');
			else sb.append('*');
		}
		return sb;
	}


	/**
	 * Choose randomly NUMBER_OF_HINTS of the synonyms of a word to be its game hints.
	 * If there are less than NUMBER_OF_HINTS, then just shuffle them (by choosing them randomly in the same manner).
	 * TODO:add filtering
	 * @return
	 */
	private static List<String> chooseSynonymHints(List<String> allSynonyms) {
		List<String> synonymsCopy = new ArrayList<String>();
        synonymsCopy.addAll(allSynonyms);
        int count = synonymsCopy.size();
        List<String> synonymHints = new ArrayList<String>();    
       
        //stop getting synonyms when there are not any left or when you got three (or whatever is NUMBER_OF_HINTS)
        for (int i = 0; i <NUMBER_OF_HINTS && count > 0; i++, count-- ) {	
        	Random random = new Random();
    		int n = random.nextInt(count);
    		synonymHints.add(synonymsCopy.remove(n));
        }
        return synonymHints;
	}
	
	/**
	 * Build a hint string consisting of the word's category and the previously chosen 3 random synonymHints.
	 * e.g. for the word "готин", hint can be "(прил.) прекрасен, чудесен, страхотен"
	 * @param category 	the grammar category of the word
	 * @param synonymHints list of synonyms to be included in the hint string
	 * @return
	 */
	private static String buildHint(String category, List<String> synonymHints) { 
		String result = "";
		if (category!= null) result += ("(" + category + ") ");
		if (synonymHints!= null && synonymHints.size()!= 0) {
			for (String gameHint : synonymHints) {
				 result += gameHint; 
				 result +=", "; 
			}
			//remove the last two characters, which are comma and space ", "
			result = result.substring(0, result.length()-2); 
		}
		return result;
	}


	/**
	 * Update the current word state, based on the letter chosen by the player from the virtual keyboard
	 * Replaces all * in the masked word with the letter if the letter is at that position in the original word
	 * @param guessLetter
	 */
	public void update(String guessLetter) 
	{
		mPointsForLastGuess = 0;
		mLastLetterGuessCorrect = false;
		char letterChar = guessLetter.charAt(0);
		
		for(int i = 0; i<getWord().length(); i++) {
			//if the letter is in the word and is not already guessed
			if(letterChar == getWord().charAt(i) && letterChar!=mMaskedWordBuilder.charAt(i)) {
				mLastLetterGuessCorrect = true;
				mPointsForLastGuess = getPointsForLastGuess() + Game.POINTS_PER_LETTER;
				mGuessedLetters.add(guessLetter);
				mMaskedWordBuilder.setCharAt(i, letterChar);
			}
		}
		if(!mLastLetterGuessCorrect) mAttemptsLeft--;
		updateFinishFlags();
	}
	
	
	private void updateFinishFlags() {
		if(isGuessed()) {
			mFinished = true;
			mSuccess = true;
		} 
		else if(mAttemptsLeft == 0) {
			mFinished = true;
			mSuccess = false;
		} 
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	///GETTERS


	public boolean isGuessed() {
		return (getWord().equals(getMaskedWordBuilderString()));
	}
	
	/**
	 * @return the mAttemptsLeft
	 */
	public int getAttemptsLeft() {
		return mAttemptsLeft;
	}

	/**
	 * @return the mCharactersCount
	 */
	public int getCharactersCount() {
		return mCharactersCount;
	}

	/**
	 * @return the string value of the actual word
	 */
	public String getWord() {
		return mWord;
	}

	/**
	 * @return the mHint
	 */
	public String getHint() {
		return mHint;
	}


	private String getMaskedWordBuilderString() {
		return mMaskedWordBuilder.toString();
	}

	public int getPointsForLastGuess() {
		return mPointsForLastGuess;
	}

	public boolean isFinished() {
		return mFinished;
	}
	
	public boolean isSuccess() {
		return mSuccess;
	}

	public boolean isLastLetterGuessCorrect() {
		return mLastLetterGuessCorrect;
	}
}
