package com.example.smail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    Fragment start,fragment0,fragment1,fragment2,fragment3;
    Button button1,button2,button3,button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start= new start();
        fragment0 = new Fragment0();
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        button1 = (Button) findViewById(R.id.main);
        button2 = (Button) findViewById(R.id.calender);
        button3 = (Button) findViewById(R.id.camera);
        button4 = (Button) findViewById(R.id.mypage);


        getSupportFragmentManager().beginTransaction().add(R.id.frame, fragment0).commit();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, calender.class);

                startActivity(intent);
            }

        });
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();

                Fragment selected = fragment0;
                if(position == 0){

                    selected = fragment0;

                }else if (position == 1){

                    selected = fragment1;

                }else if (position == 2){

                    selected = fragment2;

                }else if (position == 3){

                    selected = fragment3;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}