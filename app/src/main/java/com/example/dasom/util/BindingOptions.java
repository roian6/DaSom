package com.example.dasom.util;

import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dasom.adapter.ChatAdapter;
import com.example.dasom.model.ChatModel;

public class BindingOptions {

    @BindingConversion
    public static int convertBooleanToVisibility(boolean visible) {
        return visible ? View.VISIBLE : View.GONE;
    }

    @BindingAdapter("chatItem")
    public static void bindChatItem(RecyclerView recyclerView, ObservableArrayList<ChatModel> items) {
        ChatAdapter adapter = (ChatAdapter) recyclerView.getAdapter();
        if (adapter != null) adapter.setItem(items);
    }

}
