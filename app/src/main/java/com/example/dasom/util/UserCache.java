package com.example.dasom.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class UserCache {

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUser(Context context, String user) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("user", user).apply();
    }

    public static String getUser(Context context) {
        String user = getSharedPreferences(context).getString("user", "");
        return user == null || user.isEmpty() ? null : user;
    }

    public static void clear(Context context) {
        SharedPreferenceUtil.putString(context, "user", null);
    }
}
