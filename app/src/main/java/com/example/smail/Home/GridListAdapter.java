package com.example.smail.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smail.Model;
import com.example.smail.R;
import com.example.smail.SearchMailDetail;
import com.example.smail.mailDetail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class GridListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    //ArrayList<ListItem> items = new ArrayList<ListItem>();
    Context context;
    private FirebaseStorage storage;
    private ArrayList<Model> modelList;
    private List<Model> uidList = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;

    //public GridListAdapter(){}
    public  GridListAdapter(){
       modelList = new ArrayList<>();
        storage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                modelList.clear();
                System.out.println("데이터 불러옴");
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String key = snapshot.getKey();
                    Model model = ds.getValue(Model.class);
                    modelList.add(model);
                }
               notifyDataSetChanged();
                System.out.println("데이터 리로드");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        //LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        //View view = layoutInflater.inflate(R.layout.list_item,parent,false);

       // System.out.println("리스트불러오기");
        ImageView imageView = new ImageView(parent.getContext());
        int width = imageView.getResources().getDisplayMetrics().widthPixels / 3;
        imageView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        return new ViewHolder(imageView);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        

        context = holder.itemView.getContext();
        String date = modelList.get(position).date;
        String name = modelList.get(position).sender;
        String memo = modelList.get(position).description;
        String url = modelList.get(position).imageUrl;
        System.out.println(url);
        Glide.with(holder.itemView.getContext())
                .load(modelList.get(position).imageUrl)
                .placeholder(R.drawable.image_ex)
                .into((ImageView) holder.itemView);
        //holder.nameText.setText(modelList.get(position).getImageUrl());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("tbqlenqk" +holder.image_.getText().toString());
                Intent intent = new Intent(holder.itemView.getContext(), mailDetail.class);
                intent.putExtra("image",modelList.get(position).imageUrl);
               intent.putExtra("letter",modelList.get(position).description);
                holder.itemView.getContext().startActivity(intent);

                //ContextCompat.startActivity(holder.itemView.getContext(),intent,null);
            }
        });
    }


    @Override
    public int getItemCount() {
        System.out.println(modelList.size());
        return modelList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameText;
        TextView memoText;
        TextView dateText;
        public  ImageView imageView;

        public ViewHolder(@NonNull ImageView imageView){
            super(imageView);
            this.imageView = imageView;
           // photo = itemView.findViewById(R.id.photo);
            nameText = itemView.findViewById(R.id.name);
            memoText = itemView.findViewById(R.id.memo);
            dateText = itemView.findViewById(R.id.date);

        }

    /*    @Override
        public void onClick(View view) {


        }*/
    }
}
