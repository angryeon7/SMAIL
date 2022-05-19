package com.example.smail.tabFragment;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smail.R;


public class CameraTab extends Fragment {


    public CameraTab() {
//        CameraTab((PhotoActivity) getActivity());// Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.carmeratab, container, false);
    }

}
