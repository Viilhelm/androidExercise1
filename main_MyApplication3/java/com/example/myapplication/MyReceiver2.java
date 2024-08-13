package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

public class MyReceiver2 extends BroadcastReceiver {
    private final static String ORDERED_ACTION = "com.example.myapplication.MY_ORDERED_BROADCAST";
    private TextView orderTextView;

    public MyReceiver2(TextView orderTextView) {
        this.orderTextView = orderTextView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        if(intent != null && intent.getAction().equals(ORDERED_ACTION)) {
            orderTextView.append("\nMyReceiver2 received the ordered broadcast.");
        }
        Toast.makeText(context, "Receiver 2 received the broadcast", Toast.LENGTH_SHORT).show();

    }
}