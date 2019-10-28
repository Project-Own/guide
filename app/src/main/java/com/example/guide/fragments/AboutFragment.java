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
        users.add(new Users("NIRAJAN PRJAPATI","KHCE 074 BCT 024","prajapatinirajan0@gmail.com","ruko"));
        users.add(new Users("NIRJAL PRAJAPATI","KHCE 074 BCT 026","nirjalprajapati@gmail.com","nirjal"));
        users.add(new Users("ROHIT PRAJAPATI","KHCE074 BCT 033","roht.praz@gmail.com","rohit"));
        users.add(new Users("SAHAS PRAJAPATI","KHCE074 BCT 037","sahas_1999@hotmail.com","sahas"));

        AboutAdapter adapter = new AboutAdapter(users, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setAdapter(adapter);


        return v;
    }

}
