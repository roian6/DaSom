package com.example.dasom;

import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dasom.model.ChatModel;

public class ChatActivityViewModel extends ViewModel {

    public ObservableArrayList<ChatModel> chatModels = new ObservableArrayList<>();

    public MutableLiveData<Boolean> isVoiceSet = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> isListening = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> onResult = new MutableLiveData<>(false);

    public MutableLiveData<String> message = new MutableLiveData<>("");



}
