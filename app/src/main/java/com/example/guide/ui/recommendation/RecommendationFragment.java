package com.example.guide.ui.recommendation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codesgood.views.JustifiedTextView;
import com.example.guide.Model.Recommendation;
import com.example.guide.R;
import com.example.guide.adapters.RecomendationAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecommendationFragment extends Fragment {

    private RecommendationViewModel mViewModel;
    private RecyclerView hotelRecyclerView, placesRecyclerView;
    private FloatingActionButton placesLeft, placesRight, hotelLeft, hotelRight;
    private int hotelPosition = 0;
    private int placePosition = 0;
    private int visibleItemCount, totalItemCount, coun;
    private int firstVisibleItemPosition, lastVisibleItem;
    RecyclerView.LayoutManager mPlacesLayoutManager;
    RecyclerView.LayoutManager mLayoutManager;
    private String festival = "February/March: Holi\n" +
            "April: Bisket Jatra\n" +
            "October: Dashain\n" +
            "October / November: Tihar\n" +
            "August–September:Sa:paru\n" +
            "For this year’s event please check our calendar\n";
    private JustifiedTextView textView;

    public static RecommendationFragment newInstance() {
        return new RecommendationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recommendation_fragment, container, false);

        placesLeft = v.findViewById(R.id.placesleft);
        placesRight = v.findViewById(R.id.placesRight);
        hotelLeft = v.findViewById(R.id.hotelLeft);
        hotelRight = v.findViewById(R.id.hotelRight);

        textView = v.findViewById(R.id.text1);
        hotelRecyclerView = v.findViewById(R.id.recycleView);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        hotelRecyclerView.setLayoutManager(mLayoutManager);

        placesRecyclerView = v.findViewById(R.id.recyclerViewPlaces);
        mPlacesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        placesRecyclerView.setLayoutManager(mPlacesLayoutManager);



        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NavController navController = Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment);

        textView.setText(festival);

        mViewModel = ViewModelProviders.of(this).get(RecommendationViewModel.class);
        mViewModel.loadHotel().observe(this, new Observer<List<Recommendation>>() {
            @Override
            public void onChanged(List<Recommendation> recommendations) {
                RecomendationAdapter adapter = new RecomendationAdapter(recommendations, getContext(), hotelRecyclerView, navController);
                hotelRecyclerView.setAdapter(adapter);


                hotelRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        visibleItemCount = mLayoutManager.getChildCount();
                        totalItemCount = mLayoutManager.getItemCount();
                        firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
                        lastVisibleItem = firstVisibleItemPosition + visibleItemCount;
                        hotelPosition = firstVisibleItemPosition;
                        if (hotelPosition == 0 || firstVisibleItemPosition == 0) {
                            hotelLeft.hide();
                        } else if (hotelPosition == recommendations.size() || lastVisibleItem == recommendations.size()) {
                            hotelRight.hide();
                        } else {
                            hotelLeft.show();
                            hotelRight.show();
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        visibleItemCount = mLayoutManager.getChildCount();
                        totalItemCount = mLayoutManager.getItemCount();
                        firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
                        lastVisibleItem = firstVisibleItemPosition + visibleItemCount;
                        hotelPosition = firstVisibleItemPosition;
                        if (hotelPosition == 0 || firstVisibleItemPosition == 0) {
                            hotelLeft.hide();
                        } else if (hotelPosition == recommendations.size() || lastVisibleItem == recommendations.size()) {
                            hotelRight.hide();
                        } else {
                            hotelLeft.show();
                            hotelRight.show();
                        }
                    }
                });
                hotelLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (hotelPosition > 0) {
                            hotelPosition--;
                            hotelRecyclerView.scrollToPosition(hotelPosition);
                        }
                    }
                });

                hotelRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (hotelPosition < recommendations.size()-1) {
                            hotelPosition++;
                            hotelRecyclerView.scrollToPosition(hotelPosition);
                        }
                    }
                });



            }
        });


        mViewModel.loadRecommendedPlaces().observe(this, new Observer<List<Recommendation>>() {
            @Override
            public void onChanged(List<Recommendation> recommendations) {
                RecomendationAdapter adapter = new RecomendationAdapter(recommendations, getContext(), placesRecyclerView, navController);
                placesRecyclerView.setAdapter(adapter);
                placesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        visibleItemCount = mPlacesLayoutManager.getChildCount();
                        totalItemCount = mPlacesLayoutManager.getItemCount();
                        firstVisibleItemPosition = ((LinearLayoutManager) mPlacesLayoutManager).findFirstVisibleItemPosition();
                        lastVisibleItem = firstVisibleItemPosition + visibleItemCount;
                        placePosition = firstVisibleItemPosition;
                        if (placePosition == 0 || firstVisibleItemPosition == 0) {
                            placesLeft.hide();
                        } else if (placePosition == recommendations.size() || lastVisibleItem == recommendations.size()) {
                            placesRight.hide();
                        } else {
                            placesRight.show();
                            placesLeft.show();
                        }
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        visibleItemCount = mPlacesLayoutManager.getChildCount();
                        totalItemCount = mPlacesLayoutManager.getItemCount();
                        firstVisibleItemPosition = ((LinearLayoutManager) mPlacesLayoutManager).findFirstVisibleItemPosition();
                        lastVisibleItem = firstVisibleItemPosition + visibleItemCount;
                        placePosition = firstVisibleItemPosition;
                        if (placePosition == 0 || firstVisibleItemPosition ==0 ) {
                            placesLeft.hide();
                        } else if (placePosition == recommendations.size() || lastVisibleItem==recommendations.size()) {
                            placesRight.hide();
                        } else {
                            placesRight.show();
                            placesLeft.show();
                        }
                    }
                });

                placesLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (placePosition > 0) {
                            placePosition--;
                            placesRecyclerView.scrollToPosition(placePosition);
                        }
                    }
                });

                placesRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (placePosition < recommendations.size()-1) {
                            placePosition++;
                            placesRecyclerView.scrollToPosition(placePosition);

                        }
                    }
                });


            }
        });
    }

}
