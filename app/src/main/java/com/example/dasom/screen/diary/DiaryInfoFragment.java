package com.example.dasom.screen.diary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dasom.R;
import com.example.dasom.databinding.FragmentDiaryInfoBinding;
import com.example.dasom.model.ChatModel;

public class DiaryInfoFragment extends Fragment {

    public static DiaryInfoFragment newInstance() {
        return new DiaryInfoFragment();
    }

    private FragmentDiaryInfoBinding binding;
    private DiaryInfoViewModel viewModel;

    private ChatModel model = new ChatModel();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary_info, container, false);
        binding.setLifecycleOwner(requireActivity());
        binding.setClickHandler(new DiaryInfoFragmentClickHandler());

        viewModel = new ViewModelProvider(requireActivity()).get(DiaryInfoViewModel.class);
        binding.setViewModel(viewModel);

        model = viewModel.diaryList.get(viewModel.fragments.indexOf(this));
        binding.setModel(model);

        return binding.getRoot();
    }

    public class DiaryInfoFragmentClickHandler {
        public void onPlayBtnClick() {
            viewModel.onPlayBtnClick.play(model.getText());
        }
    }
}
