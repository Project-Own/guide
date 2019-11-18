package com.example.guide.ui.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Model.Contact;
import com.example.guide.R;
import com.example.guide.adapters.ContactAdapter;
import com.example.guide.databinding.ContactFragmentBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {

    private ContactViewModel mViewModel;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private List<Contact> contacts=new ArrayList<>();
    private TabLayout contactTabs;
    private ContactFragmentBinding mContactFragmentBinding;
    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      mContactFragmentBinding = DataBindingUtil.bind(inflater.inflate(R.layout.contact_fragment, container, false)) ;
        View v = mContactFragmentBinding.getRoot();

        recyclerView = v.findViewById(R.id.recycleView);

        contactTabs = v.findViewById(R.id.contactTabs);

       RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
//

        contactTabs.addTab(contactTabs.newTab().setText("Embassy"));
        contactTabs.addTab(contactTabs.newTab().setText("Emergency"));

        contactTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getText().toString().toLowerCase()) {
                    case "embassy":
                        mViewModel.setTabSelected("embassy");
                        break;
                    case "emergency":
                        mViewModel.setTabSelected("emergency");
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
        contactTabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryLight));

        adapter = new ContactAdapter(contacts, getContext());

        recyclerView.setAdapter(adapter);

//
        mContactFragmentBinding.setViewModel(mViewModel);
        mContactFragmentBinding.setLifecycleOwner(this);
//
        mViewModel.loadContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contactList) {
              //  Toast.makeText(getActivity(), contactList.toString(), Toast.LENGTH_SHORT).show();
                adapter.filterList(contactList);
            }
        });
    }

}
