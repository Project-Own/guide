package com.example.guide.fragments;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Contact;
import com.example.guide.R;
import com.example.guide.adapters.ContactAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    private RecyclerView recycleView;
    private List<Contact> contacts;
    private ContactAdapter adapter;
    private EditText editText;

    public ContactFragment() {
        // Required empty public constructor
    }

    private List<Contact> filteredListContact = new ArrayList<>();
    private List<Contact> filteredListContactEmergency = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_recycler, container, false);
        recycleView = view.findViewById(R.id.recycleView);
        editText = view.findViewById(R.id.contactEditText);

        TabLayout contactTabs = view.findViewById(R.id.contactTabs);
        contactTabs.addTab(contactTabs.newTab().setText("Embassy"));
        contactTabs.addTab(contactTabs.newTab().setText("Emergency"));


        contactTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                editText.setText("");
                switch (tab.getText().toString().toLowerCase()) {
                    case "embassy":
                        setupEmbassyContact();
                        adapter.filterList(contacts);
                        editText.setHint("Enter Country Name...");
                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                setupEmbassyContact();
                                filter(s.toString());
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                        break;
                    case "emergency":
                        setupEmergencyContact();
                        adapter.filterList(contacts);
                        editText.setHint("Enter Emergency Service...");
                        editText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                setupEmergencyContact();
                                filterEmergency(s.toString());
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        contacts = new ArrayList<>();
        setupEmbassyContact();
        adapter = new ContactAdapter(contacts, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setAdapter(adapter);

        return view;

    }

    private void setupEmbassyContact() {
        contacts.clear();
        contacts.add(new Contact("Embassy of Bangladesh","01-4390132","bdt",""));
        contacts.add(new Contact("Embassy of Brazil","01-4721462 /01-4721463","brl",""));
        contacts.add(new Contact("Embassy of China","01-4440286","cny",""));
        contacts.add(new Contact("Embassy of Egypt","01-5590166","egp",""));

        contacts.add(new Contact("Embassy of Finland","01-4417221/01-4416636","fin",""));
        contacts.add(new Contact("Embassy of France","01-4412332/01-4414734","fr",""));
        contacts.add(new Contact("Embassy of Germany","01-4412786/01-4416527","ger",""));
        contacts.add(new Contact("Embassy of India","01-4410900","inr",""));
        contacts.add(new Contact("Embassy of Israel","01-4411811/01-4413419","ils",""));
        contacts.add(new Contact("Embassy of Japan","01-4426680","jpy",""));
        contacts.add(new Contact("Embassy of Malaysia","01-5545680/01-5545681","myr",""));
        contacts.add(new Contact("Embassy of Myanmar","01- 5592774/01-5592841","mmk",""));
        contacts.add(new Contact("Embassy of North Korea","01- 5521855/01-5535871","dpr",""));
        contacts.add(new Contact("Embassy of Norway","01- 554 5307","nor",""));
        contacts.add(new Contact("Embassy of Pakistan","01-4374024/01-4374016","pkr",""));
        contacts.add(new Contact("Embassy of Qatar","01-5173161","qar",""));
        contacts.add(new Contact("Embassy of Russia","01-4412155/01-441 1063","rub",""));
        contacts.add(new Contact("Embassy of Saudi Arabia","01-4720891","sar",""));
        contacts.add(new Contact("Embassy of South Korea","01-4270172/01-4270417","skr",""));
        contacts.add(new Contact("Embassy of Srilanka","01-4721389","lkr",""));
        contacts.add(new Contact("Embassy of Switzerland","01-4217008/01-5525358","chf",""));
        contacts.add(new Contact("Embassy of Thailand","01-4371410/01-4371411","thb",""));
        contacts.add(new Contact("Embassy of the United Arab Emirates","01-4237100","aed",""));
        contacts.add(new Contact("Embassy of the United Kingdom","01-4371410/01-4371411","gbp",""));
        contacts.add(new Contact("Embassy of the United States of America","01-4234000","usd",""));




    }

    private void setupEmergencyContact() {

        contacts.clear();
        contacts.add(new Contact("Bhaktapur Hospital", "01-6610676", "bkthpt","Doodh Pati"));
        contacts.add(new Contact("Bir Hospital", "01-4221119/01-441 1063", "bir","Kathmandu"));
        contacts.add(new Contact("Police Control", "100", "police",""));
        contacts.add(new Contact("Tourist Police", "01-4226359/01-4226403", "police","Bhrikuti Mandap, Kathmandu"));
        contacts.add(new Contact("TU Teaching Hospital", "01-4412707/01-4412808/01-4412505", "teaching","Maharajgunj,kathmandu"));
        contacts.add(new Contact("Nepal Tourism Board", "01-4256909/014256229", "ntb","Bhrikuti Mandap, Kathmandu"));
        contacts.add(new Contact("Department of Immigration", "01-4223509/01-4222453", "immigration","Kathmandu"));
        contacts.add(new Contact("Tribhuvan International Airport(TIA)", "01-4472256/01-4472257", "tia","Kathmandu"));
        contacts.add(new Contact("Tilganga Eye Hospital", "01-4423684", "tilganga","Kathmandu"));
        contacts.add(new Contact("Public Health Care Center", "01-6610317/01-6613146", "polytechnic","Chyamhasingh,Bhaktapur"));
        contacts.add(new Contact("Emergency Ambulance", "102", "ambulance",""));
        contacts.add(new Contact("International Flight Service", "4470311/4472835(ext )", "aero","Kathmandu"));
        contacts.add(new Contact("Night Taxi Service", "01-4244485/01-4224375", "taxi","Dharmapath"));
        contacts.add(new Contact("Interpol Section", "01-4411210/01-4412602", "interpol","Naxal, Kathmandu"));




    }

    private void filter(String text) {
        filteredListContact.clear();

        for (Contact contact : contacts) {

            if (contact.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredListContact.add(contact);

            }

        }
        adapter.filterList(filteredListContact);

    }

    private void filterEmergency(String text) {
        filteredListContactEmergency.clear();

        for (Contact contact : contacts) {

            if (contact.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredListContactEmergency.add(contact);

            }

        }
        adapter.filterList(filteredListContactEmergency);

    }



}
