package com.example.dasom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.dasom.adapter.ChatAdapter;
import com.example.dasom.databinding.ActivityChatBinding;
import com.example.dasom.model.ChatModel;
import com.example.dasom.util.LinearLayoutManagerWrapper;
import com.example.dasom.util.UserCache;

public class ChatActivity extends AppCompatActivity {

    private ObservableArrayList<ChatModel> items = new ObservableArrayList<>();
    private ActivityChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        binding.setActivity(this);
        binding.setItems(items);
        binding.setIsListening(false);
        binding.setIsResult(false);

        LinearLayoutManagerWrapper wrapper = new LinearLayoutManagerWrapper(
                this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setLayoutManager(wrapper);

        ChatAdapter adapter = new ChatAdapter();
        binding.recyclerView.setAdapter(adapter);

        ChatModel model1 = new ChatModel();
        model1.setText("오늘은 무슨 일들이 있었나요? 일기에 남기고 싶은 일이 있나요?");
        model1.setMine(false);
        items.add(model1);

        ChatModel model2 = new ChatModel();
        model2.setText("오늘은 점심에 밥을 먹었어");
        model2.setMine(true);
        items.add(model2);

    }

    public void startListening(){
        binding.setIsListening(true);
    }

    public void stopListening(){
        binding.setIsListening(false);
        binding.setIsResult(true);
    }

    public void sendMessage(){
        binding.setIsResult(false);
    }


}