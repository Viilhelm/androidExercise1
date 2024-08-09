package com.example.exercise;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.CheckBox;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

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


        // 设置CheckBox默认值
        //CheckBox blackScreenCheckBox = findViewById(R.id.checkbox_black_screen);
        //CheckBox flowerScreenCheckBox = findViewById(R.id.checkbox_flower_screen);
        //CheckBox stuckCheckBox = findViewById(R.id.checkbox_stuck);
        //CheckBox syncCheckBox = findViewById(R.id.checkbox_sync);
        //CheckBox normalCheckBox = findViewById(R.id.checkbox_normal);

        //blackScreenCheckBox.setChecked(false);
        //flowerScreenCheckBox.setChecked(false);
        //stuckCheckBox.setChecked(false);
        //syncCheckBox.setChecked(false);
        //normalCheckBox.setChecked(true);




    }
}