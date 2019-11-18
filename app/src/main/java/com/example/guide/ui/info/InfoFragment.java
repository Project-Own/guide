package com.example.guide.ui.info;

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

import com.example.guide.Model.Info;
import com.example.guide.R;
import com.example.guide.databinding.InfoFragmentBinding;

public class InfoFragment extends Fragment {

    private InfoViewModel mViewModel;
    private InfoFragmentBinding binding;
    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.bind(inflater.inflate(R.layout.info_fragment, container, false));

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);
        // TODO: Use the ViewModel

        // Inflate view and obtain an instance of the binding class.
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);

        mViewModel.getInfoData().observe(this, new Observer<Info>() {
            @Override
            public void onChanged(Info info) {

            }
        });
    }

}
