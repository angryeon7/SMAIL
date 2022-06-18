package com.example.smail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.net.URL;

public class SearchMailDetail extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchmaildetailview);

        imageView = findViewById(R.id.search_imageview);
        textView = findViewById(R.id.search_textview);

        Intent intent = getIntent();
        String str = intent.getStringExtra("image_");
//        if(str.contains("alt=")){
//            str=str.split("alt=").;
//
//        }
        Glide.with(this)
                .load(str)
                .into(imageView);
       // imageView.setImageURI(Uri.parse(str));
        System.out.println("유휴"+str);
        textView.setText(intent.getStringExtra("letter"));

    }
}
