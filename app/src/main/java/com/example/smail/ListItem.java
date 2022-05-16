package com.example.smail;

import android.media.Image;

public class ListItem {

    String name;
    String memo;
    String date;

    public ListItem(String name,String memo, String date){
        //this.image = image;
        this.name = name;
        this.memo = memo;
        this.date = date;
    }

    public String getName(){
        return name;
    }
    public String getMemo(){
        return memo;
    }
    public String getDate(){
        return date;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setMemo(String memo){
        this.memo = memo;
    }
    public void setDate(String date){
        this.date = date;
    }
}
