package com.example.dasom.util;

import android.content.Context;

import com.example.dasom.model.UserModel;
import com.google.gson.Gson;

public class UserCache {

    private static Gson gson = new Gson();

    public static void setUser(Context context, UserModel model) {
        SharedPreferenceUtil.putString(context, "user_json", gson.toJson(model));
    }

    public static UserModel getUser(Context context) {
        String user = SharedPreferenceUtil.getString(context, "user_json", "");
        return user.isEmpty() ? null : gson.fromJson(user, UserModel.class);
    }

    public static void clear(Context context) {
        SharedPreferenceUtil.putString(context, "user_json", null);
    }
}
