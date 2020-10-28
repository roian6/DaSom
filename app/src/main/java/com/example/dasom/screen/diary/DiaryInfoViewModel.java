package com.example.dasom.screen.diary;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dasom.model.ChatModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DiaryInfoViewModel extends ViewModel {

    public interface OnPlayBtnClickListener{
        void play(String text);
    }

    public OnPlayBtnClickListener onPlayBtnClick = text -> {};

    public ObservableArrayList<ChatModel> diaryList = new ObservableArrayList<>();
    public ObservableArrayList<Fragment> fragments = new ObservableArrayList<>();

    @BindingAdapter("bindTabMediator")
    public static void bindTabMediator(TabLayout tab, ViewPager2 pager) {
        new TabLayoutMediator(tab, pager, (t, position) -> {
            t.view.setClickable(false);
            t.view.setFocusable(false);
        }).attach();
    }

    public MutableLiveData<Integer> currentIndex = new MutableLiveData<>(0);

}
