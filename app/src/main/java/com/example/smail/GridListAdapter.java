package com.example.smail;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridListAdapter extends BaseAdapter {
    ArrayList<ListItem> items = new ArrayList<ListItem>();
    Context context;

    public void addItem(ListItem item){
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        context = parent.getContext();
        ListItem listItem = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item,parent,false);

        }


        ImageView photo = convertView.findViewById(R.id.photo);
        TextView nameText = convertView.findViewById(R.id.name);
        TextView memoText = convertView.findViewById(R.id.memo);
        TextView dateText = convertView.findViewById(R.id.date);


        photo.setImageResource(listItem.getImage());
        nameText.setText(listItem.getName());
        memoText.setText(listItem.getMemo());
        dateText.setText(listItem.getDate());


        return convertView;


    }
}
