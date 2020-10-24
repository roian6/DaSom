package com.example.dasom.screen.main1;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dasom.databinding.RowDiaryBinding;
import com.example.dasom.model.ChatModel;

import java.util.ArrayList;
import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryHolder> {

    private List<ChatModel> list;

    public DiaryAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public DiaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DiaryHolder(RowDiaryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryHolder holder, int position) {
        ChatModel item = list.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setItem(List<ChatModel> items) {
        this.list = items;
        notifyDataSetChanged();
    }

    static class DiaryHolder extends RecyclerView.ViewHolder {

        private RowDiaryBinding binding;

        DiaryHolder(RowDiaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ChatModel item) {
            binding.setItem(item);
        }
    }
}

