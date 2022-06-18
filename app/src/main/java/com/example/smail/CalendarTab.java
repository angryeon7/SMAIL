package com.example.smail;

import static android.content.Context.MODE_NO_LOCALIZED_COLLATORS;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.io.FileOutputStream;

public class CalendarTab extends DialogFragment {

    private Fragment fragment;
    Button diary_saveBtn;
    EditText diary_content;
    DatePicker datePicker;


    public CalendarTab(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_dialog,container,false);
        fragment = getActivity().getSupportFragmentManager().findFragmentByTag("tag");

        diary_content = view.findViewById(R.id.diary_content);
        datePicker = view.findViewById(R.id.diary_datepick);
        diary_saveBtn = view.findViewById(R.id.diary_save);

        diary_saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //saveDiary();
                String str = diary_content.getText().toString();
            }
        });
        return view;

    }


}
