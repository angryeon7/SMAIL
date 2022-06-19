package com.example.smail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class mailDetail extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maildetailview);

        TextView content = (TextView)findViewById(R.id.textview);
        ImageView iv = (ImageView)findViewById(R.id.imageview);

        Intent intent = getIntent();
        String str = intent.getStringExtra("image");
//        if(str.contains("alt=")){
//            str=str.split("alt=").;
//
//        }
        Glide.with(this)
                .load(str)
                .into(iv);
        // imageView.setImageURI(Uri.parse(str));
        content.setText(intent.getStringExtra("letter"));

    } // end of onCreate
} // end of class
