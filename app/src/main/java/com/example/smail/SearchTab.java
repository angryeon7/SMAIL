package com.example.smail;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class SearchTab extends AppCompatActivity {
    DatabaseReference ref;
    ArrayList<SearchListitem> list;
    RecyclerView recyclerView;
    SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchtab);

        ref = FirebaseDatabase.getInstance().getReference().child("Profile");
        recyclerView = findViewById(R.id.rv);
        searchView = findViewById(R.id.searchView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(ref != null){
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        list = new ArrayList<>();
                        for (DataSnapshot da: snapshot.getChildren()){
                            list.add(da.getValue(SearchListitem.class));
                        }
                        SearchAdapter searchAdapter = new SearchAdapter(list);
                        recyclerView.setAdapter(searchAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if(searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }

    private void search(String str){
        ArrayList<SearchListitem> myList = new ArrayList<>();
        for(SearchListitem object : list){
            if(object.getDescription().contains(str) || object.getDate().contains(str) || object.getSender().contains(str)){
                myList.add(object);
            }
        }
        SearchAdapter searchAdapter = new SearchAdapter(myList);
        recyclerView.setAdapter(searchAdapter);
    }
}
