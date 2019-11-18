package com.example.guide.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Model.Users;
import com.example.guide.R;
import com.example.guide.adapters.AboutAdapter;

import java.util.List;

public class AboutFragment extends Fragment {

    private AboutViewModel mViewModel;
    private RecyclerView recycleView;
    RecyclerView.LayoutManager mLayoutManager;
    private List<Users> users;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.about_fragment, container, false);
        recycleView = v.findViewById(R.id.aboutRecyclerView);

       mLayoutManager = new LinearLayoutManager(getContext());


        return v;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AboutViewModel.class);

        mViewModel.loadUser().observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {
                AboutAdapter adapter = new AboutAdapter(users, getContext());
                recycleView.setAdapter(adapter);
                recycleView.setLayoutManager(mLayoutManager);

            }
        });
    }

}
