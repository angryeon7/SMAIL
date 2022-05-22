package com.example.smail.tabFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.smail.CameraFragment;
import com.example.smail.HomeTab;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeTab();
            case 1:
                return new calender();
            case 2:
                return new CameraFragment();
            default:
                return new HomeTab();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
