package com.example.smail;

public class Listitem_Person {
    int imageId_Person;
    String name_person;

    public Listitem_Person(int image,String name){
        this.imageId_Person = image;
        this.name_person = name;

    }
    public int getImage(){
        return imageId_Person;
    }

    public void setImage(int image){
        this.imageId_Person = image;
    }

    public String getName(){
        return name_person;
    }


    public void setName(String name){
        this.name_person = name;
    }

}
