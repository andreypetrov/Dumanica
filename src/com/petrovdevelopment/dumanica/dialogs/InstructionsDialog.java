package com.petrovdevelopment.dumanica.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petrovdevelopment.dumanica.R;

/**
 * Dialog giving information about the rules of the game
 * @author andrey
 *
 */
public class InstructionsDialog extends ConfirmDialog{
	
	@Override
	protected View inflateLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dialog_instructions, container, false);
	}
}
