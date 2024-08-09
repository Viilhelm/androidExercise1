package com.example.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter<T> extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<T> dataList;
    private int layoutResId;
    private OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public MyAdapter(List<T> dataList, int layoutResId) {
        this.dataList = dataList;
        this.layoutResId = layoutResId;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }



    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutResId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        T item = dataList.get(position);
        holder.bind(item);
        holder.itemView.setActivated(position == selectedPosition);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
            notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);

            if (item instanceof TestItem) {
                TestItem testItem = (TestItem) item;
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("title", testItem.getTitle());
                intent.putExtra("description", testItem.getDescription());
                v.getContext().startActivity(intent);
            }


        });


        holder.itemView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                v.setActivated(true);
            } else if (position != selectedPosition) {
                v.setActivated(false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView subTextView;
        private TextView extraTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);
            subTextView = itemView.findViewById(R.id.item_subtext);
            extraTextView = itemView.findViewById(R.id.item_extra_text);
        }

        public void bind(T item) {
            if (item instanceof String) {
                textView.setText((String) item);
                if (subTextView != null) subTextView.setVisibility(View.GONE);
                if (extraTextView != null) extraTextView.setVisibility(View.GONE);
            } else if (item instanceof TestItem) {
                TestItem testItem = (TestItem) item;
                textView.setText(testItem.getTitle());
                if (subTextView != null) {
                    subTextView.setText(testItem.getDescription());
                    subTextView.setVisibility(View.VISIBLE);
                }
                if (extraTextView != null) {
                    extraTextView.setText(testItem.getExtraInfo());
                    extraTextView.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
