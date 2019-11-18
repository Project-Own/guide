package com.example.guide.ui.calendar;

import android.app.Application;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.List;

public class CalendarViewModel extends AndroidViewModel {

    private MutableLiveData<List<Event>> eventList;
    public CalendarViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Event>> loadEvent(){
        if(eventList == null){
            eventList = new MutableLiveData<>();
            loadEventList();
        }
        return eventList;
    }

    private void loadEventList() {
       List<Event> events;
       events = new ArrayList<>();
        //Bisket
        events.add(new Event(Color.RED, 1554914612000L, "Bisket Jatra"));
        events.add(new Event(Color.RED, 1555001012000L, "Bisket Jatra"));
        events.add(new Event(Color.RED, 1555087412000L, "Bisket Jatra"));
        events.add(new Event(Color.RED, 1555173812000L, "Bisket Jatra"));
        events.add(new Event(Color.RED, 1555260212000L, "Bisket Jatra"));
        events.add(new Event(Color.RED, 1555346612000L, "Bisket Jatra"));
        events.add(new Event(Color.RED, 1555433012000L, "Bisket Jatra"));
        events.add(new Event(Color.RED, 1555519412000L, "Bisket Jatra"));
        events.add(new Event(Color.RED, 1555605812000L, "Bisket Jatra"));

        //Dashain
        events.add(new Event(Color.RED, 1569775412000L, "Ghatasthapana! Start of Dashain"));
        events.add(new Event(Color.RED, 1570293812000L, "Fulpati"));
        events.add(new Event(Color.RED, 1570380212000L, "Maha Astami"));
        events.add(new Event(Color.RED, 1570466612000L, "Maha Nawami"));
        events.add(new Event(Color.RED, 1570553012000L, "Vijaya Dashami! End of Dashain"));


        //Tihar
        events.add(new Event(Color.RED, 1572108212000L, "Kag Tihar! (Worshipping of Crow)Start of Tihar"));
        events.add(new Event(Color.RED, 1572194612000L, "Kukur Tihar! (Worshipping of Dog)"));
        events.add(new Event(Color.RED, 1572281012000L, "Laxmi Puja! (Worshipping Goddess of Wealth)"));
        events.add(new Event(Color.RED, 1572367412000L, "Mha puja! (Worshipping Human body)"));
        events.add(new Event(Color.RED, 1572453812000L, "Kija puja! End of Tihar"));

        //Other Evnet
        events.add(new Event(Color.RED, 1565973812000L, "GaiJatra"));
        events.add(new Event(Color.RED, 1568393012000L, "IndraJatra"));
        events.add(new Event(Color.RED, 1576169012000L, "Yomari Punhi"));
        events.add(new Event(Color.RED, 1580402612000L, "Shree Panchami"));
        events.add(new Event(Color.RED, 1582303412000L, "Shiva ratri"));
        events.add(new Event(Color.RED, 1583772212000L, "Happy holi"));
        events.add(new Event(Color.GREEN, 1572367412000L, "nhu da ya vintuna! new year"));

        eventList.setValue(events);

    }
}
