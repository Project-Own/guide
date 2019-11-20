package com.example.guide.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.guide.Model.Home;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Home>> homeList;
    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Home>> getHomeList(){
        if(homeList == null){
            homeList = new MutableLiveData<List<Home>>();

            loadHomeData();
        }

        return homeList;
    }

    private void loadHomeData(){
        List<Home> home = new ArrayList<>();
        home.add(new Home("Navigation", "navigationws"));
        home.add(new Home("Weather", "weather_icon"));
        home.add(new Home("Heritages", "nyatapolo_logo"));
        home.add(new Home("Food", "okmyanws1"));
        home.add(new Home("Calendar", "calendarws1"));
        home.add(new Home("Currency", "currencyws2"));
        home.add(new Home("Translator", "translatorws"));
        home.add(new Home("Gallery", "galleryws"));

        homeList.setValue(home);


    }
}
