package com.example.dasom.screen.chat;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dasom.model.ChatModel;

public class ChatActivityViewModel extends ViewModel {

    @BindingAdapter("bindChatModels")
    public static void bindChatModels(RecyclerView recyclerView, ObservableArrayList<ChatModel> items) {
        ChatAdapter adapter = (ChatAdapter) recyclerView.getAdapter();
        if (adapter != null) adapter.setItem(items);
    }

    public ObservableArrayList<ChatModel> chatModels = new ObservableArrayList<>();

    public MutableLiveData<Boolean> isVoiceSet = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> isListening = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> onResult = new MutableLiveData<>(false);

    public MutableLiveData<String> message = new MutableLiveData<>("");



}
