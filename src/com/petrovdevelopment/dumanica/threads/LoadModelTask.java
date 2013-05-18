package com.petrovdevelopment.dumanica.threads;

import com.petrovdevelopment.dumanica.MainApplication;
import com.petrovdevelopment.dumanica.model.Game;
import com.petrovdevelopment.dumanica.model.Word;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

public class LoadModelTask extends AsyncTask<Object, Void, Integer>{
	private Context mContext;
	private Cursor mWordsCursor;
	private DatabaseHelper mDb;
	
	//private final ProgressDialog dialog = new ProgressDialog(
	//		DatabaseActivity.this);

	// can use UI thread here
	protected void onPreExecute() {
		//this.dialog.setMessage("Inserting data...");
		//this.dialog.show();
	}

	/**
	 * Open database, get the random words and load then into a list of word models
	 * Pass in the context as a parameter
	 * Automatically done on worker thread (separate from UI thread)
	 */
	protected Integer doInBackground(final Object... args) {
		mContext = (Context) args[0];
		
		mDb = new DatabaseHelper(mContext);
		mWordsCursor = mDb.getWords();
		Game game = ((MainApplication) mContext.getApplicationContext()).getGame();
		game.initFromCursor(mWordsCursor);
		return game.getWordsCount();		
	}

	// can use UI thread here
	protected void onPostExecute(Integer result) {
		Log.i(this.toString(), "onPostExecute, wordsCount: " + result);
		((LoadModelCaller) mContext).onPostLoadModelExecute();
	}
}