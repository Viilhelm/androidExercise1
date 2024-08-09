package com.example.exercise;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;


public class ContentFragment0 extends Fragment {

    protected View mView;
    protected Context mContext;
    private TabLayout tabLayout;
    private FragmentContainerView fragmentContainerView;

    public ContentFragment0() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_content0, container, false);

        tabLayout = mView.findViewById(R.id.fc_tabLayout);
        fragmentContainerView = mView.findViewById(R.id.fragment_container);

        //TextView tv_first = mView.findViewById(R.id.tv_content0);
        //tv_first.setText("Content for 0");

        tabLayout.addTab(tabLayout.newTab().setText("Overview"));
        tabLayout.addTab(tabLayout.newTab().setText("CPU"));
        tabLayout.addTab(tabLayout.newTab().setText("Display"));
        tabLayout.addTab(tabLayout.newTab().setText("Decoder"));

        if (savedInstanceState == null) {
            switchFragment(new ContentFragment0_overview());
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0:
                        switchFragment(new ContentFragment0_overview());
                        break;
                    case 1:
                        switchFragment(new ContentFragment0_cpu());
                        break;
                    case 2:
                        switchFragment(new ContentFragment0_display());
                        break;
                    case 3:
                        switchFragment(new ContentFragment0_decoder());
                        break;
                    default:
                        switchFragment(new ContentFragment0_overview());
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return mView;

    }

    private void switchFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }
}