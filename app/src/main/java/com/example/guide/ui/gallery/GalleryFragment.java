package com.example.guide.ui.gallery;

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

import com.example.guide.Model.Places;
import com.example.guide.R;
import com.example.guide.adapters.GalleryAdapter;
import com.example.guide.adapters.InfiniteScrollAdapter;
import com.example.guide.interfaces.GalleryTagsListInterface;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel mViewModel;
    private RecyclerView recyclerView;
    private GalleryAdapter adapter;
    private GalleryTagsListInterface galleryTagsListInterface;
    private HorizontalInfiniteCycleViewPager pager;
    private int listPosition;

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.galerry_fragment, container, false);

        pager = v.findViewById(R.id.horizontal_cycle);

        recyclerView = v.findViewById(R.id.places_recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        // TODO: Use the ViewModel

        galleryTagsListInterface = new GalleryTagsListInterface() {
            @Override
            public void onTagClicked(String tagName, String description, int position) {

                listPosition = position;
                pager.setCurrentItem(position);
            }
        };

        mViewModel.getPlacesList().observe(this, new Observer<List<Places>>() {
            @Override
            public void onChanged(List<Places> places) {
                adapter = new GalleryAdapter(places, recyclerView, getContext(),  galleryTagsListInterface);
                recyclerView.setAdapter(adapter);
                InfiniteScrollAdapter infiAdapter = new InfiniteScrollAdapter(places, getContext());
                pager.setAdapter(infiAdapter);

            }
        });

    }

}
