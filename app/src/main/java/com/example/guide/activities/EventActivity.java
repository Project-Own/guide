package com.example.guide.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.guide.Modal.Calendar.Phone;
import com.example.guide.Modal.Calendar.PhoneCategory;
import com.example.guide.NavigationBar;
import com.example.guide.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import iammert.com.expandablelib.ExpandCollapseListener;
import iammert.com.expandablelib.ExpandableLayout;
import iammert.com.expandablelib.Section;

public class EventActivity extends AppCompatActivity {

    List<Event> events = new ArrayList<>();
    private CompactCalendarView compactCalendarView;

    public void bisket() {
        Event ev1 = new Event(Color.RED, 1554914612000L, "Bisket Jatra");
        Event ev2 = new Event(Color.RED, 1555001012000L, "Bisket Jatra");
        Event ev3 = new Event(Color.RED, 1555087412000L, "Bisket Jatra");
        Event ev4 = new Event(Color.RED, 1555173812000L, "Bisket Jatra");
        Event ev5 = new Event(Color.RED, 1555260212000L, "Bisket Jatra");
        Event ev6 = new Event(Color.RED, 1555346612000L, "Bisket Jatra");
        Event ev7 = new Event(Color.RED, 1555433012000L, "Bisket Jatra");
        Event ev8 = new Event(Color.RED, 1555519412000L, "Bisket Jatra");
        Event ev9 = new Event(Color.RED, 1555605812000L, "Bisket Jatra");

        events.add(ev1);
        events.add(ev2);
        events.add(ev3);
        events.add(ev4);
        events.add(ev5);
        events.add(ev6);
        events.add(ev7);
        events.add(ev8);
        events.add(ev9);
    }

    public void Dashain() {
        Event ev1 = new Event(Color.RED, 1569775412000L, "Ghatasthapana! Start of Dashain");
        Event ev2 = new Event(Color.RED, 1570293812000L, "Fulpati");
        Event ev3 = new Event(Color.RED, 1570380212000L, "Maha Astami");
        Event ev4 = new Event(Color.RED, 1570466612000L, "Maha Nawami");
        Event ev5 = new Event(Color.RED, 1570553012000L, "vijaya Dashami! End of Dashain");

        events.add(ev1);
        events.add(ev2);
        events.add(ev3);
        events.add(ev4);
        events.add(ev5);

    }

    public void Tihar() {
        Event ev1 = new Event(Color.RED, 1572108212000L, "Kag Tihar! (Worshipping of Crow)Start of Tihar");
        Event ev2 = new Event(Color.RED, 1572194612000L, "Kukur Tihar! (Worshipping of Dog)");
        Event ev3 = new Event(Color.RED, 1572281012000L, "Laxmi Puja! (Worshipping Goddess of Wealth)");
        Event ev4 = new Event(Color.RED, 1572367412000L, "Mha puja! (Worshipping Human body)");
        Event ev5 = new Event(Color.RED, 1572453812000L, "Kija puja! End of Tihar");


        events.add(ev1);
        events.add(ev2);
        events.add(ev3);
        events.add(ev4);
        events.add(ev5);
    }

    public void OtherEvents() {
        Event ev1 = new Event(Color.RED, 1565973812000L, "GaiJatra");
        Event ev2 = new Event(Color.RED, 1568393012000L, "IndraJatra");
        Event ev3 = new Event(Color.RED, 1576169012000L, "Yomari Punhi");
        Event ev4 = new Event(Color.RED, 1580402612000L, "Shree Panchami");
        Event ev5 = new Event(Color.RED, 1582303412000L, "Shiva ratri");
        Event ev6 = new Event(Color.RED, 1583772212000L, "Happy holi");


        events.add(ev1);
        events.add(ev2);
        events.add(ev3);
        events.add(ev4);
        events.add(ev5);
        events.add(ev6);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationBar(this, drawer, this.getClass().getSimpleName()));

        ExpandableLayout layout = findViewById(R.id.expandable_layout);
        layout.setRenderer(new ExpandableLayout.Renderer<PhoneCategory, Phone>() {

            @Override
            public void renderParent(View view, PhoneCategory PhoneCategory, boolean isExpanded, int parentPosition) {
                ((TextView) view.findViewById(R.id.tv_parent_name)).setText(PhoneCategory.name);
                view.findViewById(R.id.arrow).setBackgroundResource(isExpanded ? R.drawable.ic_up_arrow_foreground : R.drawable.ic_down_arrow_foreground);

            }

            @Override
            public void renderChild(View view, Phone Phone, int parentPosition, int childPosition) {
                ((TextView) view.findViewById(R.id.tv_child_name)).setText(Phone.name);
            }
        });


        layout.setExpandListener(new ExpandCollapseListener.ExpandListener<PhoneCategory>() {
            @Override
            public void onExpanded(int i, PhoneCategory phoneCategory, View view) {
                view.findViewById(R.id.arrow).setBackgroundResource(R.drawable.ic_up_arrow_foreground);

            }
        });

        layout.setCollapseListener(new ExpandCollapseListener.CollapseListener<PhoneCategory>() {
            @Override
            public void onCollapsed(int i, PhoneCategory phoneCategory, View view) {
                view.findViewById(R.id.arrow).setBackgroundResource(R.drawable.ic_down_arrow_foreground);

            }
        });
        bisket();
        Dashain();
        Tihar();
        OtherEvents();



        layout.addSection(getSection("April"));
        layout.addSection(getSection("May"));
        layout.addSection(getSection("June"));
        layout.addSection(getSection("July"));
        layout.addSection(getSection("August"));
        layout.addSection(getSection("September"));
        layout.addSection(getSection("October"));
        layout.addSection(getSection("November"));
        layout.addSection(getSection("December"));
        layout.addSection(getSection("January"));
        layout.addSection(getSection("February"));
        layout.addSection(getSection("March"));

    }

    private Section<PhoneCategory, Phone> getSection(String month) {
        Section<PhoneCategory, Phone> section = new Section<>();
        PhoneCategory PhoneCategory1 = new PhoneCategory(month);

        List<Phone> PhoneList = new ArrayList<>();


        Boolean present = false;
        for (Event event : events) {
            Date date = new Date(event.getTimeInMillis());
            String fDate = new SimpleDateFormat("yyyy-MMM-dd").format(date);
            Log.i("Dates", "" + month.substring(0, 3));

            if (fDate.substring(5, 8).equals(month.substring(0, 3))) {
                present = true;
                PhoneList.add(new Phone(event.getData().toString(), event.getData().toString()));
            }
        }
        if (!present) {
            PhoneList.add(new Phone("No EventActivity", "No EventActivity"));

        }
        section.parent = PhoneCategory1;
        section.children.addAll(PhoneList);
        return section;

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
