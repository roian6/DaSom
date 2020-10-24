package com.example.dasom.screen.main1;

import android.util.Log;

import com.example.dasom.api.NetworkHelper;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiaryNetwork {

    private String baseUrl, token;

    private OnDiarySuccessListener onDiarySuccessListener;
    private OnDiaryFailedListener onDiaryFailedListener;

    public interface OnDiarySuccessListener {
        void onDiarySuccess(List<DiaryData> dataList);
    }

    public interface OnDiaryFailedListener {
        void onDiaryFailed(String errorMsg);
    }

    public DiaryNetwork(String baseUrl, String token) {
        this.baseUrl = baseUrl;
        this.token = token;
    }

    public void getAllDiary(OnDiarySuccessListener s, OnDiaryFailedListener e) {
        onDiarySuccessListener = s;
        onDiaryFailedListener = e;

        NetworkHelper.getInstance(baseUrl).getAllDiary("Bearer " + token)
                .enqueue(new Callback<DiaryBody>() {
                    @Override
                    public void onResponse(@NotNull Call<DiaryBody> call, @NotNull Response<DiaryBody> response) {
                        DiaryBody body;
                        if (response.code() != 200) {
                            try {
                                Gson gson = new Gson();
                                body = gson.fromJson(response.errorBody().string(), DiaryBody.class);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                return;
                            }
                        } else body = response.body();

                        Log.d("baam", "onResponse: "+new Gson().toJson(body));
                        onDiarySuccessListener.onDiarySuccess(body.getData());
                    }

                    @Override
                    public void onFailure(@NotNull Call<DiaryBody> call, @NotNull Throwable t) {
                        t.printStackTrace();
                        onDiaryFailedListener.onDiaryFailed(t.getLocalizedMessage());
                    }
                });
    }
}
