package com.example.guide.ui.calendar;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.guide.R;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CalendarFragment extends Fragment {

    CompactCalendarView compactCalendarView;
    List<Event> eventList;
    TextView textView;
    Button button;
    TextView textView1;


    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());


    private CalendarViewModel mViewModel;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.calendar_fragment, container, false);


        compactCalendarView = view.findViewById(R.id.compactcalendar_view);

        textView = view.findViewById(R.id.calendarTextView);


        textView1 = view.findViewById(R.id.text1);


        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override

            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);

                try {
                    Event event = events.get(0);
                    Toast.makeText(getActivity(), "" + event.getData(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "No EventActivity", Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

                textView.setText(dateFormatMonth.format(firstDayOfNewMonth));
                textView1.setText(getSection(firstDayOfNewMonth));
            }
        });

        return view;
    }

    private String  getSection(Date date) {
        //String fDate = new java.text.SimpleDateFormat("yyyy-MMM-dd").format(date);

        String eve="";
        Boolean present = false;
        for (Event event : compactCalendarView.getEventsForMonth(date)) {
            present=true;
            eve+= new Date(event.getTimeInMillis()).toString().substring(4,10) + " - " + event.getData()+ "\n";

        }
        if (!present) {
            eve += "No Activity";

        }
        return eve;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        mViewModel.loadEvent().observe(this, new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                    compactCalendarView.setUseThreeLetterAbbreviation(true);

                    compactCalendarView.addEvents(events);
                    textView1.setText(getSection(compactCalendarView.getFirstDayOfCurrentMonth()));

                    textView.setText(dateFormatMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

            }
        });
    }

}
