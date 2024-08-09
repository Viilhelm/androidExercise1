package com.example.exercise;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ContentFragment2 extends Fragment {

    protected View mView;
    protected Context mContext;

    public ContentFragment2() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_content2, container, false);
        TextView tv_first = mView.findViewById(R.id.tv_content2);
        tv_first.setText("Content for B");
        return mView;

    }
}