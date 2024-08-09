package com.example.exercise;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ContentFragment1_3 extends Fragment {

    protected View mView;
    protected Context mContext;

    public ContentFragment1_3() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_content_fragment1_3, container, false);
        TextView tv_first = mView.findViewById(R.id.text_view);
        tv_first.setText("Content for A_3");
        return mView;

    }
}