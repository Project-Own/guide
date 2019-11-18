package com.example.guide.ui.viewpagerContainer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.guide.R;

public class ViewpagerContainerFragment extends Fragment {

    private ViewpagerContainerViewModel mViewModel;

    public static ViewpagerContainerFragment newInstance() {
        return new ViewpagerContainerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.viewpager_container_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ViewpagerContainerViewModel.class);
        // TODO: Use the ViewModel
    }

}
