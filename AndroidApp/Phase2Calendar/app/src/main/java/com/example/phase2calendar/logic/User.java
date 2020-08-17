package com.example.phase2calendar.logic;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.MonthDay;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Observable;

public class User extends Observable implements Serializable {

    private String username;
    private String password;
    private transient Context context;
    private static final long serialVersionUID = 42L;

    private ArrayList<Calendar> calendarsList;
    private ArrayList<Message> messagesList;
    private ArrayList<MonthDay> holidays;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.calendarsList = new ArrayList<>();
        this.messagesList = new ArrayList<>();
        initializeHolidays();
    }

    public void initializeHolidays() {
        this.holidays = new ArrayList<>();
        holidays.add(MonthDay.of(1, 1));
        holidays.add(MonthDay.of(10, 31));
        holidays.add(MonthDay.of(12, 25));
        holidays.add(MonthDay.of(12, 31));
    }

    public void signalChanges() {
        setChanged();
        if (hasChanged()) notifyObservers(context);
        clearChanged();
    }

    public Calendar getCalendar(int i) {
        return calendarsList.get(i);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Context getContext() { return context; }

    public void setContext(Context context) { this.context = context; }

    public void addCalendar(Calendar calendar) {
        calendarsList.add(calendar);
        signalChanges();
    }

    public ArrayList<Calendar> getCalendarsList() { return calendarsList; }

    public void addEventToCalendar(Event event, Calendar calendar) {
        calendar.addEvent(event);
        signalChanges();
    }

    public void removeEventFromCalendar(Event event, Calendar calendar) {
        calendar.removeEvent(event);
        signalChanges();
    }

    public ArrayList<Event> getEventsFromCalendar(Calendar calendar) {
        return calendar.getEvents();
    }

    public void editEventInCalendar(Calendar calendar, Event event, String name, String description, LocalDateTime startTime, LocalDateTime endTime) {
        calendar = calendarsList.get(calendarsList.indexOf(calendar));
        calendar.editEvent(event, name, description, startTime, endTime);
        signalChanges();
    }

    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> returnList = new ArrayList<>();
        for(Calendar calendar:calendarsList){
            returnList.addAll(calendar.getEvents());
        }
        return returnList;
    }

    public void addTagToEventInCalendar(Event event, Tag att, Calendar calendar){
        calendar.addTagToEvent(event, att);
        signalChanges();
    }

    public void removeTagFromEventInCalendar(Event event, Tag att, Calendar calendar){
        calendar.removeTagFromEvent(event, att);
        signalChanges();
    }

    public void addMemoToEventInCalendar(Event event, Memo memo, Calendar calendar){
        calendar.addMemoToEvent(event, memo);
        signalChanges();
    }

    public void removeMemoFromEventInCalendar(Event event, Calendar calendar){
        calendar.removeMemoFromEvent(event);
        signalChanges();
    }

    public void addSeriesToCalendar(Series series, Calendar calendar) {
        calendar.addSeries(series);
        signalChanges();
    }

    public void removeSeriesFromCalendar(Series series, Calendar calendar) {
        calendar.removeSeries(series);
        signalChanges();
    }

    public void addEventToSeriesInCalendar(Event event, Series series, Calendar calendar) {
        calendar.addEventToSeries(event, series);
        signalChanges();
    }

    public ArrayList<Series> getSeriesFromCalendar(Calendar calendar) {
        return calendar.getSeries();
    }

    public void addMemoToCalendar(Memo memo, Calendar calendar) {
        calendar.addMemo(memo);
        signalChanges();
    }

    public void removeMemoFromCalendar(Memo memo, Calendar calendar) {
        calendar.removeMemo(memo);
        signalChanges();
    }

    public ArrayList<Memo> getMemosFromCalendar(Calendar calendar) {
        return calendar.getMemos();
    }

    public ArrayList<Memo> getAllMemos() {
        ArrayList<Memo> memosList = new ArrayList<>();
        for(Calendar c:calendarsList){
            memosList.addAll(c.getMemos());
        }
        return memosList;
    }

    public void addAlertToEventInCalendar(Event event, Triple<String, String, LocalDateTime> t, Calendar calendar) {
        calendar.addAlertToEvent(event, t);
        signalChanges();
    }

    public void addAlertsToEventInCalendar(Event event, ArrayList<Triple<String, String, LocalDateTime>> t, Calendar calendar) {
        calendar.addAlertsToEvent(event, t);
        signalChanges();
    }

    public void editAlertInEventInCalendar(Event event, Alert alert, Calendar calendar, String name, String description, LocalDateTime startTime) {
        calendar = calendarsList.get(calendarsList.indexOf(calendar));
        event = calendar.getEvents().get(calendar.getEvents().indexOf(event));
        event.editAlert(alert, name, description, startTime);
        signalChanges();
    }

    public void deleteAlert(Event event, Alert alert, Calendar calendar) {
        calendar = calendarsList.get(calendarsList.indexOf(calendar));
        event = calendar.getEvents().get(calendar.getEvents().indexOf(event));
        event.deleteAlert(alert);
        signalChanges();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public int isHoliday(LocalDateTime now) {
        MonthDay today = MonthDay.from(now);
        for (int i = 0; i < holidays.size(); i++) {
            if (today.equals(holidays.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public String getHoliday(int holiday) {
        switch (holiday) {
            case 0:
                return "Happy New Years!!!";
            case 1:
                return "Happy Halloween";
            case 2:
                return "Merry Christmas!";
            case 3:
                return "It's New Year's Eve!!!";
            default:
                return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Alert> raiseAllAlerts(LocalDateTime now) {
        ArrayList<Alert> list = new ArrayList<Alert>();
        for(Calendar c:calendarsList){
            list.addAll(c.raiseAllAlerts(now));
        }
        signalChanges();
        return list;
    }

    public void addMessage(Message message){
        messagesList.add(message);
        signalChanges();
    }

    public void deleteMessage(Message message){
        messagesList.remove(message);
        signalChanges();
    }

    public void sendMessage(Message message, User recipient){
        recipient.addMessage(message);
    }

    public ArrayList<Message> getMessagesList() { return messagesList; }
}