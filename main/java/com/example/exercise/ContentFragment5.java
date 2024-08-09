package com.example.exercise;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ContentFragment5 extends Fragment {

    protected View mView;
    private RecyclerView leftList;
    private FrameLayout contentFrame;


    public ContentFragment5() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_content5, container, false);

        leftList = mView.findViewById(R.id.left_list);
        contentFrame = mView.findViewById(R.id.content_frame);
        setupRecyclerView();
        //TextView tv_first = mView.findViewById(R.id.tv_content1);
        //tv_first.setText("Content for A");
        if (savedInstanceState == null) {
            Fragment defaultFragment = new ContentFragment1_default();
            getChildFragmentManager().beginTransaction().replace(R.id.content_frame,defaultFragment).commit();
        }
        return mView;

    }

    private void setupRecyclerView() {
        leftList.setLayoutManager(new LinearLayoutManager(getContext()));

        MyAdapter<String> adapter = new MyAdapter<>(getDummyData(),R.layout.list_item_layout);
        adapter.setOnItemClickListener(this::onListItemClick);
        leftList.setAdapter(adapter);
    }

    private List<String> getDummyData() {
        List<String> data = new ArrayList<>();
        data.add("Option 1");
        data.add("Option 2");
        data.add("Option 3");
        data.add("Option 4");
        data.add("Option 5");
        data.add("Option 6");
        data.add("Option 7");
        data.add("Option 8");
        data.add("Option 9");
        data.add("Option 10");
        data.add("Option 11");
        data.add("Option 12");
        data.add("Option 13");
        return data;
    }

    private void onListItemClick(int position) {
        Fragment contentFragment;
        switch(position) {
            case 0:
                contentFragment = new ContentFragment5_1();
                break;
            case 1:
                contentFragment = new ContentFragment5_2();
                break;
            default:
                contentFragment = new ContentFragment5_1();
                break;

        }
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame, contentFragment);
        transaction.commit();
    }


}