package com.example.smail;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.smail.Home.Fragment0;
import com.example.smail.Home.Fragment1;
import com.example.smail.Home.Fragment2;
import com.example.smail.Home.Fragment3;
import com.example.smail.Home.HomeViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeTab extends Fragment{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View mView;
    private Button searchB;

    public HomeTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.home, container, false);

        tabLayout = mView.findViewById(R.id.tabs);
        viewPager = mView.findViewById(R.id.pager);
        searchB = mView.findViewById(R.id.searchIcon);;;;;;

        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        searchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search_ = new Intent( getActivity(),SearchTab.class);
                startActivity(search_);
            }
        });
        return mView;
    }

}