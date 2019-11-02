package com.example.guide.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


    public RecomendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recommendation_recycler, container, false);

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

        recomendationsPlaces.add(new Recomendation("Bhaktapur Durbar Square", "", "", "", "", "one of the largest ancient squares in Nepal filled to the " +
                "\n brim with temples, cultural carvings and buildings ", "durbarsquare"));
        recomendationsPlaces.add(new Recomendation("Basantapur Chowk", "", "", "", "", "two famous sculptures that cost a man his hands \n" +
                "\n", "usd "));
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
        recomendationsPlaces.add(new Recomendation("Nyatapola Temple in Taumadhi Square", "", "", "", "", "with a five-tier roof and an ornate exterior stone staircase the Nyatapola Temple is one of the tallest in all Nepal.", "bara"));
        recomendationsPlaces.add(new Recomendation("Dattatreya Square", "", "", "", "", "the oldest royal square in Nepal filled with things to see and do.", "dattatreya"));
        recomendationsPlaces.add(new Recomendation("Dattatreya Temple in Dattatreya Square", "", "", "", "", "Smaller but much revered three-tier temple. \n" +
                "\n", "dattatreya"));
        recomendationsPlaces.add(new Recomendation("The Peacock Window", "", "", "", "", "One of Nepal's most precious woodcarvings and national art treasures - there are several craft stores along the same street worth visiting", "peacockw"));

        placesAdapter.notifyDataSetChanged();
        return v;
    }

}
