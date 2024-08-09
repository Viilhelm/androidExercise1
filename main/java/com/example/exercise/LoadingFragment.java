package com.example.exercise;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LoadingFragment extends Fragment {

    protected View mView;
    private static final String ARG_TITLE = "title";

    public LoadingFragment() {

    }

    public static LoadingFragment newInstance(String title) {
        LoadingFragment fragment = new LoadingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_loading, container, false);

        TextView titleTextView = mView.findViewById(R.id.loading_title);
        TextView loadingTextView = mView.findViewById(R.id.loading_text);

        if (getArguments() != null) {
            String title = getArguments().getString(ARG_TITLE);
            titleTextView.setText(title);
            loadingTextView.setText("正在缓冲");
        }
        return mView;
    }

}