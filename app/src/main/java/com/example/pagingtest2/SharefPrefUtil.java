package com.example.pagingtest2;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SharefPrefUtil {
    public static String ACTION = "sialalala";

    public static void savePage(Context context, int page) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt(ACTION, page);
        editor.commit();
    }

    public static int getPage(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(ACTION, -1);
    }

}
