package com.joaostanzione.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jbosco on 10/07/2016.
 */
public class PrefsUtils {
    private static final String PREFS_NAME = "com.joaostanzione.PREFERENCE_FILE_KEY";
    public static final String KEY_CRITERIA = "key_criteria";
    public static final String CRITERIA_DEFAULT_VALUE = "popular";
    public static final String CRITERIA_VALUE_POPULAR = "popular";
    public static final String CRITERIA_VALUE_TOP_RATED = "top_rated";

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context) {
        return getPrefs(context).edit();
    }
}
