package com.example.guide.ui.gallery;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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
    private AlertDialog alertDialog;
    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }
    private int spanCount = 2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.galerry_fragment, container, false);

        pager = v.findViewById(R.id.horizontal_cycle);


        recyclerView = v.findViewById(R.id.places_recyclerView);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(spanCount, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
// Changes the height and width to the specified *pixels*
        params.height = Resources.getSystem().getDisplayMetrics().heightPixels;
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        recyclerView.setLayoutParams(params);
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


                Bundle bundle = new Bundle();
                bundle.putString("image", tagName);
                Navigation.findNavController(getActivity(),R.id.my_nav_host_fragment).navigate(R.id.action_nav_gallery_to_fullscreenImageFragment, bundle);
//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
//                View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
//                PhotoView photoView = mView.findViewById(R.id.imageView);
//                photoView.setImageResource(R.drawable.bodygaurd);
//                mBuilder.setView(mView);
//               alertDialog = mBuilder.create();
//                alertDialog.show();

            }
        };



        mViewModel.getPlacesList().observe(this, new Observer<List<Places>>() {
            @Override
            public void onChanged(List<Places> places) {
                adapter = new GalleryAdapter(places, recyclerView, getContext(),  galleryTagsListInterface);
                recyclerView.setAdapter(adapter);
                InfiniteScrollAdapter infiAdapter = new InfiniteScrollAdapter(places, getContext(), galleryTagsListInterface);
                pager.setAdapter(infiAdapter);

            }
        });



    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            spanCount = 3;
        }else{
            spanCount = 2;
        }
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(spanCount, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
