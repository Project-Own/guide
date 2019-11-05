package com.example.guide.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Recomendation;
import com.example.guide.R;
import com.example.guide.adapters.RecomendationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecomendationFragment extends Fragment {
    private RecyclerView recycleView, placesRecyclerView;

    private List<Recomendation> recomendations, recomendationsPlaces;

    private Button placesLeft, placesRight, hotelLeft, hotelRight;
    private int hotelPosition = 0;
    private int placePosition = 0;
    private int visibleItemCount, totalItemCount, coun;
    private int firstVisibleItemPosition, lastVisibleItem;

    public RecomendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recommendation_recycler, container, false);


        TextView text1= v.findViewById(R.id.text1);

        text1.setText("Time needed:\n" +
                "\n" +
                "Lite version (this page): 2 hours\n" +
                "Heritage enthusiast: overnight stays or at least one full day" +
                "\n" +
                "This walk can take between 2 hours or as mentioned above you could easily spread it out into an overnight stay depending on your likes.\n" +
                "\n" +
                "Distance:\n" +
                "\n" +
                "Lite version: (this page) – 2.6 km\n" +
                "Heritage enthusiast: 4.22 km" );

        placesLeft = v.findViewById(R.id.placesleft);
        placesRight = v.findViewById(R.id.placesRight);
        hotelLeft = v.findViewById(R.id.hotelLeft);
        hotelRight = v.findViewById(R.id.hotelRight);

        recycleView = v.findViewById(R.id.recycleView);
        placesRecyclerView = v.findViewById(R.id.recyclerViewPlaces);

        recomendations = new ArrayList<>();
        recomendationsPlaces = new ArrayList<>();

        RecomendationAdapter adapter = new RecomendationAdapter(recomendations, getContext(), recycleView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setAdapter(adapter);
        recomendations.add(new Recomendation("Peacock Guest House", "4.5/5", "01-6611829", "Datattreya Square", "NPR 4031 per night", "On the main square stands this well run guesthouse with clean well decorated rooms in Newari design.", "peacock"));
        recomendations.add(new Recomendation("Milla Guest House", "5/5", " 9851027012/9851024137", "Datattreya Square", "NPR 7932 per night", "Highly rated hotel and have bery good reputation locally", "milla"));
        recomendations.add(new Recomendation("Thagu Chenn", "5/5", "01-6612043/9851152541", "Bhaktapur Durbar Square", "NPR 6643 per night", "Located just 50m away from UNESCO enlisted Bhaktapur Durbar Square, THAGU CHHEN, is a heritage boutique Bed and Breakfast " +
                "initiated as a step towards preserving culture with a sustainable and green approach", "thagu"));
        recomendations.add(new Recomendation("Shiva Guest House 1 & 2", "4.5/5", "01-6613912/01-6610740", "Bhaktapur Durbar Square", "NPR 1158 per  night", "Eco-Friendly Heritage Guest House. SHIVA GUEST HOUSE-pleasantly located in the heart of the Durbar Square is your address in Bhaktapur. Standing next to the Yaksheshwor" +
                " Temple the city's oldest edifice dating back to 1472 AD, " +
                "the guest house is where you can start your exploration trip from.", "shiva"));


        RecomendationAdapter placesAdapter = new RecomendationAdapter(recomendationsPlaces, getContext(), placesRecyclerView);
        RecyclerView.LayoutManager mPlacesLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        placesRecyclerView.setLayoutManager(mPlacesLayoutManager);
        placesRecyclerView.setAdapter(placesAdapter);

        placesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                visibleItemCount = mPlacesLayoutManager.getChildCount();
                totalItemCount = mPlacesLayoutManager.getItemCount();
                firstVisibleItemPosition = ((LinearLayoutManager) mPlacesLayoutManager).findFirstVisibleItemPosition();
                lastVisibleItem = firstVisibleItemPosition + visibleItemCount;
                placePosition = lastVisibleItem;
                if (placePosition <= 1) {
                    placesLeft.setVisibility(View.INVISIBLE);
                } else if (placePosition == recomendationsPlaces.size()) {
                    placesRight.setVisibility(View.INVISIBLE);
                } else {
                    placesRight.setVisibility(View.VISIBLE);
                    placesLeft.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = mPlacesLayoutManager.getChildCount();
                totalItemCount = mPlacesLayoutManager.getItemCount();
                firstVisibleItemPosition = ((LinearLayoutManager) mPlacesLayoutManager).findFirstVisibleItemPosition();
                lastVisibleItem = firstVisibleItemPosition + visibleItemCount;

                if (placePosition <= 1) {
                    placesLeft.setVisibility(View.INVISIBLE);
                } else if (placePosition == recomendationsPlaces.size()) {
                    placesRight.setVisibility(View.INVISIBLE);
                } else {
                    placesRight.setVisibility(View.VISIBLE);
                    placesLeft.setVisibility(View.VISIBLE);
                }
            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
                lastVisibleItem = firstVisibleItemPosition + visibleItemCount;
                hotelPosition = lastVisibleItem;
                if (hotelPosition <= 1) {
                    hotelLeft.setVisibility(View.INVISIBLE);
                } else if (hotelPosition == recomendations.size()) {
                    hotelRight.setVisibility(View.INVISIBLE);
                } else {
                    hotelLeft.setVisibility(View.VISIBLE);
                    hotelRight.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
                lastVisibleItem = firstVisibleItemPosition + visibleItemCount;
                hotelPosition = lastVisibleItem;
                if (hotelPosition <= 1) {
                    hotelLeft.setVisibility(View.INVISIBLE);
                } else if (hotelPosition == recomendations.size()) {
                    hotelRight.setVisibility(View.INVISIBLE);
                } else {
                    hotelLeft.setVisibility(View.VISIBLE);
                    hotelRight.setVisibility(View.VISIBLE);
                }
            }
        });
        recomendationsPlaces.add(new Recomendation("Bhaktapur Durbar Square", "", "", "", "", "one of the largest ancient squares in Nepal filled to the " +
                "\n brim with temples, cultural carvings and buildings ", "durbarsquare"));
        recomendationsPlaces.add(new Recomendation("Basantapur Chowk", "", "", "", "", "two famous sculptures that cost a man his hands \n" +
                "\n", "basantapur"));
        recomendationsPlaces.add(new Recomendation("The Palace of Fifty-five Windows", "", "", "", "", "a masterpiece of wood carving The Golden Gate: one of " +
                "\n the most intricate and well decorated gates in the world", "durbarsquare"));
        recomendationsPlaces.add(new Recomendation("Pashupatinath Temple", "", "", "", "", "impressive wooden temple", "pasupati"));
        recomendationsPlaces.add(new Recomendation("Vatsala Durga Temple & Taleju Bell", "", "", "", "", "Stone temple with bell built by King Jagat Prakash Malla " +
                "\n (currently - 2017/18- under reconstruction however you can give donations directly to its rebuilding) ", "vatsala"));
        recomendationsPlaces.add(new Recomendation("Siddhi Laxmi Temple", "", "", "", "", "A stone temple built in the 17th-century (currently - 2017/18- under reconstuction)\n" +
                "\n", "siddhilaxmi"));
        recomendationsPlaces.add(new Recomendation("Pottery Square", "", "", "", "", "Watch Bhaktapur's famous pottery as it's made in this idyllic traditional square filled with potters and their wares. ", "potters"));
        recomendationsPlaces.add(new Recomendation("Royal Curd", "", "", "", "", "Take a time out and eat some of Bhaktapur's famous curd", "kingcurd"));
        recomendationsPlaces.add(new Recomendation("Side streets of Bhaktapur", "", "", "", "", "Spend hours wandering off down the side " +
                "\n streets of the city enjoying local handicraft stores, " +
                "\n artisans, local cafes and countless ancient buildings ", "alley"));
        recomendationsPlaces.add(new Recomendation("Taumadhi Square", "", "", "", "", "a huge square with the tallest temple in Nepal", "nightpolo"));

        recomendationsPlaces.add(new Recomendation("Dattatreya Square", "", "", "", "", "the oldest royal square in Nepal filled with things to see and do.", "datattreya"));

        recomendationsPlaces.add(new Recomendation("The Peacock Window", "", "", "", "", "One of Nepal's most precious woodcarvings and national art treasures - there are several craft stores along the same street worth visiting", "peacockw"));

        placesAdapter.notifyDataSetChanged();

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
                if (placePosition < recomendationsPlaces.size()) {
                    placePosition++;
                    placesRecyclerView.scrollToPosition(placePosition);

                }
            }
        });

        hotelLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hotelPosition > 0) {
                    hotelPosition--;
                    recycleView.scrollToPosition(hotelPosition);
                }
            }
        });

        hotelRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hotelPosition < recomendations.size()) {
                    recycleView.scrollToPosition(hotelPosition);
                }
            }
        });


        return v;
    }

}
