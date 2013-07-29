package com.petrovdevelopment.dumanica.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Encapsulates the access to the shared preferences file for the application
 * @author andrey
 *
 */
public class Preferences {
	public static final String PREFERENCES = "preferences";
	
	public static final String WORDS_COUNT = "words_count";
	public static final String ATTEMPTS_COUNT = "attempts_count";
	public static final int DEFAULT_WORDS_COUNT = 4;
	public static final int DEFAULT_ATTEMPTS_COUNT = 6;
	
	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor mSharedPreferencesEditor;
	private Context mContext;
	
	public Preferences(Context context) {
		mContext = context;
	}
	
	/**
	 * Initialize the preferences. It can be called only after activity's onCreate method
	 */
	public void init() {
		 mSharedPreferences = mContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
		 mSharedPreferencesEditor = mSharedPreferences.edit();
	}
	
	
	/** 
	 * Return the word count saved in the preferences file or if there is none, then return the default one
	 * @return
	 */
	public int getWordsCount(){
		return mSharedPreferences.getInt(WORDS_COUNT, DEFAULT_WORDS_COUNT);
	}
	
	public int getAttemptsCount() {
		return mSharedPreferences.getInt(ATTEMPTS_COUNT, DEFAULT_ATTEMPTS_COUNT);
	}
	
	public void setWordsCount(int wordsCount) {
		mSharedPreferencesEditor.putInt(WORDS_COUNT, wordsCount);
		mSharedPreferencesEditor.apply();
	}
	public void setAttemptsCount(int attemptsCount) {
		mSharedPreferencesEditor.putInt(ATTEMPTS_COUNT, attemptsCount);
		mSharedPreferencesEditor.apply();
	}
}
