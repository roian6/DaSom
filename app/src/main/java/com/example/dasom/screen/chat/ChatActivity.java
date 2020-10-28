package com.example.dasom.screen.chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dasom.R;
import com.example.dasom.databinding.ActivityChatBinding;
import com.example.dasom.model.ChatModel;
import com.example.dasom.screen.diary.DiaryInfoActivity;
import com.example.dasom.util.DateTimeUtil;
import com.example.dasom.util.KeyboardUtil;
import com.example.dasom.util.LinearLayoutManagerWrapper;
import com.example.dasom.util.TokenCache;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;

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
                e -> {
                    Toast.makeText(this, R.string.error_voice_recognize, Toast.LENGTH_SHORT).show();
                    voiceHelper.startTTS(getString(R.string.voice_recognize_retry), null);
                });

        viewModel.chatModels.add(new ChatModel(getString(R.string.chat_intro), null, false));
        viewModel.isVoiceSet.observe(this, enabled -> {
            if (enabled) voiceHelper.startTTS(getString(R.string.chat_intro_short), null);
        });

    }

    public class ChatActivityClickHandler {
        public void btnBackClick() {
            finish();
        }

        public void btnMiddleClick() {
            KeyboardUtil.hideKeyboard(ChatActivity.this);
            if (viewModel.onResult.getValue())
                sendChat(viewModel.message.getValue(), viewModel.image.getValue());
            else {
                viewModel.onResult.setValue(false);
                startListening();
            }
        }

        public void btnRetryClick() {
            KeyboardUtil.hideKeyboard(ChatActivity.this);
            viewModel.onResult.setValue(false);
            startListening();
        }

        public void btnImageClick() {
            KeyboardUtil.hideKeyboard(ChatActivity.this);
            if (viewModel.image.getValue() == null)
                TedBottomPicker.with(ChatActivity.this).show(uri -> viewModel.image.setValue(uri));
            else viewModel.image.setValue(null);
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

    private void sendChat(String message, Uri photo) {
        viewModel.chatModels.add(new ChatModel(viewModel.message.getValue(), photo, true));
        binding.recyclerView.smoothScrollToPosition(viewModel.chatModels.size() - 1);

        viewModel.message.setValue("");
        viewModel.onResult.setValue(false);
        viewModel.image.setValue(null);

        chatNetwork.sendChat(createChatModel(message), photo, body -> {
            viewModel.chatModels.add(new ChatModel(body));
            binding.recyclerView.smoothScrollToPosition(viewModel.chatModels.size() - 1);
            voiceHelper.startTTS(body.getText(), () -> performAction(body.getAction(), body.getData()));
        }, errorMsg -> Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show());
    }

    private ChatModel createChatModel(String message) {
        return new ChatModel(DateTimeUtil.getDate(), DateTimeUtil.getTime(), message);
    }

    private void performAction(String action, List<ChatModel> diaryList) {
        runOnUiThread(() -> {
            switch (action) {
                case "start":
                    break;
                case "read":
                    if (diaryList == null) return;
                    Intent intent = new Intent(ChatActivity.this, DiaryInfoActivity.class);
                    Bundle bundle = new Bundle();

                    ArrayList<ChatModel> chatModels = new ArrayList<>(diaryList);
                    bundle.putParcelableArrayList("diaryList", chatModels);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case "continue":
                    break;
                case "stop":
                    finish();
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