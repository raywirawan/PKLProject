package com.myproject.pkl.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.myproject.pkl.R;
import com.myproject.pkl.activity.PhotoActivity;

public class HomeFragment extends Fragment {

    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (v == null){
            v = inflater.inflate(R.layout.fragment_home, container, false);
            ExtendedFloatingActionButton fab = v.findViewById(R.id.fab_add_photo);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), PhotoActivity.class);
                    startActivity(intent);
                }
            });
        }
        return v;
    }
}