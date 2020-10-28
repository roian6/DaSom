package com.example.dasom.screen.diary;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.dasom.R;
import com.example.dasom.databinding.ActivityDiaryInfoBinding;
import com.example.dasom.model.ChatModel;

import java.util.ArrayList;

public class DiaryInfoActivity extends AppCompatActivity {

    private ActivityDiaryInfoBinding binding;
    private DiaryInfoViewModel viewModel;

    private TTSHelper ttsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diary_info);
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new DiaryInfoActivityClickHandler());

        viewModel = new ViewModelProvider(this).get(DiaryInfoViewModel.class);
        binding.setViewModel(viewModel);

        Bundle bundle = getIntent().getExtras();
        ArrayList<ChatModel> models = new ArrayList<>(bundle.getParcelableArrayList("diaryList"));

        viewModel.diaryList.addAll(models);
        for (int i = 0; i < models.size(); i++)
            viewModel.fragments.add(DiaryInfoFragment.newInstance());

        DialogInfoPagerAdapter pagerAdapter = new DialogInfoPagerAdapter(this, viewModel.fragments);
        binding.pagerDiaryInfo.setAdapter(pagerAdapter);

        ttsHelper = new TTSHelper(this);
        ttsHelper.setupTTSHelper(isSuccessful -> {
            if (!isSuccessful) return;
            viewModel.setOnPlayBtnClick(text -> {
                viewModel.isPlaying.setValue(true);
                ttsHelper.startTTS(text, () -> runOnUiThread(() -> viewModel.isPlaying.setValue(false)));
            });
        });

        viewModel.isPlaying.observe(this, b -> {
            if (!b) ttsHelper.stopTTS();
        });
    }

    public class DiaryInfoActivityClickHandler {
        public void btnBackClick() {
            finish();
        }
    }
}