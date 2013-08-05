package com.petrovdevelopment.dumanica.utils;

import android.util.Log;

public class U {
	public static void log(Object sender, String message) {
		Log.i(sender.getClass().getSimpleName(), message);
	}

	public static void log(Object sender, int message) {
		log(sender, String.valueOf(message));
	}
	
	public static void log(String message) {
		log(new Object(), message);
	}
	
	public static void log(int message) {
		log(new Object(), message);
	}
}
