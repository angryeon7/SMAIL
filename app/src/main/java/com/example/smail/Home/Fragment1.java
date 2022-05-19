package com.example.smail.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smail.ListAdapter;
import com.example.smail.Listitem_Person;
import com.example.smail.R;

public class Fragment1 extends Fragment {
    @Nullable
    ListView listview;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1,container,false);

        listview = (ListView) view.findViewById(R.id.listview);
        ListAdapter adapter = new ListAdapter();

        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"김일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"김일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"김일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"김일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"나일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"다일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"김일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"나일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"다일이"));

        listview.setAdapter(adapter);


        return view;
    }
}