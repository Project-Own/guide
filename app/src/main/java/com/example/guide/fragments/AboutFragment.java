package com.example.guide.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Users;
import com.example.guide.R;
import com.example.guide.adapters.AboutAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private RecyclerView recycleView;

    private List<Users> users;

    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_about_recycler, container, false);
        recycleView = v.findViewById(R.id.recycleView);
        users = new ArrayList<>();
        AboutAdapter adapter = new AboutAdapter(users, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setAdapter(adapter);
        users.add(new Users("Nirajan","KHCE074BCT024","prajapatinirajan0@gmail.com"));
        users.add(new Users("Nirjal","KHCE074BCT026","prajapatinirajan0@gmail.com"));
        users.add(new Users("Rohit","KHCE074BCT033","prajapatinirajan0@gmail.com"));
        users.add(new Users("Sahas","KHCE074BCT037","prajapatinirajan0@gmail.com"));


        return v;
    }

}
