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
        contacts.add(new Contact("chinese Embassy","01-4440286"));
        contacts.add(new Contact("Indian Embassy","01-4410900"));
        contacts.add(new Contact("Australian Embassy","01-4371678"));
        contacts.add(new Contact("Us Embassy","01-4234000"));
        contacts.add(new Contact("German Embassy","01-4217200"));
        contacts.add(new Contact("France Embassy","01-4412332"));
        contacts.add(new Contact("Korean Embassy","01-4270172"));
        contacts.add(new Contact("Saudi Embassy","01-4720891"));
        contacts.add(new Contact("Japan Embassy","01-4426680"));
        return view;

    }

}
