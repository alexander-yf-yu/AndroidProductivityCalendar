package com.example.phase2calendar.logic;

public class EventSearcherFactory {
    public EventSearcher getEventSearcher(int type){
        switch (type){
            case 4:
                return new AttachmentSearcher();
            case 3:
                return new DateSearcher();
            case 1:
                return new EventNameSearcher();
            default:
                return null;
        }
    }
}
