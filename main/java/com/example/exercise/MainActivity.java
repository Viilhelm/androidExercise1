package com.example.exercise;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import com.example.exercise.TabPagerAdapter;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private String[] mTitleArray = {"设备信息","高品质认证", "臻彩认证", "基础检测","扩展检测","检测任务","日志结果"};

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

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN );
        //getWindow().setStatusBarColor(Color.WHITE);
        //getWindow().setNavigationBarColor(Color.WHITE);


        Toolbar tl_head = findViewById(R.id.tl_head);
        setSupportActionBar(tl_head);
        getSupportActionBar().setTitle("");
        tabLayout = findViewById(R.id.tabLayout);
        initTabLayout();
        viewPager = findViewById(R.id.viewPager);
        initTabViewPager();

        viewPager.setCurrentItem(1);


    }

    private void initTabLayout() {
        //tabLayout.addTab(tabLayout.newTab().setText(mTitleArray[0]));
        //tabLayout.addTab(tabLayout.newTab().setText(mTitleArray[1]));
        //tabLayout.addOnTabSelectedListener(this);
    }

    private void initTabViewPager() {
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(), mTitleArray);
        viewPager.setAdapter((adapter));
        //viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            //@Override
            //public void onPageSelected(int position) {
                //tabLayout.getTabAt(position).select();
           // }
        //});
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }


}