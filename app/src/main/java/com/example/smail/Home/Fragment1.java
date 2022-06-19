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

import com.example.smail.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Fragment1 extends Fragment {
    @Nullable
    ListView listview;
    List userList = new ArrayList<>();
    List userList_result = new ArrayList<>();
    ArrayAdapter adapter;
    static boolean calledAlready = false;
    /*@Override*/
/*    public void onCreate(Bundle savdInstanceState){
        super.onCreate(savdInstanceState);
    }*/
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1,container,false);

/*        if(!calledAlready)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledAlready = true;
        }*/
        listview = (ListView) view.findViewById(R.id.listview);
        //ListAdapter adapter = new ListAdapter();
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_person, userList_result);
        listview.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference().child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("파ㅇ어베이스");
                for (DataSnapshot userSnapshot : snapshot.getChildren()){
                    String str = userSnapshot.child("sender").getValue(String.class);
                    Log.i("TAG: value is",str);

                    userList.add(str);

                    for(Object item : userList){
                        if(!userList_result.contains(item))
                            userList_result.add(item);
                    }

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG: ", "Failed to read value", error.toException());
                System.out.println("파ㅇ어베이스 실패");
            }
        });


        return view;
    }
}