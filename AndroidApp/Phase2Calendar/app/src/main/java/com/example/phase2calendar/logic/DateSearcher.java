package com.example.phase2calendar.logic;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DateSearcher implements EventSearcher<LocalDateTime> {
    //Searches through a list of events and returns those that have the specific date.
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public ArrayList<Event> search(ArrayList<Event> events, LocalDateTime date) {
        ArrayList<Event> lst = new ArrayList<Event>();
        for (Event event : events) {
            if (event.getStartTime().withHour(0).withMinute(0).withSecond(0).isEqual(date)) {
                lst.add(event);
            }
        }
        return lst;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Event> searchBefore(ArrayList<Event> events, LocalDateTime date) {
        ArrayList<Event> lst = new ArrayList<Event>();
        for (Event event : events) {
            if (event.getStartTime().isBefore(date)) {
                lst.add(event);
            }
        }
        return lst;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Event> searchAfter(ArrayList<Event> events, LocalDateTime date) {
        ArrayList<Event> lst = new ArrayList<Event>();
        for (Event event : events) {
            if (event.getStartTime().isAfter(date)) {
                lst.add(event);
            }
        }
        return lst;
    }
}
