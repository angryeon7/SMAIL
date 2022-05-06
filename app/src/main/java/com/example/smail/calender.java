package com.example.smail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class calender extends AppCompatActivity {

    Button calenderButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calender);
        calenderButton = (Button) findViewById(R.id.back);

        calenderButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(calender.this,MainActivity.class);

                startActivity(intent);
            }
        });
    }
}
