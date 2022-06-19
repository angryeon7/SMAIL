package com.example.smail.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smail.Model;
import com.example.smail.R;
import com.example.smail.mailDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class List_by_Person extends AppCompatActivity {

//    ListView listpersonView;
//    ArrayList<Model> modelList;
//    private List<Model> personList = new ArrayList<>();
//    private FirebaseDatabase firebaseDatabase;
//    ArrayAdapter adapter;

    List<Model> modelList = new ArrayList<>();
    private List<String> uidList = new ArrayList<>();
    //ArrayList<ListItem> items = new ArrayList<ListItem>();

    GridView gridview;

    private FirebaseDatabase firebaseDatabase;
    private RecyclerView recyclerView;

    public String person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_by_person);
        System.out.println("페이지이동");

        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.list_recyclerview);
        recyclerView.setAdapter(new GridFragmentRecyclerViewAdatper());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

    } // end of onCreate

    // Recycler View Adapter
    class GridFragmentRecyclerViewAdatper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<Model> contentDTOs;

        public GridFragmentRecyclerViewAdatper() {

            Intent intent = getIntent();
            String str = intent.getStringExtra("person");

            contentDTOs = new ArrayList<>();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Profile");
            myRef.orderByChild("sender").equalTo(str).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    contentDTOs.clear();
                    System.out.println("데이터 불러옴");
                    System.out.println(person);
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        String key = dataSnapshot.getKey();
                        Model model = ds.getValue(Model.class);
                        contentDTOs.add(model);
                    }
                    notifyDataSetChanged();
                    System.out.println("데이터 리로드");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            //현재 사이즈 뷰 화면 크기의 가로 크기의 1/3값을 가지고 오기
            ImageView imageView = new ImageView(parent.getContext());
            int width = imageView.getResources().getDisplayMetrics().widthPixels / 3;
            imageView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            return new CustomViewHolder(imageView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            Glide.with(holder.itemView.getContext())
                    .load(contentDTOs.get(position).imageUrl)
                    .placeholder(R.drawable.image_ex)
                    .into(((CustomViewHolder) holder).imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //System.out.println("tbqlenqk" +holder.image_.getText().toString());
                    Intent intent = new Intent(holder.itemView.getContext(), mailDetail.class);
                    intent.putExtra("image",contentDTOs.get(position).imageUrl);
                    intent.putExtra("letter",contentDTOs.get(position).description);
                    holder.itemView.getContext().startActivity(intent);

                    //ContextCompat.startActivity(holder.itemView.getContext(),intent,null);
                }
            });
        }

        @Override
        public int getItemCount() {
            System.out.println(contentDTOs.size());
            return contentDTOs.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;

            public CustomViewHolder(ImageView imageView) {
                super(imageView);
                this.imageView = imageView;
            }
        }
    }




} // end of class