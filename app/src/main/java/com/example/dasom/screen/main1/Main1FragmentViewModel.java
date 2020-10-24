package com.example.dasom.screen.main1;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dasom.model.ChatModel;
import com.example.dasom.screen.chat.ChatAdapter;

public class Main1FragmentViewModel extends ViewModel {

    @BindingAdapter("bindDiaryItems")
    public static void bindDiaryItems(RecyclerView recyclerView, ObservableArrayList<ChatModel> items) {
        DiaryAdapter adapter = (DiaryAdapter) recyclerView.getAdapter();
        if (adapter != null) adapter.setItem(items);
    }

    public ObservableArrayList<ChatModel> diaryItems;

    public Main1FragmentViewModel(){
        diaryItems = new ObservableArrayList<>();
    }
}
