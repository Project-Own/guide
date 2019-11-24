package com.example.guide.ui.gallery;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.guide.Model.Places;

import java.util.ArrayList;
import java.util.List;

public class GalleryViewModel extends AndroidViewModel {

    private MutableLiveData<List<Places>> places;

    public GalleryViewModel(@NonNull Application application) {
        super(application);

    }

    public MutableLiveData<List<Places>> getPlacesList(){
        if(places == null){
            places = new MutableLiveData<List<Places>>();

            loadPlaces();
        }

        return places;
    }

    // TODO: Implement the ViewModel

    public void loadPlaces(){
        List<Places> placesList = new ArrayList<>();
        placesList.add(new Places("", "Two boys in ritual attire", "fuches"));
        placesList.add(new Places("", "Puppet of the Kumari(Living Goddess)", "katamari"));
        placesList.add(new Places("", "Aarati", "batti"));
        placesList.add(new Places("", "Bodygaurd at service of Dattatraya", "bodygaurd"));
        placesList.add(new Places("", "Girl in traditional newari dress", "rubi"));
        placesList.add(new Places("", "Statue of Bhupatindra Malla", "bhupa"));
        placesList.add(new Places("", "Artifacts at rubbles after 2015 earthquake", "rubbles"));
        placesList.add(new Places("", "Nyatapolo at night", "nightpolo"));
        placesList.add(new Places("", "Wood carving at Vatsala Temple", "vatsala"));
        placesList.add(new Places("", "Children in traditional newa attire", "children"));
        placesList.add(new Places("", "Chariot of Bhairavnath with temple of its own in background", "bhailakha"));
        placesList.add(new Places("", "Dattatraya captured", "datattraya"));
        placesList.add(new Places("", "Newa girl dressed up for ritual called (ihi)", "ihimacha"));
        placesList.add(new Places("", "Sukunda", "panas"));
        placesList.add(new Places("", "Big bell of Bhaktapur with statue of Bhupatindra Malla in background", "bigbell"));

        placesList.add(new Places("", "Siddha Pukhu", "siddha"));

        placesList.add(new Places("", "Pottery in making", "pottery1"));
        //placesList.add(new Places("", "Renovated Bhairav temple at taumadhi", "bhairav"));
        // placesList.add(new Places("", "Siddhi Laxmi temple at Durbar Square", "siddhilaxmi"));
        placesList.add(new Places("", "Potters Square at noon", "potters"));
        //  placesList.add(new Places("", "Wakupati Narayan Temple near Dattatreya", "wakupati"));
        placesList.add(new Places("", "Woman threshing grain in traditional way", "grain"));
        //placesList.add(new Places("", "The perfect shot", "nyatapolo12"));
        //placesList.add(new Places("", "Cuteness Overloaded", "traditionalboy"));
        placesList.add(new Places("", "Hymn of the Weekend", "traditionalmusic"));
        placesList.add(new Places("", "Music in the air", "khing"));
        placesList.add(new Places("", "The beauty", "peacockw"));
        placesList.add(new Places("", "Shining at dark ", "dbatnight"));

        places.setValue(placesList);

    }

}
