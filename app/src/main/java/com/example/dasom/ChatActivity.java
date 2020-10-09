package com.example.dasom;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dasom.adapter.ChatAdapter;
import com.example.dasom.databinding.ActivityChatBinding;
import com.example.dasom.model.ChatModel;
import com.example.dasom.util.LinearLayoutManagerWrapper;
import com.example.dasom.util.TokenCache;

public class ChatActivity extends AppCompatActivity {

    private VoiceHelper voiceHelper;
    private ChatNetwork chatNetwork;

    private ActivityChatBinding binding;
    private ChatActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new ChatActivityClickHandler());

        viewModel = ViewModelProviders.of(this).get(ChatActivityViewModel.class);
        binding.setViewModel(viewModel);

        binding.recyclerView.setLayoutManager(new LinearLayoutManagerWrapper(
                this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(new ChatAdapter());

        chatNetwork = new ChatNetwork(getString(R.string.base_url), TokenCache.getToken(this));

        voiceHelper = new VoiceHelper(this);
        voiceHelper.setupVoice(isSuccessful -> {
            if (!isSuccessful) {
                Toast.makeText(this, R.string.error_chat_voice, Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            viewModel.isVoiceSet.setValue(true);
        });

        voiceHelper.setupRecognitionListener(
                () -> viewModel.isListening.setValue(false),
                result -> {
                    viewModel.message.setValue(result);
                    viewModel.onResult.setValue(true);
                },
                e -> Toast.makeText(this, R.string.error_voice_recognize, Toast.LENGTH_SHORT).show());

        viewModel.chatModels.add(new ChatModel(getString(R.string.chat_intro), false));
        viewModel.isVoiceSet.observe(this, enabled -> {
            if (enabled) voiceHelper.startTTS(getString(R.string.chat_intro_short));
        });

    }

    public class ChatActivityClickHandler {
        public void btnMiddleClick() {
            if (viewModel.onResult.getValue()) sendChat(viewModel.message.getValue());
            else startListening();
        }

        public void btnRetryClick() {
            viewModel.onResult.setValue(false);
            startListening();
        }

        public void btnStopClick() {
            stopListening();
        }
    }

    public void startListening() {
        voiceHelper.startSTT();
        viewModel.isListening.setValue(true);
    }

    public void stopListening() {
        voiceHelper.stopSTT();
        viewModel.isListening.setValue(false);
    }

    private void sendChat(String message) {
        viewModel.chatModels.add(new ChatModel(viewModel.message.getValue(), true));
        binding.recyclerView.smoothScrollToPosition(viewModel.chatModels.size() - 1);

        viewModel.message.setValue("");
        viewModel.onResult.setValue(false);

        chatNetwork.sendChat(createChatModel(message), body -> {
            viewModel.chatModels.add(new ChatModel(body));
            binding.recyclerView.smoothScrollToPosition(viewModel.chatModels.size() - 1);
            voiceHelper.startTTS(body.getText(), () -> performAction(body.getAction()));
        }, errorMsg -> Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show());
    }

    private ChatModel createChatModel(String message) {
        return new ChatModel(DateTimeUtil.getDate(), DateTimeUtil.getTime(), message);
    }

    private void performAction(String action) {
        runOnUiThread(() -> {
            switch (action) {
                case "start":
                    break;
                case "read":
                    break;
                case "continue":
                    break;
                case "stop":
                    break;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        voiceHelper.destroy();
    }
}