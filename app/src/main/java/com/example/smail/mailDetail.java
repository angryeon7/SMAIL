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
        iv.setImageResource(R.drawable.image_ex);


        // 100 줄의 텍스트를 생성합니다.
        String text = "장 선생님께\n안녕하세요\n보내주신 선물 정말 감사히 받았습니다.\n이 어려운 상황에서 관심과 친절을 보여주셔서\n저희 가족 모두는 감동했습니다\n."
                + "선생님의 가족들도 이 시기를\n건강하고 안전히 보내시기를 간절히 바랍니다.\n캘리포니아 새크라멘토";
        content.setText(text);
    } // end of onCreate
} // end of class
