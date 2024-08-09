package com.example.exercise;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {
    private String[] mTitleArray;

    public TabPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public TabPagerAdapter(FragmentManager fm, String[] titleArray) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mTitleArray = titleArray;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ContentFragment0();
            case 1:
                return new ContentFragment1();
            case 2:
                return new ContentFragment2();
            case 3:
                return new ContentFragment3();
            case 4:
                return new ContentFragment4();
            case 5:
                return new ContentFragment5();
            case 6:
                return new ContentFragment6();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTitleArray.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleArray[position];
    }
}
