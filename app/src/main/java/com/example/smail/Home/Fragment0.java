package com.example.smail.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smail.R;

public class Fragment0 extends Fragment {


    GridView gridview;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment0,container,false);


        gridview = (GridView) view.findViewById(R.id.gridView);
        GridListAdapter adapter = new GridListAdapter();

        adapter.addItem(new ListItem(R.drawable.ic_launcher_foreground,"김일이","생일 축하해","2022.02.04"));
        adapter.addItem(new ListItem(R.drawable.ic_launcher_foreground,"김삼사","결혼 기념일","2022.03.02"));
        adapter.addItem(new ListItem(R.drawable.ic_launcher_foreground,"이사오","스승의 날","2022.05.15."));
        adapter.addItem(new ListItem(R.drawable.ic_launcher_foreground,"김일이","생일 축하해","2022.02.04"));
        adapter.addItem(new ListItem(R.drawable.ic_launcher_foreground,"김삼사","결혼 기념일","2022.03.02"));
        adapter.addItem(new ListItem(R.drawable.ic_launcher_foreground,"이사오","스승의 날","2022.05.15."));
        adapter.addItem(new ListItem(R.drawable.ic_launcher_foreground,"김일이","생일 축하해","2022.02.04"));
        adapter.addItem(new ListItem(R.drawable.ic_launcher_foreground,"김삼사","결혼 기념일","2022.03.02"));
        adapter.addItem(new ListItem(R.drawable.ic_launcher_foreground,"이사오","스승의 날","2022.05.15."));
        gridview.setAdapter(adapter);


        return view;
    }
}
