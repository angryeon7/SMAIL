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
import java.util.List;

public class Fragment1 extends Fragment {
    @Nullable
    ListView listview;
    List userList = new ArrayList<>();
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
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_person, userList);
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
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG: ", "Failed to read value", error.toException());
                System.out.println("파ㅇ어베이스 실패");
            }
        });

/*        listview = (ListView) view.findViewById(R.id.listview);
        ListAdapter adapter = new ListAdapter();

        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"김서희"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"발신자 입력"));*/
   /*     adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"김일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"김일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"나일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"다일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"김일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"나일이"));
        adapter.addItem(new Listitem_Person(R.drawable.ic_launcher_foreground,"다일이"));*/

       /* listview.setAdapter(adapter);*/


        return view;
    }
}