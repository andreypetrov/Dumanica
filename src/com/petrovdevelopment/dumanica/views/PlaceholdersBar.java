package com.petrovdevelopment.dumanica.views;

import java.util.ArrayList;
import java.util.List;

import com.petrovdevelopment.dumanica.R;
import com.petrovdevelopment.dumanica.libs.MagicTextView;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * View of the word being guessed. TODO add here whatever is the guessed word generation from game activity
 * 
 * @author andrey
 * 
 */
public class PlaceholdersBar extends LinearLayout {
	private List<TextView> mLetterViews;
	private Activity mActivity;
	private String mWord;
	private AttributeSet mAttributeSet;
	private Drawable mPlaceholderDefaultBackground;
	private Drawable mPlaceholderSuccesstBackground;
	private Drawable mPlaceholderSpaceBackground;

	public PlaceholdersBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode()) {
			mLetterViews = new ArrayList<TextView>();

			mActivity = (Activity) context;
			mAttributeSet = attrs;
			getAttributesFromLayout();
		}
	}

	private void getAttributesFromLayout() {
		if (mAttributeSet != null) {
			TypedArray attributes = mActivity.getTheme().obtainStyledAttributes(mAttributeSet, R.styleable.PlaceholdersBar, 0, 0);
			try {
				mPlaceholderDefaultBackground = attributes.getDrawable(R.styleable.PlaceholdersBar_placeholder_default_image);
				mPlaceholderSuccesstBackground = attributes.getDrawable(R.styleable.PlaceholdersBar_placeholder_success_image);
				mPlaceholderSpaceBackground = attributes.getDrawable(R.styleable.PlaceholdersBar_placeholder_space_image);
			} finally {
				attributes.recycle();
			}
		} else { // assign default image
			mPlaceholderDefaultBackground = null;
			mPlaceholderSuccesstBackground = null;
			mPlaceholderSpaceBackground = null;
		}
	}

	/**
	 * Create a placeholders row with as many placeholders as there are letters in the word
	 * 
	 * @param word
	 */
	public void initFromWord(String word) {
		mWord = word;
		initBar();
	}

	/**
	 * Reveal all occurrences of the letter within the hidden word. Depends on the fact that the number of children of this view is equal to
	 * the number of letters in the word
	 * 
	 * @param letter
	 */
	@SuppressWarnings("deprecation")
	public void update(String letter) {

		for (int i = 0; i < mWord.length(); i++) {
			TextView placeholder = (TextView) this.getChildAt(i);
			if (mWord.charAt(i) == letter.charAt(0)) {
				placeholder.setText(letter);
				placeholder.setBackgroundDrawable(mPlaceholderSuccesstBackground);
			}
		}
		invalidate();
		requestLayout();
	}

	private void initBar() {
		this.removeAllViews();
		for (int i = 0; i < mWord.length(); i++) {
			TextView placeholderView = createPlaceholderView(mWord.charAt(i));
			this.addView(placeholderView);
			mLetterViews.add(placeholderView);
		}
		invalidate();
		requestLayout();
	}

	@SuppressWarnings("deprecation")
	private TextView createPlaceholderView(char letter) {
		MagicTextView placeholder = new MagicTextView(mActivity);
		placeholder.setTextColor(Color.WHITE);
		if (letter == ' ')
			placeholder.setBackgroundDrawable(mPlaceholderSpaceBackground);
		else
			placeholder.setBackgroundDrawable(mPlaceholderDefaultBackground);

		return placeholder;
	}

}
