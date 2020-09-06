package com.example.dasom.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import androidx.preference.PreferenceManager;

public class UserCache {

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    public static void setUser(Context context,String model) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("user_json", model).apply();

    }

    public static String getUser(Context context) {
        try {
            return getSharedPreferences(context).getString("user_json", "");
        } catch (Exception e) {
            return null;
        }
    }

    public static void clear(Context context) {
        SharedPreferenceUtil.putString(context, "user_json", null);
    }
}
