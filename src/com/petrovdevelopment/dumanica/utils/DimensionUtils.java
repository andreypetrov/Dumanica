package com.petrovdevelopment.dumanica.utils;

import android.content.res.Resources;

//TODO: delete probably. not used class
public final class DimensionUtils {

    private static float pixelsPerOneDp = 0f;

    /**
     * Init dimensions util when starting the application
     * @param r
     */
    public static void init(Resources r) {
        pixelsPerOneDp = r.getDisplayMetrics().densityDpi / 160f;
    }

    public static float pxToDp(float px)
    {
    	return px / pixelsPerOneDp;
    }

    public static float dpToPx(float dp)
    {
        return dp * pixelsPerOneDp;
    }
}

