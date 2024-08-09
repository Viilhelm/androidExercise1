package com.example.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ContentFragment1_1 extends Fragment {

    protected View mView;
    protected Context mContext;
    private RecyclerView recyclerView;

    public ContentFragment1_1() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_content_fragment1_1, container, false);
        recyclerView = mView.findViewById(R.id.right_list);
        setupRecyclerView();
        //TextView tv_first = mView.findViewById(R.id.text_view);
        //tv_first.setText("Content for A_1");
        return mView;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter<TestItem> adapter = new MyAdapter<>(getDataForOption1(), R.layout.right_list_item_layout);
        adapter.setOnItemClickListener(this::onListItemClick);
        recyclerView.setAdapter(adapter);
    }

    private List<TestItem> getDataForOption1() {
        List<TestItem> data = new ArrayList<>();
        data.add(new TestItem("4K Playback Capability Test", "Main/5.0 3840x2160@24fps AAC/48000Hz"));
        data.add(new TestItem("Dolby Playback Capability Test", "Main/5.1 3840x2160@24fps EAC3/48000Hz"));
        data.add(new TestItem("IMAX Playback Capability Test", "Main/5.0 3840x2160@24fps DTS HD/48000Hz"));
        return data;
    }

    private void onListItemClick(int position) {
        TestItem selectedItem = getDataForOption1().get(position);


        Intent intent = new Intent(getContext(), VideoActivity.class);
        intent.putExtra("EXTRA_POSITION", position);
        intent.putExtra("EXTRA_TITLE", selectedItem.getTitle());
        startActivity(intent);
    }
}