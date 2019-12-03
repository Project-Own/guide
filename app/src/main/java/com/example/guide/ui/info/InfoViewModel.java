package com.example.guide.ui.info;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.guide.Model.Info;

public class InfoViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<Info> infoData;
    private MutableLiveData<String> ticketInfo;
    private MutableLiveData<String> price;
    private MutableLiveData<String> aboutAbstract;
    private MutableLiveData<String> about;



    private Info info;
    public InfoViewModel(@NonNull Application application) {
        super(application);
    }
    
    public MutableLiveData<Info> getInfoData(){
        if(infoData == null){
            infoData = new MutableLiveData<Info>();
            setInfoData();
        }
        return infoData;
    }

    private void setInfoData() {
        String infoText = "Bhaktapur, known as Khwopa in local Nepal bhasa is the eastern gateway of the Kathmandu valley which lies about" +
                " 16 kilometers from the heart of Kathmandu city. Bhaktapur is a medieval town locked in centuries–old beliefs and trad" +
                "itions. The city spreads over an area of 6.88 sq. km and lies at 1401 meters above sea level. Also known from its second" +
                " name Bhadgoun, Bhaktapur translates to city of devotees which indicates its religious antecedents.\n\n King Ananda Malla is" +
                " reputed to have founded the town- although it is more likely that a group of villages involved in trade with Tibet slowly" +
                " came together to shape it. Bhaktapur reached the pinnacle of its glory during the Malla era and Bhaktapur has maintained" +
                " its individuality mainly by virtue of its self- sufficiency and isolation from Kathmandu for a long time.\n\n Bhaktapur gives" +
                " shelter to almost 100 thousand people, most of whom are peasants. Businessman, handicraft producers and public employees" +
                " are among others. While walking in the street of Bhaktapur one could find artisans at work, craftsmen producing their wares" +
                " and modern facilities. Majority of the population are Hindus and Buddhist, however there is a religious harmony among the " +
                "people which has avoided conflicts for centuries. Every festival and cultural activity, irrespective of its religion, is " +
                "observed and celebrated with enthusiasm.\n\n Bhaktapur is decorated by the blend of northern art and southern mythological " +
                "philosophy. These can be seen in the Pagoda and Sikhar style temples, Vihars, Bahis and other cultural and historical" +
                " heritages. Bhaktapur is living heritage displaying the vibrant depth of Newari culture.";
        String infoTicketData = "This ticket covers all of Bhaktapur including, Bhaktapur Durbar Square, Pottery Square, Taumadhi Square and " +
                "Dattatreya Tole. Ticket can be extended for one week with no extra cost if you are planning to stay in Bhaktapur.\n\nTicket" +
                " booths are located at all the main entrance streets into the old city of Bhaktapur and there are random \"ticket inspections\" so do hold on to your ticket! \n" +
                "\n";

        String aboutAbstractData = "Bhaktapur, known as Khwopa in local Nepal bhasa is the eastern gateway of the Kathmandu valley which lies about 16 kilometers from the heart of Kathmandu city. Bhaktapur is a medieval town locked in centuries–old beliefs and trad" +
                "itions.";
        String priceData = "SAARC COUNTRIES and China: Rs 500 \nOTHER COUNTRIES: Rs 1500";

        info = new Info();
        info.setAbout(aboutAbstractData);
        info.setInfo(infoText);
        info.setPrice(priceData);
        info.setTicketInfo(infoTicketData);

        infoData.setValue(info);
//        price.setValue(priceData);
//        ticketInfo.setValue(infoTicketData);
//        aboutAbstract.setValue(aboutAbstractData);
//        about.setValue(infoText);

    }


}
