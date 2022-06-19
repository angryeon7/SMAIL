package com.example.smail.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class Fragment2 extends Fragment {
    @Nullable
    ListView list2view;
    ArrayList<String> DateList = new ArrayList<>();
    ArrayList<String> DateList_result = new ArrayList<>();
    ArrayAdapter adapter;
    static boolean calledAlready = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2,container,false);

        list2view = (ListView) view.findViewById(R.id.listview_date);
        //ListAdapter adapter = new ListAdapter();
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment2_listitem, DateList_result);
        list2view.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference().child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("파ㅇ어베이스");
                for (DataSnapshot userSnapshot : snapshot.getChildren()){
                    String str = userSnapshot.child("date").getValue(String.class);
                    Log.i("TAG: value is",str);

                    DateList.add(str);

                    for(String item : DateList){
                        if(!DateList_result.contains(item))
                            DateList_result.add(item);
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

        list2view.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), List_by_Date.class);
                //Object sValue = userList_result.get(position)
                //String selectedItem = (String) view.findViewById(R.id.tv_item).getTag().toString();
                //Toast.makeText(getContext(), "Clicked: " + position +" " + selectedItem, Toast.LENGTH_SHORT).show();
                intent.putExtra("person",DateList_result.get(position));
                startActivity(intent);
            }
        });



        return view;
    }
}