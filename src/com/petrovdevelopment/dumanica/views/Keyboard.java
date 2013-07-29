package com.petrovdevelopment.dumanica.views;

import com.petrovdevelopment.dumanica.R;
import com.petrovdevelopment.dumanica.libs.MagicTextButton;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * All mandatory layout properties from LinearLayout are mandatory in Keyboard too (width and height)
 * @author andrey
 *
 */
public class Keyboard extends LinearLayout {
	private int mButtonBackgroundResourceId;
	private OnButtonClickedListener mListener;
	private LinearLayout firstRow;
	private LinearLayout secondRow;
	private Activity mActivity;
	private Drawable mKeyBackgroundImage;
	
	public interface OnButtonClickedListener {
		void onButtonClicked(String value);
	}
	
	
	public Keyboard(Context context, AttributeSet attrs) {
		super(context, attrs);
		mActivity = (Activity) context;
		
		loadAttributes(context, attrs);
		
		firstRow = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.keyboard, this, false);
		firstRow.setId(R.id.keyboardBar1);
		
		secondRow = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.keyboard, this, false);
		secondRow.setId(R.id.keyboardBar2);
		
		initRow(firstRow, 0, 14);
		initRow(secondRow, 15, 29);
		
		this.addView(firstRow);
		this.addView(secondRow);

	}

	private void loadAttributes(Context context, AttributeSet attrs) {		
		if (attrs != null) {
			TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Keyboard, 0, 0);
			try {
				setKeyBackgroundImage(attributes.getDrawable(R.styleable.Keyboard_key_background_image));
			} finally {
				attributes.recycle();
			}
		}
	}

	
	/**
	 * 
	 * @param row
	 * @param firstLetterIndexInAlphabet - zero based
	 * @param lastLetterIndexInAlphabet - zero based
	 */
	private void initRow(LinearLayout bar, int firstLetterIndexInAlphabet, int lastLetterIndexInAlphabet) {
		int alphabetBarWidth = getDisplayWidthMinusDimensionHorizontalMarginsInPixels(); // minus left and right margin
		//Calculate button width based on the alphabet bar width
		int letterWidth = (alphabetBarWidth/15);
		
		for (int i = firstLetterIndexInAlphabet; i<=lastLetterIndexInAlphabet;i++) {
			String letter = String.valueOf(mActivity.getString(R.string.alphabet).charAt(i));	
			Button letterButton = createButton(letter, letterWidth, letterWidth);
			bar.addView(letterButton);
		}
	}

	private int getDisplayWidthMinusDimensionHorizontalMarginsInPixels() {
		DisplayMetrics outerMetrics = new DisplayMetrics(); //output parameter, C artifact
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(outerMetrics);
		int displayWidth = outerMetrics.widthPixels; //in pixels		
		int marginInPixels = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin); 
		return displayWidth - 2 *marginInPixels;
	}
	
	
	@SuppressWarnings("deprecation") //TODO decide whether to use the setBackgroundDrawable (deprecated) or the setBackground (requires API Level 16) 
	private Button createButton(String label, int width, int height) {
		MagicTextButton button = new MagicTextButton(mActivity);
		if(getKeyBackgroundImage()!=null) button.setBackgroundDrawable(getKeyBackgroundImage());

		button.setText(label);
		button.setTextColor(Color.WHITE);
		button.setTextSize(TypedValue.COMPLEX_UNIT_PX, width/3);
	
		button.setMinimumWidth(0); // set this to 0, because button has default min width 64dip!
		button.setWidth(width);
		//button.setLayoutParams(getButtonLinearLayoutParams());
		
		button.setMinHeight(0);
		button.setHeight(height);
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button button = (Button) v;
				mListener.onButtonClicked(button.getText().toString());
			}
		});
		
		return button;
	}
	 
	
	
	
	
	
	
	public void setOnButtonClickedListener (OnButtonClickedListener onButtonClickListener) {
		mListener = onButtonClickListener;
	}
	
	
	
	/**
	 * @return the mButtonBackgroundResourceId
	 */
	public int getButtonBackgroundResourceId() {
		return mButtonBackgroundResourceId;
	}

	/**
	 * @param mButtonBackgroundResourceId the mButtonBackgroundResourceId to set
	 */
	public void setButtonBackgroundResourceId(int buttonBackgroundResourceId) {
		this.mButtonBackgroundResourceId = buttonBackgroundResourceId;
		invalidate();
		requestLayout();
	}

	public Drawable getKeyBackgroundImage() {
		return mKeyBackgroundImage;
	}

	public void setKeyBackgroundImage(Drawable keyBackgroundImage) {
		this.mKeyBackgroundImage = keyBackgroundImage;
		invalidate();
		requestLayout();
	}
}
