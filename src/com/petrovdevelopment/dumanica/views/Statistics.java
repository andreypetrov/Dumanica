package com.petrovdevelopment.dumanica.views;

import java.util.ArrayList;
import java.util.List;

import com.petrovdevelopment.dumanica.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Statistics extends LinearLayout {

	private LinearLayout mFirstRow;
	private LinearLayout mSecondRow;
	private Activity mActivity;

	private List<TextView> mAttemptMarkers;
	private List<TextView> mWordMarkers;

	private Drawable mAttemptMarkerBackground;
	private Drawable mWordMarkerBackground;
	private Drawable mAttemptUsedMarkerBackground;
	private Drawable mWordUsedMarkerBackground;

	private int mAttemptsLeft = 0;
	private int mWordsLeft = 0;

	// private AttributeSet mAttributeSet;

	public Statistics(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode()) {
			mAttemptMarkers = new ArrayList<TextView>();
			mWordMarkers = new ArrayList<TextView>();
			mActivity = (Activity) context;

			mAttemptMarkerBackground = mActivity.getResources().getDrawable(R.drawable.attempts_remaining_icon_800_480_240dpi);
			mAttemptUsedMarkerBackground = mActivity.getResources().getDrawable(R.drawable.inactive_icon_800_480_240dpi);

			mWordMarkerBackground = mActivity.getResources().getDrawable(R.drawable.word_remaining_icon_800_480_240dpi);
			mWordUsedMarkerBackground = mActivity.getResources().getDrawable(R.drawable.inactive_icon_800_480_240dpi);
		}
	}

	@Override
	protected void onFinishInflate() {
		mFirstRow = (LinearLayout) findViewById(R.id.statisticsFirstRow);
		mSecondRow = (LinearLayout) findViewById(R.id.statisticsSecondRow);
	}

	public void init(int attemptsTotal, int wordsTotal) {
		mAttemptsLeft = attemptsTotal;
		mWordsLeft = wordsTotal;

		initAttemptsRow(attemptsTotal);
		initWordsRow(wordsTotal);
	}

	@SuppressWarnings("deprecation")
	public void updateAttempts() {
		mAttemptsLeft--;
		mAttemptMarkers.get(mAttemptsLeft).setBackgroundDrawable(mAttemptUsedMarkerBackground);
	}

	@SuppressWarnings("deprecation")
	public void updateWordsAndResetAttempts() {
		mWordsLeft--;
		mWordMarkers.get(mWordsLeft).setBackgroundDrawable(mWordUsedMarkerBackground);
		resetAttempts();

	}

	@SuppressWarnings("deprecation")
	private void resetAttempts() {
		mAttemptsLeft = mAttemptMarkers.size();
		for (TextView marker : mAttemptMarkers) {
			marker.setBackgroundDrawable(mAttemptMarkerBackground);
		}
	}

	@SuppressWarnings("deprecation")
	private void initAttemptsRow(int count) {
		for (int i = 0; i < count; i++) {
			TextView marker = new TextView(mActivity);
			marker.setMinHeight(0);
			marker.setMinWidth(0);
			marker.setBackgroundDrawable(mAttemptMarkerBackground);
			mFirstRow.addView(marker);
			mAttemptMarkers.add(marker);
		}
	}

	@SuppressWarnings("deprecation")
	private void initWordsRow(int count) {
		for (int i = 0; i < count; i++) {
			TextView marker = new TextView(mActivity);
			marker.setMinHeight(0);
			marker.setMinWidth(0);
			marker.setBackgroundDrawable(mWordMarkerBackground);
			mSecondRow.addView(marker);
			mWordMarkers.add(marker);
		}
	}

}
