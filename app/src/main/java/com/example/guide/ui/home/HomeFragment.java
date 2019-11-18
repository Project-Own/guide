package com.example.guide.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Model.Home;
import com.example.guide.R;
import com.example.guide.adapters.HomeAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {



    public HomeFragment( ) {

                // Required empty public constructor
    }

    private RecyclerView recyclerView;

    HomeAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.homeRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

       return  v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final NavController navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);

        HomeViewModel model = ViewModelProviders.of(this).get(HomeViewModel.class);

        model.getHomeList().observe(this, new Observer<List<Home>>() {
            @Override
            public void onChanged(List<Home> homes) {


                adapter = new HomeAdapter(homes, getContext(), navController);
                recyclerView.setAdapter(adapter);
            }
        });

    }
}
