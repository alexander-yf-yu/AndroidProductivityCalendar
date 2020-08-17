package src;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.DayOfWeek;

public class Series implements Serializable {

    private String name;
    private String description;
    private ArrayList<Event> events;

    public Series(String name, String description) {
        this.name = name;
        this.description = description;
        this.events = new ArrayList<Event>();
    }

    public Series(String name, String description, LocalDateTime startDateTime, LocalTime endTime, int dayGap, int numEvents) {
        this.name = name;
        this.description = description;
        this.events = new ArrayList<Event>();

        for (int i = 0; i < numEvents; i++) {
            this.events.add(new Event("Event from series " + name, description, startDateTime.plusDays(i * dayGap), startDateTime.plusDays(i * dayGap).withHour(endTime.getHour()).withMinute(endTime.getMinute())));
        }
    }

    public Series(String name, String description, LocalDateTime startDateTime, LocalTime endTime, ArrayList<DayOfWeek> days, int numEvents) {
        this.name = name;
        this.description = description;
        this.events = new ArrayList<Event>();

        while (numEvents > 0) {
            if (days.contains(startDateTime.getDayOfWeek())) {
                this.events.add(new Event("Event from series " + name, description, startDateTime, startDateTime.withHour(endTime.getHour()).withMinute(endTime.getMinute())));
                numEvents -= 1;
            }
            startDateTime = startDateTime.plusDays(1);
        }
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}