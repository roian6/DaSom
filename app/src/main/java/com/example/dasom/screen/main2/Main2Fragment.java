package com.example.dasom.screen.main2;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import com.example.dasom.databinding.FragmentMain2Binding;
import com.example.dasom.screen.login.LoginActivity;
import com.example.dasom.util.TokenCache;
import com.example.dasom.util.UserCache;

import org.jetbrains.annotations.NotNull;

public class Main2Fragment extends Fragment{

    public static Main2Fragment newInstance() {
        return new Main2Fragment();
    }

    private Context mContext;
    private FragmentMain2Binding binding;
    private AlarmManager alarmManager;
    private int hour,minute,cycle_time;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main2, container, false);
        // logout
//        binding.diaryOut.setOnClickListener(v -> {
//            UserCache.clear(mContext);
//            TokenCache.clear(mContext);
//            startActivity(new Intent(mContext, LoginActivity.class));
//
//            Activity a = getActivity();
//            if(a!=null) a.finish();
//        });

//        TODO: migrate logout from first tab
//        binding.btnMain1Logout.setOnClickListener(view -> {
//            UserCache.clear(mContext);
//            TokenCache.clear(mContext);
//            startActivity(new Intent(mContext, LoginActivity.class));
//
//            Activity a = getActivity();
//            if(a!=null) a.finish();
//        });


        binding.talkCycleChangingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog timeDialog = new TimeDialog(mContext);

                timeDialog.callFunction();
            }
        });
        return binding.getRoot();

    }
}
