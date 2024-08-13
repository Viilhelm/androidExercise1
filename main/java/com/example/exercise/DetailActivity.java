package com.example.exercise;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CheckBox;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN );

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("title", "默认标题");
            String description = extras.getString("description", "默认描述");

            TextView titleTextView = findViewById(R.id.title_text);
            TextView descriptionTextView = findViewById(R.id.description_text);

            titleTextView.setText(title);
            descriptionTextView.setText(description);
        }
        TextView titleTextView = findViewById(R.id.title_text);
        TextView descriptionTextView = findViewById(R.id.description_text);

        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");

        titleTextView.setText(title);
        descriptionTextView.setText(description);

        expandableListView = findViewById(R.id.expandable_list);
        initData();
        expandableListAdapter = new ExpandableListAdapter(this, listDataHeader, listHashMap);
        expandableListView.setAdapter(expandableListAdapter);

        LinearLayout cancelContainer = findViewById(R.id.cancel);
        cancelContainer.setOnClickListener(v -> {
            finish();
        });


    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        listDataHeader.add("播放是否正常");

        List<String> subItems = new ArrayList<>();
        subItems.add("黑屏");
        subItems.add("花屏");
        subItems.add("卡顿");
        subItems.add("音画不同步");
        subItems.add("正常");

        listHashMap.put(listDataHeader.get(0), subItems);
    }
}