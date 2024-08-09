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


public class ContentFragment1 extends Fragment {

    protected View mView;
    private RecyclerView leftList;
    private FrameLayout contentFrame;


    public ContentFragment1() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_content1, container, false);

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
        data.add("1-视频播放能力检测");
        data.add("2-音频播放能力检测");
        data.add("3-DRM播放能力检测");
        return data;
    }

    private void onListItemClick(int position) {
        Fragment contentFragment;
        switch(position) {
            case 0:
                contentFragment = new ContentFragment1_1();
                break;
            case 1:
                contentFragment = new ContentFragment1_2();
                break;
            case 2:
                contentFragment = new ContentFragment1_3();
                break;
            default:
                contentFragment = new ContentFragment1_default();
                break;

        }
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame, contentFragment);
        transaction.commit();
    }


}