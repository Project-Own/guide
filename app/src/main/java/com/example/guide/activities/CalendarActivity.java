package com.example.guide.activities;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import iammert.com.expandablelib.ExpandCollapseListener;
import iammert.com.expandablelib.ExpandableLayout;
import iammert.com.expandablelib.Section;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CalendarActivity extends AppCompatActivity {


    CompactCalendarView compactCalendarView;
    List<Event> eventList;
    TextView textView;
    Button button;


    ExpandableLayout layout;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());

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

        compactCalendarView.addEvent(ev1);
        compactCalendarView.addEvent(ev2);
        compactCalendarView.addEvent(ev3);
        compactCalendarView.addEvent(ev4);
        compactCalendarView.addEvent(ev5);
        compactCalendarView.addEvent(ev6);
        compactCalendarView.addEvent(ev7);
        compactCalendarView.addEvent(ev8);
        compactCalendarView.addEvent(ev9);
    }

    public void Dashain() {
        Event ev1 = new Event(Color.RED, 1569775412000L, "Ghatasthapana! Start of Dashain");
        Event ev2 = new Event(Color.RED, 1570293812000L, "Fulpati");
        Event ev3 = new Event(Color.RED, 1570380212000L, "Maha Astami");
        Event ev4 = new Event(Color.RED, 1570466612000L, "Maha Nawami");
        Event ev5 = new Event(Color.RED, 1570553012000L, "vijaya Dashami! End of Dashain");


        compactCalendarView.addEvent(ev1);
        compactCalendarView.addEvent(ev2);
        compactCalendarView.addEvent(ev3);
        compactCalendarView.addEvent(ev4);
        compactCalendarView.addEvent(ev5);
    }

    public void Tihar() {
        Event ev1 = new Event(Color.RED, 1572108212000L, "Kag Tihar! (Worshipping of Crow)Start of Tihar");
        Event ev2 = new Event(Color.RED, 1572194612000L, "Kukur Tihar! (Worshipping of Dog)");
        Event ev3 = new Event(Color.RED, 1572281012000L, "Laxmi Puja! (Worshipping Goddess of Wealth)");
        Event ev4 = new Event(Color.RED, 1572367412000L, "Mha puja! (Worshipping Human body)");
        Event ev5 = new Event(Color.RED, 1572453812000L, "Kija puja! End of Tihar");


        compactCalendarView.addEvent(ev1);
        compactCalendarView.addEvent(ev2);
        compactCalendarView.addEvent(ev3);
        compactCalendarView.addEvent(ev4);
        compactCalendarView.addEvent(ev5);
    }

    public void OtherEvents() {
        Event ev1 = new Event(Color.RED, 1565973812000L, "GaiJatra");
        Event ev2 = new Event(Color.RED, 1568393012000L, "IndraJatra");
        Event ev3 = new Event(Color.RED, 1576169012000L, "Yomari Punhi");
        Event ev4 = new Event(Color.RED, 1580402612000L, "Shree Panchami");
        Event ev5 = new Event(Color.RED, 1582303412000L, "Shiva ratri");
        Event ev6 = new Event(Color.RED, 1583772212000L, "Happy holi");
        Event ev7 = new Event(Color.GREEN, 1572367412000L, "nhu da ya vintuna! new year");


        compactCalendarView.addEvent(ev1);
        compactCalendarView.addEvent(ev2);
        compactCalendarView.addEvent(ev3);
        compactCalendarView.addEvent(ev4);
        compactCalendarView.addEvent(ev5);
        compactCalendarView.addEvent(ev6);
        compactCalendarView.getEventsForMonth(12312);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        compactCalendarView = findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);

        DrawerLayout drawer = findViewById(R.id.drawer_layout1);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationBar(this, drawer, this.getClass().getSimpleName()));

        textView = findViewById(R.id.calendarTextView);

        bisket();
        Dashain();
        Tihar();
        OtherEvents();

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CalendarActivity.this, EventActivity.class);


                startActivity(intent);
            }
        });


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override

            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);

                try {
                    Event event = events.get(0);
                    Toast.makeText(CalendarActivity.this, "" + event.getData(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(CalendarActivity.this, "No EventActivity", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                textView.setText(dateFormatMonth.format(firstDayOfNewMonth));
                layout.removeAllViews();
              layout.addSection( getSection(firstDayOfNewMonth));
            }
        });

        layout = findViewById(R.id.expandable_layout);
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

    }


    private Section<PhoneCategory, Phone> getSection(Date date) {
        Section<PhoneCategory, Phone> section = new Section<>();
       String fDate = new java.text.SimpleDateFormat("yyyy-MMM-dd").format(date);
        PhoneCategory PhoneCategory1 = new PhoneCategory(fDate.substring(5,8));

        List<Phone> PhoneList = new ArrayList<>();


        Boolean present = false;
        for (Event event : compactCalendarView.getEventsForMonth(date)) {
            present=true;
            PhoneList.add(new Phone(event.getData().toString(), event.getData().toString()));

        }
        if (!present) {
            PhoneList.add(new Phone("No Activity", "No Activity"));

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
