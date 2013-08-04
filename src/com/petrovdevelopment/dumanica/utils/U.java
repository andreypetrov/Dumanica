package com.petrovdevelopment.dumanica.utils;

import android.util.Log;

public class U {
	public static void log(Object sender, String message) {
		Log.i(sender.getClass().getSimpleName(), message);
	}

	public static void log(Object sender, int percentageSuccess) {
		log(sender, String.valueOf(percentageSuccess));
	}
}
