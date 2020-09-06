package com.example.dasom.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class TokenCache {

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setToken(Context context, String token) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("token", token).apply();
    }

    public static String getToken(Context context) {
        String token = getSharedPreferences(context).getString("token", "");
        return token == null || token.isEmpty() ? null : token;
    }

    public static void clear(Context context) {
        SharedPreferenceUtil.putString(context, "token", null);
    }
}
