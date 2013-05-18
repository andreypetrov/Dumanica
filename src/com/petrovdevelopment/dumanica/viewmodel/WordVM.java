package com.petrovdevelopment.dumanica.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.petrovdevelopment.dumanica.model.Game;
import com.petrovdevelopment.dumanica.model.Word;

/**
 * View model (following the c# pattern MVVM)
 * except that it really provides only the data with no event handlers
 * Represents the data required to visualize a Word on the screen
 * @author andrey
 *
 */
public class WordVM {
	private static final int NUMBER_OF_HINTS = 3;
	
	private int mAttemptsLeft;
	private int mCharactersCount;
	private String mWord; //the string of the actual word
	private String mHint;
	
	public WordVM(Game game, Word word) {
		mAttemptsLeft = game.getAttemptsCount();
		mWord = word.getWord();
		List<String> synonymHints = chooseSynonymHints(word.getSynonyms());
		mHint = buildHint(word.getCategory(), synonymHints);
	}

	
	/**
	 * Choose randomly NUMBER_OF_HINTS of the synonyms of a word to be its game hints.
	 * If there are less than NUMBER_OF_HINTS, then just shuffle them (by choosing them randomly in the same manner).
	 * TODO:add filtering
	 * @return
	 */
	public static List<String> chooseSynonymHints(List<String> allSynonyms) {
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
			result = result.substring(0, result.length()-2); //remove the last two characters ", "
		}
		return result;
	}

	///GETTERS

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
}
