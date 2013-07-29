package com.petrovdevelopment.dumanica;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.petrovdevelopment.dumanica.dialogs.ConfirmDialog;
import com.petrovdevelopment.dumanica.libs.MagicTextButton;
import com.petrovdevelopment.dumanica.model.Game;
import com.petrovdevelopment.dumanica.viewmodels.WordVM;
import com.petrovdevelopment.dumanica.views.Keyboard;
import com.petrovdevelopment.dumanica.views.Keyboard.OnButtonClickedListener;

public class GameActivity extends Activity {
	Game mGame;
	WordVM mCurrentWordVM;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mGame = ((MainApplication) getApplication()).getGame();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);	
		initKeyboard();
		initGameBoard();
	}
	
	
	private void initKeyboard() {
		Keyboard keyboard = (Keyboard) findViewById(R.id.keyboard);
		keyboard.setOnButtonClickedListener(new OnButtonClickedListener() {
			@Override
			public void onButtonClicked(	String value) {
				Log.i(this.getClass().getSimpleName(), value);
			}
		});
	}


	/**
	 * Execute only once, when the activity has been created
	 */
	private void initGameBoard() {
		//initAlphabetView();
		initNextRound();//next round is in this case the first round	
	}


	/**
	 * Execute every time a new word should come on screen
	 */
	private void initNextRound() {
		mCurrentWordVM = mGame.removeNextWordVM();
		if (mCurrentWordVM!=null) {
			initWordBar();
			updateWordsLeftCount(mGame.getWordsLeft());
			updateAttemptsLeftCount(mCurrentWordVM.getAttemptsLeft());
		} else {
			endGame();
		}
	}

	private void initWordBar() {
		LinearLayout wordBar = (LinearLayout) findViewById(R.id.wordBar);
		wordBar.removeAllViews(); //clear existing views
		
		int lettersCount = mCurrentWordVM.getWord().length();

		for (int i = 0; i < lettersCount; i++){
		   //create a view and add it to the wordBar
			//TODO remove the letters from here, it should be empty
			wordBar.addView(createWordBarLetter(String.valueOf(mCurrentWordVM.getWord().charAt(i))));
			//wordBar.addView(createWordBarLetter(""));
		}
		
	}
	
	//create every letter for the main hangman hidden word
	private TextView createWordBarLetter(String label) {
		TextView textView = new TextView(this);
		textView.setBackgroundResource(R.drawable.green_circle);
		textView.setText(label);
		return textView;
	}
	
	
	
	
	private void initAlphabetView() {	
		LinearLayout alphabetBar1 = (LinearLayout) findViewById(R.id.keyboardBar1);
		LinearLayout alphabetBar2 = (LinearLayout) findViewById(R.id.keyboardBar2);
		
		int alphabetBarWidth = getDisplayWidthMinusDimensionHorizontalMarginsInPixels(); // minus left and right margin
		
		//Calculate button width based on the alphabet bar width
		int letterWidth = (alphabetBarWidth/15);

		//first half of alphabet
		for (int i = 0; i<15;i++) {
			String letter = String.valueOf(getString(R.string.alphabet).charAt(i));	
			Button letterButton = createButton(letter, letterWidth, letterWidth);
			alphabetBar1.addView(letterButton);
			
		}
		//second half of alphabet
		for (int i = 15; i<30; i++) {
			String letter = String.valueOf(getString(R.string.alphabet).charAt(i));	
			Button letterButton = createButton(letter, letterWidth, letterWidth);
			alphabetBar2.addView(letterButton);
		}

	}
	
	private int getDisplayWidthMinusDimensionHorizontalMarginsInPixels() {
		DisplayMetrics outerMetrics = new DisplayMetrics(); //output parameter, C artifact
		getWindowManager().getDefaultDisplay().getMetrics(outerMetrics);
		int displayWidth = outerMetrics.widthPixels; //in pixels		
		int marginInPixels = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin); 
		return displayWidth - 2 *marginInPixels;
	}
	
	
	private Button createButton(String label, int width, int height) {
		MagicTextButton button = new MagicTextButton(this);
		button.setBackgroundResource(R.drawable.green_circle);
		button.setText(label);
		button.setTextColor(Color.WHITE);
		button.setTextSize(TypedValue.COMPLEX_UNIT_PX, width/3);
	
		button.setMinimumWidth(0); // set this to 0, because button has default min width 64dip!
		button.setWidth(width);
		//button.setLayoutParams(getButtonLinearLayoutParams());
		
		button.setMinHeight(0);
		button.setHeight(height);
	
		Log.i(this.toString(), "paddingLeft: " + button.getPaddingLeft());
		//36 padding 16 
		Log.i(this.toString(), "button width: " + button.getWidth());
		return button;
	}
	
	/**
	 * Wrap content and add margins.
	 * Not used for now
	 * @return
	 */
	/*private LayoutParams getButtonLinearLayoutParams() {
		LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(2, 0, 2, 0);
		return params;
	}
	*/
	

	//If there are animations, they should be executed async
	public void updateUi() {
		updateAttemptsLeftCount(mCurrentWordVM.getAttemptsLeft());
		updatePoints();
		updateWordBar();
	}

	
	
	
	private void updatePoints() {
		// TODO Auto-generated method stub	
	}
	
	private void updateWordBar() {
		// TODO Auto-generated method stub
		
	}


	public void updateAttemptsLeftCount(int count) {
		TextView attempts = (TextView) findViewById(R.id.attemptsLeftCount);
		attempts.setText(getString(R.string.attemptsLeftCount) + count);
	}
    public void updateWordsLeftCount(int count) {
    	TextView words = (TextView) findViewById(R.id.wordsLeftCount);
    	words.setText(getString(R.string.wordsLeftCount) + count);
	}
    
    
    
    
	private void endGame() {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void onBackPressed() {
		ConfirmDialog confirmDialog = new ConfirmDialog();
		confirmDialog.show(getFragmentManager(), MainApplication.DIALOG);
	}

}
