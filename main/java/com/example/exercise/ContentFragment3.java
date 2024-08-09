package com.example.exercise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ContentFragment3 extends Fragment {

    protected View mView;
    protected Context mContext;

    public ContentFragment3() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_content3, container, false);
        TextView titleTextView = mView.findViewById(R.id.titleTextView);
        TextView descriptionTextView = mView.findViewById(R.id.descriptionTextView);
        Button startTestButton = mView.findViewById(R.id.startTestButton);
        //TextView tv_first = mView.findViewById(R.id.tv_content3);
        //tv_first.setText("Content for C");

        startTestButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(),TestActivity.class);
            startActivity(intent);
        });

        return mView;

    }
}