package com.petrovdevelopment.dumanica;

import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class EndGameActivity extends Activity {
	public static final String FINAL_POINTS_EXTRA = "points";
	public static final String FINAL_PERCENT_EXTRA = "percent";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_end_game);
		
		TextView pointsView = (TextView) findViewById(R.id.finalPoints);
		
		Intent intent = getIntent();
		int points = intent.getIntExtra(FINAL_POINTS_EXTRA, 0);
		pointsView.setText(String.valueOf(points));
		
	}

	@Override
	public void onBackPressed() {
		finish();
	}

	
}
