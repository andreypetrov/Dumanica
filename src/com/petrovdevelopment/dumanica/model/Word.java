package com.petrovdevelopment.dumanica.model;

import java.util.Arrays;
import java.util.List;

/**
 * Model of a word from the game.
 * Holds information about the word, a list of its synonyms and its grammatical category
 * @author andrey
 *
 */
public class Word {
	
	private String mWord;
	private String mCategory; //TODO better this to be enum?
	private List<String> mSynonyms;

	public Word(String word, String category, String synonyms) {
		mWord = word;
		mCategory = category;
		String[] synonymsArray = synonyms.split(", ");
		mSynonyms =  Arrays.asList(synonymsArray);
		
	}
	
	/**
	 * Get the word itself
	 * @return
	 */
	public String getWord() {
		return mWord;
	}
	
	/**
	 * Get a list of all the word synonyms. A synonym can be more than one word
	 * @return
	 */
	public List<String> getSynonyms() {
		return mSynonyms;
	}

	/**
	 * Return the gramatical category of the word (noun, verb, adjective and so on)
	 * @return
	 */
	public String getCategory() {
		return mCategory;
	}
	
	
}
