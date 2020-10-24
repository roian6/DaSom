package com.example.dasom.screen.chat;

import com.example.dasom.api.NetworkHelper;
import com.example.dasom.model.ChatModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatNetwork {

    private String baseUrl, token;

    private OnChatSuccessListener onChatSuccessListener;
    private OnChatFailedListener onChatFailedListener;

    public interface OnChatSuccessListener {
        void onChatSuccess(ChatBody body);
    }

    public interface OnChatFailedListener {
        void onChatFailed(String errorMsg);
    }

    public ChatNetwork(String baseUrl, String token) {
        this.baseUrl = baseUrl;
        this.token = token;
    }

    public void sendChat(ChatModel chat, OnChatSuccessListener s, OnChatFailedListener e) {
        onChatSuccessListener = s;
        onChatFailedListener = e;

        NetworkHelper.getInstance(baseUrl).sendChat("Bearer " + token,
                chat.getDate(), chat.getTime(), chat.getText()).enqueue(new Callback<ChatBody>() {
            @Override
            public void onResponse(@NotNull Call<ChatBody> call, @NotNull Response<ChatBody> response) {
                ChatBody body;
                if (response.code() != 200) {
                    try {
                        Gson gson = new Gson();
                        body = gson.fromJson(response.errorBody().string(), ChatBody.class);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        return;
                    }
                } else body = response.body();

                onChatSuccessListener.onChatSuccess(body);
            }

            @Override
            public void onFailure(@NotNull Call<ChatBody> call, @NotNull Throwable t) {
                t.printStackTrace();
                onChatFailedListener.onChatFailed(t.getLocalizedMessage());
            }
        });
    }
}
