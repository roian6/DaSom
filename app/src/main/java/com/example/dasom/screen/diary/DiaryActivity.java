package com.example.dasom.screen.diary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.dasom.R;
import com.example.dasom.databinding.ActivityDiaryBinding;

public class DiaryActivity extends AppCompatActivity {

    private ActivityDiaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diary);
        binding.setLifecycleOwner(this);
    }
}