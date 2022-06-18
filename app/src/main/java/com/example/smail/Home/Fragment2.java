package com.example.smail.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.smail.ListAdapter;
import com.example.smail.Listitem_Person;
import com.example.smail.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {
    @Nullable


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2,container,false);


        /*listview = (ListView) view.findViewById(R.id.listview_date);
        ListAdapter adapter = new ListAdapter();

        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"김일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"김일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"김일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"나일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"다일이"));

        listview.setAdapter(adapter);*/


        return view;
    }
}