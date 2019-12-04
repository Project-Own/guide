package com.example.guide.ui.fullscreenImage;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.guide.R;

public class FullscreenImageFragment extends Fragment {

    private FullscreenImageViewModel mViewModel;

    public static FullscreenImageFragment newInstance() {
        return new FullscreenImageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fullscreen_image_fragment, container, false);
        String imageName = getArguments().getString("image");
        ImageView imageView = view.findViewById(R.id.fullscreenImageView);
        if (!imageName.equals("")) {
            Glide.with(this)
                    .load(getResources()
                            .getIdentifier(imageName, "drawable", getActivity().getPackageName()))
                    .fitCenter()
                    .override(2000,2000)
                    .into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            imageView.setImageDrawable(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });

        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FullscreenImageViewModel.class);
        // TODO: Use the ViewModel
    }

}
