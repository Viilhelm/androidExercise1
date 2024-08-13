package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView resultTextView;
    private final static String ORDERED_ACTION = "com.example.myapplication.MY_ORDERED_BROADCAST";
    private TextView orderTextView;
    private TextView logTextView;
    private BroadcastReceiver logReceiver;
    private BroadcastReceiver receiver3;
    private TextView broadcastTextView;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode() == RESULT_OK && result.getData() != null){
            String responseData = result.getData().getStringExtra("responseData");
            resultTextView.setText(responseData);
        }
    });

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView sendTextView = findViewById(R.id.send_text_view);
        resultTextView = findViewById(R.id.response_text_view);
        orderTextView = findViewById(R.id.order_text_view);
        // 找到按钮并设置点击事件监听器
        Button button = findViewById(R.id.button_to_second_activity);
        button.setOnClickListener(view -> {
            // 创建 Intent 并启动 SecondActivity
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("request content", sendTextView.getText().toString());
            intent.putExtras(bundle);
            activityResultLauncher.launch(intent);
        });

        Button registerButton = findViewById(R.id.button_to_register_activity);
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });


        BroadcastReceiver orderReceiver1 = new MyReceiver1(orderTextView);
        IntentFilter filter1 = new IntentFilter(ORDERED_ACTION);
        filter1.setPriority(1); // 设置过滤器A的优先级，数值越大优先级越高
        registerReceiver(orderReceiver1, filter1); // 注册接收器A，注册之后才能正常接收广播

        BroadcastReceiver orderReceiver2 = new MyReceiver2(orderTextView); // 创建一个有序广播的接收器B
        // 创建一个意图过滤器A，只处理ORDER_ACTION的广播
        IntentFilter filter2 = new IntentFilter(ORDERED_ACTION);
        filter2.setPriority(2); // 设置过滤器B的优先级，数值越大优先级越高
        registerReceiver(orderReceiver2, filter2); // 注册接收器B，注册之后才能正常接收广播


        Button sendBroadcastButton = findViewById(R.id.button_to_send_broadcast);
        sendBroadcastButton.setOnClickListener(v -> {
            Intent intent = new Intent(ORDERED_ACTION);
            sendOrderedBroadcast(intent, null);
        });

        logTextView = findViewById(R.id.log_text_view);
        Button startServiceButton = findViewById(R.id.button_to_start_service);
        Button stopServiceButton = findViewById(R.id.button_to_stop_service);
        startServiceButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        });
        stopServiceButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
        });

        logReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.getAction().equals(MyService.LOG_ACTION)) {
                    String logMessage = intent.getStringExtra(MyService.LOG_KEY);
                    logTextView.append(logMessage + "\n");
                }
            }
        };
        IntentFilter logFilter = new IntentFilter(MyService.LOG_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(logReceiver, logFilter);

        broadcastTextView = findViewById(R.id.broadcast_text_view);
        Button buttonFoo = findViewById(R.id.button_foo);
        Button buttonBaz = findViewById(R.id.button_baz);
        buttonFoo.setOnClickListener(v -> {
            MyIntentService.startActionFoo(this, "param1", "param2");
        });
        buttonBaz.setOnClickListener(v -> {
            MyIntentService.startActionBaz(this, "param1", "param2");
        });

        receiver3 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && intent.getAction().equals(MyIntentService.BROADCAST_ACTION)) {
                    String broadcastMessage = intent.getStringExtra(MyIntentService.BROADCAST_KEY);
                    broadcastTextView.append(broadcastMessage + "\n");
                }
            }
        };
        IntentFilter filter3 = new IntentFilter(MyIntentService.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver3, filter3);

        //Content Provider
        ContentValues values = new ContentValues();
        values.put("name", "John Doe");
        values.put("grade", "A");
        Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
        Log.d(TAG, "Inserted URI:" + uri);

        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String grade = cursor.getString(cursor.getColumnIndex("grade"));
                Log.d(TAG, "Name: " + name + ", Grade: " + grade);
            }
            cursor.close();
        }

        ContentValues updateValues = new ContentValues();
        updateValues.put("grade", "B");
        int rowsUpdated = getContentResolver().update(MyContentProvider.CONTENT_URI, updateValues, "name = ?", new String[]{"John Doe"});
        Log.d(TAG, "Rows Updated: " + rowsUpdated);

        int rowsDeleted = getContentResolver().delete(MyContentProvider.CONTENT_URI, "name = ?", new String[]{"John Doe"});
        Log.d(TAG, "Rows Deleted: " + rowsDeleted);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(logReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver3);

    }


}