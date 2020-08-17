package src;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DateSearcher implements EventSearcher<LocalDateTime> {
    //Searches through a list of events and returns those that have the specific date.
    @Override
    public ArrayList<Event> search(ArrayList<Event> events, LocalDateTime date) {
        ArrayList<Event> lst = new ArrayList<Event>();
        for (Event event : events) {
            if (event.getStartTime().withHour(0).withMinute(0).withSecond(0) == date) {
                lst.add(event);
            }
        }
        return lst;
    }

    public ArrayList<Event> searchBefore(ArrayList<Event> events, LocalDateTime date) {
        ArrayList<Event> lst = new ArrayList<Event>();
        for (Event event : events) {
            if (event.getStartTime().isBefore(date)) {
                lst.add(event);
            }
        }
        return lst;
    }

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
