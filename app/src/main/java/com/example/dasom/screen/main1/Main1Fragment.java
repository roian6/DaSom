package com.example.dasom.screen.main1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.dasom.screen.login.LoginActivity;
import com.example.dasom.R;
import com.example.dasom.databinding.FragmentMain1Binding;
import com.example.dasom.util.TokenCache;
import com.example.dasom.util.UserCache;

import org.jetbrains.annotations.NotNull;

public class Main1Fragment extends Fragment {

    public static Main1Fragment newInstance() {
        return new Main1Fragment();
    }

    private Context mContext;
    private FragmentMain1Binding binding;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main1, container, false);

        binding.btnMain1Logout.setOnClickListener(view -> {
            UserCache.clear(mContext);
            TokenCache.clear(mContext);
            startActivity(new Intent(mContext, LoginActivity.class));

            Activity a = getActivity();
            if(a!=null) a.finish();
        });

        return binding.getRoot();
    }
}
