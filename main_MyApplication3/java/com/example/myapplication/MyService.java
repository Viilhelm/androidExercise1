package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    public final static String LOG_ACTION = "com.example.myapplication.MY_LOG_BROADCAST";
    public final static String LOG_KEY = "com.example.myapplication.MY_LOG_KEY";
    private volatile boolean isStopped = false;
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"Service Created");
        sendLogMessage("Service Created");
        createNotificationIntent();
    }

    @SuppressLint("ForegroundServiceType")
    public int onStartCommand(Intent intent, int flags, int startID) {
        Log.d(TAG,"Service Started");
        sendLogMessage("Service Started");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Service is running in the foreground")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1,notification);

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                if (isStopped) {
                    Log.d(TAG, "Task completed");
                    sendLogMessage("Task completed");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    Log.d(TAG, "Running task" + i);
                    sendLogMessage("Running task" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (!isStopped) {
                Log.d(TAG, "Task completed");
                sendLogMessage("Task completed");
            }
            stopSelf();
        }).start();

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isStopped = true;
        Log.d(TAG, "Service Destroyed");
        sendLogMessage("Service Destroyed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    private void createNotificationIntent() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private void sendLogMessage(String s) {
        Intent intent = new Intent(LOG_ACTION);
        intent.putExtra(LOG_KEY, s);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }


}