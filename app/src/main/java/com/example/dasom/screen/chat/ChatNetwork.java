package com.example.dasom.screen.chat;

import android.net.Uri;
import android.util.Log;

import com.example.dasom.api.NetworkHelper;
import com.example.dasom.model.ChatModel;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    public void sendChat(ChatModel chat, Uri photo, OnChatSuccessListener s, OnChatFailedListener e) {
        onChatSuccessListener = s;
        onChatFailedListener = e;

        RequestBody dateBody = RequestBody.create(MediaType.parse("multipart/form-data"), chat.getDate());
        RequestBody timeBody = RequestBody.create(MediaType.parse("multipart/form-data"), chat.getTime());
        RequestBody textBody = RequestBody.create(MediaType.parse("multipart/form-data"), chat.getText());
        MultipartBody.Part photoBody = null;

        if (photo != null) {
            File photoFile = new File(photo.getPath());
            RequestBody photoFileBody = RequestBody.create(MediaType.parse("multipart/form-data"), photoFile);
            photoBody = MultipartBody.Part.createFormData("photo", photoFile.getName(), photoFileBody);
        }

        NetworkHelper.getInstance(baseUrl).sendChat("Bearer " + token, photoBody,
                dateBody, timeBody, textBody).enqueue(new Callback<ChatBody>() {
            @Override
            public void onResponse(@NotNull Call<ChatBody> call, @NotNull Response<ChatBody> response) {
                ChatBody body;
                if (response.code() != 200) {
                    try {
                        Gson gson = new Gson();
                        body = gson.fromJson(response.errorBody().string(), ChatBody.class);
                    } catch (Exception e) {
                        Log.d("baam", "onResponse: "+response.toString());
                        e.printStackTrace();
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
