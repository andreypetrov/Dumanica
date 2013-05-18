package com.petrovdevelopment.dumanica.threads;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper {
	private static final String DATABASE_NAME = "synonyms";
	private static final int DATABASE_VERSION = 1;
	private static final String WORDS_COUNT = "10";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	/**
	 * TODO: fix that?
	 * Gets a cursor over the words and moves it to the first word
	 * @return
	 */
	public Cursor getWords() {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"0 _id", "word", "category", "synonyms"}; 
		String sqlTables = "synonyms";

		qb.setTables(sqlTables);
		//SELECT * FROM synonyms ORDER BY RANDOM() LIMIT 1;
		Cursor c = qb.query(db, sqlSelect, null, null, null, null,"RANDOM()", WORDS_COUNT);
		c.moveToFirst();
		return c;
	}
}
