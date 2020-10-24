package com.example.dasom.util;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dasom.screen.chat.ChatAdapter;
import com.example.dasom.model.ChatModel;

public class BindingOptions {

    @BindingConversion
    public static int convertBooleanToVisibility(Boolean visible) {
        return visible ? View.VISIBLE : View.GONE;
    }

    @BindingAdapter("bindInvisibility")
    public static void bindInvisibility(View v, Boolean b) {
        v.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("bindImageLink")
    public static void bindImageLink(ImageView view, String link) {
        if (link == null || link.isEmpty()) return;
        Glide.with(view).load(link).into(view);
    }

}
