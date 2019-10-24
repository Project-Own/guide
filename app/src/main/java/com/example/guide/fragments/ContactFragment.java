package com.example.guide.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Contact;
import com.example.guide.R;
import com.example.guide.adapters.ContactAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    private RecyclerView recycleView;

    private List<Contact> contacts;


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_about_recycler, container, false);
        recycleView = view.findViewById(R.id.recycleView);
        contacts = new ArrayList<>();
        ContactAdapter adapter = new ContactAdapter(contacts, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setAdapter(adapter);
        contacts.add(new Contact("NIRAJAN PRJAPATI","KHCE074BCT024","prajapatinirajan0@gmail.com"));
        contacts.add(new Contact("NIRJAL PRAJAPATI","KHCE074BCT026","nirjalprajapati@gmail.com"));
        contacts.add(new Contact("ROHIT PRAJAPATI","KHCE074BCT033","prajapatinirajan0@gmail.com"));
        contacts.add(new Contact("SAHAS PRAJAPATI","KHCE074BCT037","sahas_1999@hotmail.com"));
        return view;

    }

}
