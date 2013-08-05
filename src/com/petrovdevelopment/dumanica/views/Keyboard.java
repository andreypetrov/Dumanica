package com.petrovdevelopment.dumanica.views;

import java.util.ArrayList;
import java.util.List;

import com.petrovdevelopment.dumanica.R;
import com.petrovdevelopment.dumanica.libs.MagicTextButton;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * All mandatory layout properties from LinearLayout are mandatory in Keyboard too (width and height) TODO test the buttons background
 * change on a real telephone, maybe it is only a simulator issue?
 * 
 * @author andrey
 * 
 */
public class Keyboard extends LinearLayout {
	private OnButtonClickedListener mListener;
	private LinearLayout mFirstRow;
	private LinearLayout mSecondRow;
	private Activity mActivity;
	private List<Button> mButtons;
	private AttributeSet mAttributeSet;

	private boolean mIsInOneClickMode; // one click per key allowed on the keyboard

	public interface OnButtonClickedListener {
		void onButtonClicked(Button button, String letter);
	}

	public Keyboard(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode()) {
			mButtons = new ArrayList<Button>();

			mActivity = (Activity) context;
			mAttributeSet = attrs;

			mIsInOneClickMode = getIsInOneClickModeFromAttributes();

			mFirstRow = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.keyboard_bar, this, false);
			mFirstRow.setId(R.id.keyboardBar1);

			mSecondRow = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.keyboard_bar, this, false);
			mSecondRow.setId(R.id.keyboardBar2);

			initRow(mFirstRow, 0, 14);
			initRow(mSecondRow, 15, 29);

			this.addView(mFirstRow);
			this.addView(mSecondRow);

		}
	}

	private boolean getIsInOneClickModeFromAttributes() {
		boolean isInOneClickMode = false; // default if an issue with the attributes

		if (mAttributeSet != null) {
			TypedArray attributes = mActivity.getTheme().obtainStyledAttributes(mAttributeSet, R.styleable.Keyboard, 0, 0);
			try {
				isInOneClickMode = attributes.getBoolean(R.styleable.Keyboard_one_click_mode, false);
			} finally {
				attributes.recycle();
			}
		}
		return isInOneClickMode;
	}

	private Drawable getButtonBackground() {
		Drawable background = null;

		if (mAttributeSet != null) {
			TypedArray attributes = mActivity.getTheme().obtainStyledAttributes(mAttributeSet, R.styleable.Keyboard, 0, 0);
			try {
				background = attributes.getDrawable(R.styleable.Keyboard_key_background_image);
			} finally {
				attributes.recycle();
			}
		}
		return background;
	}

	/**
	 * 
	 * @param row
	 * @param firstLetterIndexInAlphabet
	 *            - zero based
	 * @param lastLetterIndexInAlphabet
	 *            - zero based
	 */
	private void initRow(LinearLayout bar, int firstLetterIndexInAlphabet, int lastLetterIndexInAlphabet) {
		int alphabetBarWidth = getDisplayWidthMinusDimensionHorizontalMarginsInPixels(); // minus left and right margin
		// Calculate button width based on the alphabet bar width
		int letterWidth = (alphabetBarWidth / 15);

		for (int i = firstLetterIndexInAlphabet; i <= lastLetterIndexInAlphabet; i++) {
			String letter = String.valueOf(mActivity.getString(R.string.alphabet).charAt(i));
			Button letterButton = createButton(letter, letterWidth, letterWidth, i);
			bar.addView(letterButton);
		}
	}

	public void reset() {
		for (Button button : mButtons)
			button.setEnabled(true);
	}

	private int getDisplayWidthMinusDimensionHorizontalMarginsInPixels() {
		DisplayMetrics outerMetrics = new DisplayMetrics(); // output parameter, C artifact
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(outerMetrics);
		int displayWidth = outerMetrics.widthPixels; // in pixels
		int marginInPixels = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
		return displayWidth - 2 * marginInPixels;
	}

	@SuppressWarnings("deprecation")
	// TODO decide whether to use the setBackgroundDrawable (deprecated) or the setBackground (requires API Level 16)
	private Button createButton(String label, int width, int height, int alphabetIndex) {

		MagicTextButton button = new MagicTextButton(mActivity);

		button.setBackgroundDrawable(getButtonBackground());

		button.setText(label);
		button.setTextColor(Color.WHITE);
		button.setTextSize(TypedValue.COMPLEX_UNIT_PX, width / 3);

		button.setMinimumWidth(0); // set this to 0, because button has default min width 64dip!
		button.setWidth(width);

		button.setMinHeight(0);
		button.setHeight(height);

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Button button = (Button) v;
				if (mIsInOneClickMode)
					button.setEnabled(false);
				mListener.onButtonClicked(button, button.getText().toString());
			}
		});

		mButtons.add(button);
		return button;
	}

	public void setOnButtonClickedListener(OnButtonClickedListener onButtonClickListener) {
		mListener = onButtonClickListener;
	}

}
