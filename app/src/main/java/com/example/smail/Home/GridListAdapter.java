package com.example.smail.Home;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

//        holder.nameText.setText(modelList.get(position).sender);
//        holder.memoText.setText(modelList.get(position).description);
//        holder.dateText.setText(modelList.get(position).date);*/

        context = holder.itemView.getContext();
        String url = modelList.get(position).imageUrl;
        System.out.println(url);
        Glide.with(holder.itemView.getContext())
                .load(modelList.get(position).imageUrl)
                .placeholder(R.drawable.image_ex)
                .into((ImageView) holder.itemView);
        //holder.nameText.setText(modelList.get(position).getImageUrl());
    }

/*    public void onBindViewHolder(final ViewHolder holder, final int position)
    {

    }*/

/*    public void addItem(Model item){
        modelList.add(item);
    }*/

  /*  @Override
    public int getCount() {
        return modelList.size();
    }*/

   /* @Override
    public Object getItem(int position) {
        return modelList.get(position);
    }
*/
   /* @Override
    public long getItemId(int position) {
        return position;
    }*/

    @Override
    public int getItemCount() {
        System.out.println(modelList.size());
        return modelList.size();
    }

/*    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        Model model = modelList.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item,parent,false);

        }


        ImageView photo = convertView.findViewById(R.id.photo);
        TextView nameText = convertView.findViewById(R.id.name);
        TextView memoText = convertView.findViewById(R.id.memo);
        TextView dateText = convertView.findViewById(R.id.date);


        photo.setImageResource(Model.get(position),getDate());
        nameText.setText(Model.date());
        memoText.setText(Model.getMemo());
        dateText.setText(Model.date());


        return convertView;


    }*/

    public class ViewHolder extends RecyclerView.ViewHolder{
       // public TextView nameText;
       // public TextView memoText;
       // public TextView dateText;
        //public ImageView photo;
        public  ImageView imageView;

        public ViewHolder(@NonNull ImageView imageView){
            super(imageView);
            this.imageView = imageView;
            //photo = itemView.findViewById(R.id.photo);
            //nameText = itemView.findViewById(R.id.name);
            //memoText = itemView.findViewById(R.id.memo);
            //dateText = itemView.findViewById(R.id.date);
            imageView.setClickable(true);
            imageView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(
                                context.getApplicationContext(), // 현재화면의 제어권자
                                mailDetail.class);

                        context.startActivity(intent);
                    }

                }
            });

        }

    /*    @Override
        public void onClick(View view) {


        }*/
    }
}
