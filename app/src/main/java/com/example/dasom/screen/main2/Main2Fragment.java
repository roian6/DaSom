package com.example.dasom.screen.main2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.dasom.R;
import com.example.dasom.api.NetworkHelper;
import com.example.dasom.databinding.FragmentMain2Binding;
import com.example.dasom.screen.login.LoginActivity;
import com.example.dasom.util.TokenCache;
import com.example.dasom.util.UserCache;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Fragment extends Fragment {

    public static Main2Fragment newInstance() {
        return new Main2Fragment();
    }

    //i want commit

    private Context mContext;
    private FragmentMain2Binding binding;
    private SharedPreferences preferences;
    private static final String BASE_URL = "https://api.taemin.dev/dasomi/";
    private String token;
    private SettingData body;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main2, container, false);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("talk_cycle_time", Context.MODE_PRIVATE);

        token = TokenCache.getToken(mContext);

        updateInfo(BASE_URL, token);

        binding.updateInfoDialogBt.setOnClickListener(v -> {
            UpdateEmergencyDialog updateEmergencyDialog = new UpdateEmergencyDialog(mContext);
            updateEmergencyDialog.showDialog();

        });

        binding.btnMainLogout.setOnClickListener(view -> {
            UserCache.clear(mContext);
            TokenCache.clear(mContext);
            startActivity(new Intent(mContext, LoginActivity.class));

            Activity a = getActivity();
            if (a != null) a.finish();
        });

        binding.talkCycleChangingTv.setText(sharedPreferences.getString("talk_cycle_time", "30분 >"));

        binding.talkCycleChangingTv.setOnClickListener(v -> {

            TimeDialog timeDialog = new TimeDialog(mContext);
            timeDialog.callFunction(binding.talkCycleChangingTv);

        });

        return binding.getRoot();

    }

    public void updateInfo(String url, String token) {
        NetworkHelper.getInstance(url).updateInfo("Bearer " + token).enqueue(new Callback<SettingData>() {
            @Override
            public void onResponse(Call<SettingData> call, Response<SettingData> response) {
                if (response.code() != 200) {
                    try {
                        Gson gson = new Gson();
                        body = gson.fromJson(response.errorBody().string(), SettingData.class);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        return;
                    }
                } else {
                    body = response.body();
                }
                Log.d("baam", "onResponse: " + body);
                Log.d("baam", "onResponse: " + new Gson().toJson(body));
                try {
                    binding.setWhenCreate(body.getData().getDataLength() + "");
                    binding.setUntilNow(body.getData().getLastDataCount() + "");
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    binding.setWhenCreate("0");
                    binding.setUntilNow("0");
                }

            }

            @Override
            public void onFailure(Call<SettingData> call, Throwable t) {

            }
        });


    }
}
