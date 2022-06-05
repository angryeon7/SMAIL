package com.example.smail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {


    ArrayList<SearchListitem> list;
    public SearchAdapter(ArrayList<SearchListitem> list){
        this.list=list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_letter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.sender__.setText(list.get(position).getSender());
        holder.description__.setText(list.get(position).getDescription());
        holder.date__.setText(list.get(position).getDate());
        //holder.imageUrl__.setText(list.get(position).getImageUrl());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date__,sender__,description__;
        public MyViewHolder(View itemView){
            super(itemView);
            date__=itemView.findViewById(R.id.date_);
            sender__ = itemView.findViewById(R.id.name_);
            description__ = itemView.findViewById(R.id.memo_);
           // imageUrl__ = itemView.findViewById(R.id.photo_);
        }
    }
}
