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

        contacts.add(new Contact("Embassy of Bangladesh","01-4390132","bdt"));
        contacts.add(new Contact("Embassy of Brazil","01-4721462 /01-4721463","brl"));
        contacts.add(new Contact("Embassy of China","01-4440286","cny"));
        contacts.add(new Contact("Embassy of Egypt","01-5590166","egp"));

        contacts.add(new Contact("Embassy of Finland","01-4417221/01-4416636","fin"));
        contacts.add(new Contact("Embassy of France","01-4412332/01-4414734","fr"));
        contacts.add(new Contact("Embassy of Germany","01-4412786/01-4416527","ger"));
        contacts.add(new Contact("Embassy of India","01-4410900","inr"));
        contacts.add(new Contact("Embassy of Israel","01-4411811/01-4413419","ils"));
        contacts.add(new Contact("Embassy of Japan","01-4426680","jpy"));
        contacts.add(new Contact("Embassy of Malaysia","01-5545680/01-5545681","myr"));
        contacts.add(new Contact("Embassy of Myanmar","01- 5592774/01-5592841","mmk"));
        contacts.add(new Contact("Embassy of North Korea","01- 5521855/01-5535871","dpr"));
        contacts.add(new Contact("Embassy of Norway","01- 554 5307","nor"));
        contacts.add(new Contact("Embassy of Pakistan","01-4374024/01-4374016","pkr"));
        contacts.add(new Contact("Embassy of Qatar","01-5173161","qar"));
        contacts.add(new Contact("Embassy of Russia","01-4412155/01-441 1063","rub"));
        contacts.add(new Contact("Embassy of Saudi Arabia","01-4720891","sar"));
        contacts.add(new Contact("Embassy of South Korea","01-4270172/01-4270417","skr"));
        contacts.add(new Contact("Embassy of Srilanka","01-4721389","lkr"));
        contacts.add(new Contact("Embassy of Switzerland","01-4217008/01-5525358","chf"));
        contacts.add(new Contact("Embassy of Thailand","01-4371410/01-4371411","thb"));
        contacts.add(new Contact("Embassy of the United Arab Emirates","01-4237100","aed"));
        contacts.add(new Contact("Embassy of the United Kingdom","01-4371410/01-4371411","gbp"));
        contacts.add(new Contact("Embassy of the United States of America","01-4234000","usd"));





        return view;

    }

}
