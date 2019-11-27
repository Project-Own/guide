package com.example.guide.ui.fullscreenGallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.guide.Model.Places;
import com.example.guide.R;
import com.example.guide.adapters.InfiniteScrollAdapter;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.List;

public class FullscreenGalleryFragment extends Fragment {

    private FullscreenGalleryViewModel mViewModel;
    private HorizontalInfiniteCycleViewPager pager;
    private TextView textView;
    public static FullscreenGalleryFragment newInstance() {
        return new FullscreenGalleryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fullscreen_gallery_fragment, container, false);
        pager = view.findViewById(R.id.horizontal_cycle);
        //textView = view.findViewById(R.id.description);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int position = getArguments().getInt("position");

        mViewModel = ViewModelProviders.of(this).get(FullscreenGalleryViewModel.class);
        mViewModel.getPlacesList().observe(this, new Observer<List<Places>>() {
            @Override
            public void onChanged(List<Places> places) {
                        InfiniteScrollAdapter infiAdapter = new InfiniteScrollAdapter(places, getContext());
                        pager.setAdapter(infiAdapter);
                        infiAdapter.notifyDataSetChanged();
                        pager.setCurrentItem(position, false);

            }
        });
    }

}
