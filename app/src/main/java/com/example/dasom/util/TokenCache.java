package com.example.dasom.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class TokenCache {

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setToken(Context context, String json) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("token_json", json).apply();
    }

    public static String getToken(Context context) {

        try {
            return getSharedPreferences(context).getString("token_json", "");
        } catch (Exception e) {
            return null;
        }
    }
}
