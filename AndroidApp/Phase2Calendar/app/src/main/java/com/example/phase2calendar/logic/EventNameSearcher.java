package com.example.phase2calendar.logic;

import java.util.ArrayList;

public class EventNameSearcher implements EventSearcher<String> {
    @Override
    public ArrayList<Event> search(ArrayList<Event> events, String eventName){
        ArrayList<Event> eventsWithName = new ArrayList<>();
        for(Event event:events){
            if(event.getName().equals(eventName)){
                eventsWithName.add(event);
            }
        }
        return eventsWithName;
    }
}
