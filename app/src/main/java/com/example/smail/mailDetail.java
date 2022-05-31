package com.example.smail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class mailDetail extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maildetailview);

        TextView content = (TextView)findViewById(R.id.textview);
        ImageView iv = (ImageView)findViewById(R.id.imageview);


        // drawable에 있는 이미지를 지정합니다.
        iv.setImageResource(R.drawable.ex2);


        // 100 줄의 텍스트를 생성합니다.
        String text = "텍스트 화면";
        content.setText(text);
    } // end of onCreate
} // end of class
