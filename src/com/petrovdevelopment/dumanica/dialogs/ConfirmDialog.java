package com.petrovdevelopment.dumanica.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.petrovdevelopment.dumanica.R;

/**
 * Confirms if the activity should do something.
 * If the activity is an instance of ConfirmDialogParent it can define its own implementation of the onDialogOkClick() method
 * Otherwise default functionality is to finish the activity on pressing ok. 
 * @author andrey
 *
 */
public class ConfirmDialog extends DialogFragment {

	public static void showConfirmDialog() {	
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_FRAME, getTheme());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View confirmLayout = inflateLayout(inflater, container, savedInstanceState);
		setOkHandler(confirmLayout);
		setCancelHandler(confirmLayout);
		customizeView(confirmLayout);
		setCanceledOnTouchOutside();
		return confirmLayout;
	}

	/**
	 * Set the dialog to be cancelled if clicked outside of its area
	 */
	protected void setCanceledOnTouchOutside() {
		getDialog().setCanceledOnTouchOutside(true);
	}

	/**
	 * Inflate a layout, to be used by the dialog view.
	 * 
	 */
	protected View inflateLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View confirmLayout = (ViewGroup) inflater.inflate(R.layout.dialog_confirm, container, false);
		return confirmLayout;
	}

	/**
	 * If there is an OK button, then set it to close the parent activity
	 * 
	 * @param view
	 */
	protected void setOkHandler(View view) {
		// close the activity
		View okButton = view.findViewById(R.id.okButton);
		if (okButton != null) {
			okButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Activity parentActivity = getActivity();
					//optional interface allowing for customized OK behavior
					if (parentActivity instanceof ConfirmDialogParent) {
						((ConfirmDialogParent) parentActivity).onDialogOkClick();
					} else { //default if the 
						getActivity().finish();
					}
				}
			});
		}

	}

	/**
	 * If there is a cancel button, then set it to dismiss the fragment
	 * 
	 * @param view
	 */
	protected void setCancelHandler(View view) {
		View cancelButton = view.findViewById(R.id.cancelButton);
		if (cancelButton != null) {
			cancelButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ConfirmDialog.this.dismiss();
				}
			});
		}

	}

	/**
	 * A method to be overwritten to provide some custom behavior and look of the confirm dialog.
	 * 
	 * @param view
	 */
	protected void customizeView(View view) {
		// do nothing
	}
}
