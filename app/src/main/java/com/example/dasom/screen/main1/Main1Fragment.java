package com.example.dasom.screen.main1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dasom.R;
import com.example.dasom.databinding.FragmentMain1Binding;
import com.example.dasom.model.ChatModel;
import com.example.dasom.screen.diary.DiaryInfoActivity;
import com.example.dasom.util.LinearLayoutManagerWrapper;
import com.example.dasom.util.TokenCache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main1Fragment extends Fragment {

    public static Main1Fragment newInstance() {
        return new Main1Fragment();
    }

    private DiaryNetwork diaryNetwork;

    private FragmentMain1Binding binding;
    private Main1FragmentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main1, container, false);
        binding.setLifecycleOwner(requireActivity());

        viewModel = new ViewModelProvider(requireActivity()).get(Main1FragmentViewModel.class);
        binding.setViewModel(viewModel);

        binding.recyclerMain1.setLayoutManager(new LinearLayoutManagerWrapper(
                requireContext(), LinearLayoutManager.VERTICAL, false));

        DiaryAdapter adapter = new DiaryAdapter();
        adapter.setOnItemClickListener(item -> {
            Intent intent = new Intent(requireContext(), DiaryInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("diaryList", new ArrayList<>(Collections.singletonList(item)));
            intent.putExtras(bundle);
            startActivity(intent);
        });

        binding.recyclerMain1.setAdapter(adapter);

        diaryNetwork = new DiaryNetwork(getString(R.string.base_url), TokenCache.getToken(requireContext()));

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.diaryItems.clear();

        //*sad callback noise*
        diaryNetwork.getAllDiary(
                dataList -> {
                    viewModel.isLoaded.setValue(true);
                    if (dataList == null) return;
                    for (DiaryData data : dataList) {
                        List<ChatModel> chatModels = data.getData();
                        for (int j = 0; j < chatModels.size(); j++) {
                            ChatModel chatModel = chatModels.get(j);
                            if (j == 0) chatModel.setDate(data.getDate());
                            viewModel.diaryItems.add(chatModel);
                        }
                    }
                },
                errorMsg -> Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_SHORT).show());
    }
}
