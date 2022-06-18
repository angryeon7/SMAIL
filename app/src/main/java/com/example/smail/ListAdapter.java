package com.example.smail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    ArrayList<Listitem_Person> items = new ArrayList<Listitem_Person>();
    Context context;

    public void addItem(Listitem_Person item){
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
        Listitem_Person listItem = items.get(position);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_person,parent,false);

        }


       // ImageView photo = convertView.findViewById(R.id.person_image);
        //TextView nameText = convertView.findViewById(R.id.person_name);



       // photo.setImageResource(listItem.getImage());
       // nameText.setText(listItem.getName());

        return convertView;


    }
}
