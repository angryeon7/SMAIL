package com.example.smail.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.smail.HomeTab;
import com.example.smail.tabFragment.CameraTab;
import com.example.smail.tabFragment.calender;

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {
    public HomeViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Fragment0();

            case 1:
                return new Fragment1();

            case 2:
                return new Fragment2();

            case 3:
                return new Fragment3();

            default:
                return new Fragment0();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "모든 편지";

            case 1:
                return "인물별";

            case 2:
                return "날찌별";

            case 3:
                return "즐겨찾기";

            default:
                return "모든 편지";
        }
    }
}

