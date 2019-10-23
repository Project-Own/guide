package com.example.guide.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        users.add(new Users("Card1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vitae fringilla sapien. Morbi at nisl enim. " +
                "Vestibulum eu posuere arcu. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. " +
                "Pellentesque congue augue eu euismod convallis. Nam quis lorem at massa ultrices faucibus in vel sapien. Ut id ornare metus, " +
                "sit amet convallis nisl. Aliquam maximus fringilla urna et volutpat. Proin fringilla tempor lacus id cursus. " +
                "Quisque finibus laoreet fermentum. Nulla mollis pellentesque imperdiet. " +
                "Phasellus vel dolor ac nisi mollis ultricies. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nunc mi erat, malesuada eu luctus ac, pharetra ut lorem. ", "http://www.link.com", 1));
        users.add(new Users("Card2", "In imperdiet nisi nec risus dignissim pellentesque. Etiam eget tristique orci. Nulla ultrices lacinia ornare. Donec dapibus facilisis lectus non imperdiet. Ut euismod tincidunt commodo. Curabitur aliquet tortor tortor, in placerat mi elementum a. Aliquam eu felis est. Suspendisse non facilisis nisl. Proin sagittis sem orci, id dapibus diam vehicula sit amet. Praesent vel nisl vel turpis fringilla volutpat. Morbi non ante vitae sem malesuada molestie. Quisque nec ipsum sed augue convallis semper. ", "http://www.link2.com", 5));
        users.add(new Users("Card3", "Fusce condimentum rhoncus massa ut volutpat. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Etiam mollis nisl turpis, eu convallis dui ultrices suscipit. Sed porta sodales hendrerit. Maecenas finibus massa eu orci ultricies pellentesque. Mauris bibendum, mauris aliquet gravida mattis, dolor sem imperdiet turpis, non suscipit arcu tellus at nisi. Nam ac orci sodales, vulputate elit ut, viverra ipsum. Nulla posuere blandit dolor faucibus mollis. Ut viverra tincidunt dapibus. Integer sollicitudin ante id dictum venenatis." +
                " Aliquam eros nibh, aliquet fermentum ex in, consectetur venenatis nunc. ", "http://www.link3.com", 2));
        users.add(new Users("Card4", "Integer aliquet erat a vestibulum luctus. Nam sed viverra diam. Vestibulum eros dolor, tempus at ex ac, feugiat euismod justo. Donec ultrices consectetur leo, eget semper massa dignissim sit amet. Vestibulum mauris massa, aliquet eget metus nec, porta pretium mauris. Maecenas facilisis nec dui eu efficitur. Phasellus laoreet in enim nec luctus. Donec luctus est at ipsum sagittis, a finibus ante commodo. Nullam ultrices ut erat vel maximus. Donec dui tortor, fermentum id ornare at, malesuada eget nunc. Nulla pharetra mi elit, a pharetra justo hendrerit quis. Praesent blandit sodales purus eget fermentum. ", "http://www.link4.com", 4));
        users.add(new Users("Card5", "Vestibulum sed dui risus. Donec et ultricies erat, id iaculis mauris. Maecenas vel magna non nisi iaculis laoreet vel nec ex. Mauris tristique arcu vitae nulla varius congue. Suspendisse varius mi vel feugiat tincidunt. Ut ultricies pretium accumsan. Integer pretium enim vitae lorem venenatis, vitae ultrices nisi rhoncus. In eget cursus mauris. In imperdiet urna nibh, non mattis justo eleifend ut. Maecenas fermentum non sem vehicula tempus. In consequat aliquet quam id gravida. Nullam condimentum justo metus, eget tempus justo sagittis fringilla. Donec a ullamcorper libero.", "http://www.link5.com", 3));

        AboutAdapter adapter = new AboutAdapter(users, getActivity().getSupportFragmentManager());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setAdapter(adapter);
        return v;
    }

}
