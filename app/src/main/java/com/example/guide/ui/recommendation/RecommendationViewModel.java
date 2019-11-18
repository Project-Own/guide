package com.example.guide.ui.recommendation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.guide.Model.Recommendation;

import java.util.ArrayList;
import java.util.List;

public class RecommendationViewModel extends AndroidViewModel {

    private MutableLiveData<List<Recommendation>> recommendationList;
    private MutableLiveData<List<Recommendation>> hotelList;

    public RecommendationViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Recommendation>> loadHotel(){
        if(hotelList == null){
            hotelList = new MutableLiveData<List<Recommendation>>();
            loadHotelList();
        }
        return hotelList;
    }


    public MutableLiveData<List<Recommendation>> loadRecommendedPlaces(){
        if(recommendationList == null){
            recommendationList = new MutableLiveData<List<Recommendation>>();
            loadRecommendedPlacesList();
        }
        return recommendationList;
    }

    private void loadRecommendedPlacesList() {
        List<Recommendation> recomendationsPlaces = new ArrayList<>();
        recomendationsPlaces.add(new Recommendation("Bhaktapur Durbar Square", "", "", "", "", "one of the largest ancient squares in Nepal filled to the " +
                "\n brim with temples, cultural carvings and buildings ", "durbarsquare"));
        recomendationsPlaces.add(new Recommendation("Basantapur Chowk", "", "", "", "", "two famous sculptures that cost a man his hands \n" +
                "\n", "basantapur"));
        recomendationsPlaces.add(new Recommendation("The Palace of Fifty-five Windows", "", "", "", "", "a masterpiece of wood carving The Golden Gate: one of " +
                "\n the most intricate and well decorated gates in the world", "durbarsquare"));
        recomendationsPlaces.add(new Recommendation("Pashupatinath Temple", "", "", "", "", "impressive wooden temple", "pasupati"));
        recomendationsPlaces.add(new Recommendation("Vatsala Durga Temple & Taleju Bell", "", "", "", "", "Stone temple with bell built by King Jagat Prakash Malla " +
                "\n (currently - 2017/18- under reconstruction however you can give donations directly to its rebuilding) ", "vatsala"));
        recomendationsPlaces.add(new Recommendation("Siddhi Laxmi Temple", "", "", "", "", "A stone temple built in the 17th-century (currently - 2017/18- under reconstuction)\n" +
                "\n", "siddhilaxmi"));
        recomendationsPlaces.add(new Recommendation("Pottery Square", "", "", "", "", "Watch Bhaktapur's famous pottery as it's made in this idyllic traditional square filled with potters and their wares. ", "potters"));
        recomendationsPlaces.add(new Recommendation("Royal Curd", "", "", "", "", "Take a time out and eat some of Bhaktapur's famous curd", "kingcurd"));
        recomendationsPlaces.add(new Recommendation("Side streets of Bhaktapur", "", "", "", "", "Spend hours wandering off down the side " +
                "\n streets of the city enjoying local handicraft stores, " +
                "\n artisans, local cafes and countless ancient buildings ", "alley"));
        recomendationsPlaces.add(new Recommendation("Taumadhi Square", "", "", "", "", "a huge square with the tallest temple in Nepal", "nightpolo"));

        recomendationsPlaces.add(new Recommendation("Dattatreya Square", "", "", "", "", "the oldest royal square in Nepal filled with things to see and do.", "datattreya"));

        recomendationsPlaces.add(new Recommendation("The Peacock Window", "", "", "", "", "One of Nepal's most precious woodcarvings and national art treasures - there are several craft stores along the same street worth visiting", "peacockw"));

        recommendationList.setValue(recomendationsPlaces);
    }

    private void loadHotelList() {

        List<Recommendation> recomendations = new ArrayList<>();
        recomendations.add(new Recommendation("Peacock Guest House", "4.5/5", "01-6611829", "Datattreya Square", "NPR 4031 per night", "On the main square stands this well run guesthouse with clean well decorated rooms in Newari design.", "peacock"));
        recomendations.add(new Recommendation("Milla Guest House", "5/5", " 9851027012/9851024137", "Datattreya Square", "NPR 7932 per night", "Highly rated hotel and have bery good reputation locally", "milla"));
        recomendations.add(new Recommendation("Thagu Chenn", "5/5", "01-6612043/9851152541", "Bhaktapur Durbar Square", "NPR 6643 per night", "Located just 50m away from UNESCO enlisted Bhaktapur Durbar Square, THAGU CHHEN, is a heritage boutique Bed and Breakfast " +
                "initiated as a step towards preserving culture with a sustainable and green approach", "thagu"));
        recomendations.add(new Recommendation("Shiva Guest House 1 & 2", "4.5/5", "01-6613912/01-6610740", "Bhaktapur Durbar Square", "NPR 1158 per  night", "Eco-Friendly Heritage Guest House. SHIVA GUEST HOUSE-pleasantly located in the heart of the Durbar Square is your address in Bhaktapur. Standing next to the Yaksheshwor" +
                " Temple the city's oldest edifice dating back to 1472 AD, " +
                "the guest house is where you can start your exploration trip from.", "shiva"));


        hotelList.setValue(recomendations);
    }




}

