package com.petrovdevelopment.dumanica.threads;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Uses the super class to create the synonyms.sqlite database from the the file assets/databases/synonyms.zip.
 * This is done only the first time the app starts. 
 * If later the database needs to change on devices with already installed app, 
 * the version should be moved to 2 and the synonyms.zip file updated
 * @param context
 */

public class DatabaseHelper extends SQLiteAssetHelper {
	private static final String DATABASE_NAME = "synonyms";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Gets a cursor over the words table and moves it to the first word
	 * @return
	 */
	public Cursor getWords(int wordsCount) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"0 _id", "word", "category", "synonyms"}; 
		String sqlTables = "synonyms";

		qb.setTables(sqlTables);
		
		//SELECT * FROM synonyms WHERE LENGTH(word) <= 10 ORDER BY RANDOM() LIMIT words_count;
		Cursor c = qb.query(db, sqlSelect, "LENGTH(word)<=10", null, null, null,"RANDOM()", String.valueOf(wordsCount));
		c.moveToFirst();
		return c;
	}
}
