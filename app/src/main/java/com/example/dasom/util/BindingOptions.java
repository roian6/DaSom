package com.example.dasom.util;

import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dasom.screen.chat.ChatAdapter;
import com.example.dasom.screen.chat.ChatModel;

public class BindingOptions {

    @BindingConversion
    public static int convertBooleanToVisibility(Boolean visible) {
        return visible ? View.VISIBLE : View.GONE;
    }

    @BindingAdapter("bindInvisibility")
    public static void bindInvisibility(View v, Boolean b) {
        v.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
    }

    @BindingAdapter("chatItem")
    public static void bindChatItem(RecyclerView recyclerView, ObservableArrayList<ChatModel> items) {
        ChatAdapter adapter = (ChatAdapter) recyclerView.getAdapter();
        if (adapter != null) adapter.setItem(items);
    }

}
