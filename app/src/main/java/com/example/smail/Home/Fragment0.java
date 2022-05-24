package com.example.smail.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smail.GridListAdapter;
import com.example.smail.ListItem;
import com.example.smail.R;
import com.example.smail.mailDetail;

import java.util.ArrayList;

public class Fragment0 extends Fragment {
    ArrayList<ListItem> items = new ArrayList<ListItem>();

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

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                Intent intent = new Intent(
                        getActivity().getApplicationContext(), // 현재화면의 제어권자
                        mailDetail.class);

                startActivity(intent);
            }
        });


        return view;
    }
}
