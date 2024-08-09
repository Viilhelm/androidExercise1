package com.example.exercise;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ContentFragment0_overview extends Fragment {

    private View mView;

    private TextView tvManufacturer;
    private TextView tvModel;
    private TextView tvDevice;
    private TextView tvBoard;


    public ContentFragment0_overview() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_content_fragment0_overview, container, false);

        tvManufacturer = mView.findViewById(R.id.tv_manufacturer);
        tvModel = mView.findViewById(R.id.tv_model);
        tvDevice = mView.findViewById(R.id.tv_device);
        tvBoard = mView.findViewById(R.id.tv_board);

        populateDeviceInfo();

        return mView;

    }

    @SuppressLint("SetTextI18n")
    private void populateDeviceInfo() {
        tvManufacturer.setText(Build.MANUFACTURER);
        tvModel.setText(Build.MODEL);
        tvDevice.setText(Build.DEVICE);
        tvBoard.setText(Build.BOARD);
    }
}