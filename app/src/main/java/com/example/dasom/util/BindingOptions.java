package com.example.dasom.util;

import android.view.View;

import androidx.databinding.BindingConversion;

public class BindingOptions {

    @BindingConversion
    public static int convertBooleanToVisibility(boolean visible) {
        return visible ? View.VISIBLE : View.GONE;
    }

}
