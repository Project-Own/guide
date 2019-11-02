package com.example.guide.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.guide.Modal.Users;
import com.example.guide.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    String info = "Bhaktapur, known as Khwopa in local Nepal bhasa is the eastern gateway of the Kathmandu valley which lies about 16 kilometers from the heart of Kathmandu city. Bhaktapur is a medieval town locked in centuries–old beliefs and trad" +
            "itions. The city spreads over an area of 6.88 sq. km and lies at 1401 meters above sea level. Also known from its second name Bhadgoun, Bhaktapur translates to city of devotees which indicates its religious antecedents. King Ananda Malla is reputed to have founded the town- although it is" +
            " more likely that a group of villages involved in trade with Tibet slowly came together to shape it. Bhaktapur reached the pinnacle of its glory during the Malla era and Bhaktapur has maintained its individuality mainly by virtue of its self- sufficiency and isolation from Kathmandu for a long time. Bhaktapur gives shelter to almost 100 thousand people, most of whom are peasants. Businessman, handicraft producers and " +
            "public employees are among others. While walking in the street of Bhaktapur one could find artisans at work, craftsmen producing their wares and modern facilities. Majority of the population are Hindus and Buddhist, however there is a religious harmony among the people which has avoided conflicts for centuries. Every festival and cultural activity, irrespective of its religion, is observed and celebrated with enthusiasm. Bhaktapur is decorated by the blend of northern art and southern mythological philosophy. These can be seen in the Pagoda and Sikhar style temples, Vihars, Bahis and other cultural " +
            "and historical heritages. Bhaktapur is living heritage displaying the vibrant depth of Newari culture.";
    String ticketInfo = "The current fee for foreigners is included in your overall Bhaktapur ticket price. This ticket covers all of Bhaktapur including, Bhaktapur Durbar Square, Pottery Square, Taumadhi Square and Dattatreya Tole. If you are planning to stay in Bhaktapur show your passport at the ticket office and you can get the same ticket extended for one week with no extra cost. Ticket booths are located at all the main entrance streets into the old city of Bhaktapur and there are random \"ticket inspections\" so do hold on to your ticket! \n" +
            "\n";
    TextView infoTextView;
    private RecyclerView recycleView;
    private List<Users> users;
    private TextView infoTicketTextView;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_info, container, false);
        infoTextView = v.findViewById(R.id.infoTextView);
        infoTextView.setText(info);
        infoTicketTextView = v.findViewById(R.id.infoTicketTextView);
        infoTicketTextView.setText(info);


        return v;
    }

}
