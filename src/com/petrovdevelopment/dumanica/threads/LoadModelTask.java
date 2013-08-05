package com.petrovdevelopment.dumanica.threads;

import com.petrovdevelopment.dumanica.MainApplication;
import com.petrovdevelopment.dumanica.R;
import com.petrovdevelopment.dumanica.model.Game;

import android.app.ProgressDialog;
import android.widget.ProgressBar;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

public class LoadModelTask extends AsyncTask<Object, Void, Integer> {
	private Context mContext;
	private Cursor mWordsCursor;
	private DatabaseHelper mDb;

	private ProgressDialog mProgressDialog;

	public LoadModelTask(Context context) {
		super();

		mContext = context;
		mProgressDialog = new ProgressDialog(mContext);
	}

	// can use UI thread here
	protected void onPreExecute() {
		ProgressBar progressBar = new ProgressBar(mContext);
		progressBar.setIndeterminateDrawable(mContext.getResources().getDrawable(R.drawable.progress_spinner));

		mProgressDialog.show();
		// this call need to be after show!
		mProgressDialog.setContentView(progressBar);
	}

	/**
	 * Open database, get the random words and load then into a list of word models. 
	 * This list is kept by the main Game model.
	 * Automatically done on worker thread (separate from UI thread)
	 */
	protected Integer doInBackground(final Object... args) {
		Game game = ((MainApplication) mContext.getApplicationContext()).getGame();
		int wordsCount = game.getWordsCountPerGame();

		mDb = new DatabaseHelper(mContext);
		mWordsCursor = mDb.getWords(wordsCount);

		game.initFromCursor(mWordsCursor);
		return game.getWordsCountPerGame();
	}

	// can use UI thread here
	protected void onPostExecute(Integer result) {
		mProgressDialog.dismiss();
		((LoadModelCaller) mContext).onPostLoadModelExecute();
	}
}
