package com.example.exercise;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterForFragment5 extends MyAdapter<TestItem> {

    public MyAdapterForFragment5(List<TestItem> dataList, int layoutResId) {
        super(dataList, layoutResId);
    }

    public MyAdapterForFragment5(List<TestItem> dataList, int layoutResId, String fragmentType) {
        super(dataList, layoutResId, fragmentType);
    }

    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        TestItem item = dataList.get(position);
        holder.bind(item);
        holder.itemView.setActivated(position == selectedPosition);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
            notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);



        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("title", item.getTitle());
            intent.putExtra("description", item.getDescription());
            v.getContext().startActivity(intent);
        });


        holder.itemView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                v.setActivated(true);
            } else if (position != selectedPosition) {
                v.setActivated(false);
            }
        });

    }
}
