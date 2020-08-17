package com.example.phase2calendar;

import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.phase2calendar.dialogs.SearchByDateDialog;
import com.example.phase2calendar.dialogs.SearchByEventNameDialog;
import com.example.phase2calendar.dialogs.SearchBySeriesNameDialog;
import com.example.phase2calendar.dialogs.SearchByTagDialog;
import com.example.phase2calendar.logic.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This activity is for the calendar menu
 *
 * Here you can:
 *
 * show events
 * show memos
 * show series
 * search by event name, series name, tag, and date
 */

public class MenuActivity extends AppCompatActivity implements SearchByEventNameDialog.SearchByEventNameDialogListener, SearchBySeriesNameDialog.SearchBySeriesNameDialogListener, SearchByTagDialog.SearchByTagDialogListener, SearchByDateDialog.SearchByDateDialogListener {

    private User user;
    private Calendar currentCalendar;
    private int currentCalendarIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Gets the User object and creates the Calender around it
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("currentUser");
        currentCalendarIndex = (int) intent.getSerializableExtra("currentCalendarIndex");
        currentCalendar = user.getCalendar(currentCalendarIndex);

        //Updates the page with the info from the Calender
       TextView textView = findViewById(R.id.textView6);
        textView.setText(currentCalendar.getName());

    }

    /**
     * This supports the back button for the activity
     * @return boolean.
     */

    @Override
    public boolean onSupportNavigateUp() {
        Intent back = new Intent(this, MainMenuActivity.class);
        back.putExtra("currentUser", user);
        startActivity(back);
        return true;
    }

    public void memoMenu(View view)
    {
        Intent goToMemos = new Intent(this, MemoListActivity.class);
        goToMemos.putExtra("currentCalendarIndex", currentCalendarIndex);
        goToMemos.putExtra("currentUser", user);
        startActivity(goToMemos);
    }

    public void eventMenu(View view)
    {
        Intent goToEvents = new Intent(this, EventMenuActivity.class);
        goToEvents.putExtra("currentCalendarIndex", currentCalendarIndex);
        goToEvents.putExtra("currentUser", user);
        startActivity(goToEvents);
    }

    public void seriesMenu(View view) {
        Intent intent = new Intent(this, SeriesListActivity.class);
        intent.putExtra("currentUser", user);
        intent.putExtra("currentCalendarIndex", currentCalendarIndex);
        startActivity(intent);
    }

    public void openSearchByEventName(View view) {
        SearchByEventNameDialog dialog = new SearchByEventNameDialog();
        dialog.show(getSupportFragmentManager(), "search by event name");
    }

    public void openSearchBySeriesName(View view) {
        SearchBySeriesNameDialog dialog = new SearchBySeriesNameDialog();
        dialog.show(getSupportFragmentManager(), "search by series name");
    }

    public void openSearchByDate(View view) {
        SearchByDateDialog dialog = new SearchByDateDialog();
        dialog.show(getSupportFragmentManager(), "search by date");
    }

    public void openSearchByTag(View view) {
        SearchByTagDialog dialog = new SearchByTagDialog();
        dialog.show(getSupportFragmentManager(), "search by tag");
    }

    @Override
    public void searchByEventName(String search) {
        EventNameSearcher searcher = new EventNameSearcher();
        ArrayList<Event> results = searcher.search(currentCalendar.getEvents(), search);
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("currentUser", user);
        intent.putExtra("currentCalendarIndex", currentCalendarIndex);
        intent.putExtra("searchResults", results);
        startActivity(intent);
    }

    @Override
    public void searchBySeriesName(String search) {
        ArrayList<Event> results = new ArrayList<>();
        for(Series s:currentCalendar.getSeries()){
            if(s.getName().equals(search)){
                results.addAll(s.getEvents());
            }
        }
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("currentUser", user);
        intent.putExtra("currentCalendarIndex", currentCalendarIndex);
        intent.putExtra("searchResults", results);
        startActivity(intent);
    }

    @Override
    public void searchByTag(String search) {
        AttachmentSearcher searcher = new AttachmentSearcher();
        ArrayList<Event> results = searcher.search(currentCalendar.getEvents(), search);
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("currentUser", user);
        intent.putExtra("currentCalendarIndex", currentCalendarIndex);
        intent.putExtra("searchResults", results);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void searchByDate(LocalDateTime date, int relation) {
        DateSearcher searcher = new DateSearcher();
        ArrayList<Event> results;
        if(relation == 0){
            results = searcher.searchBefore(currentCalendar.getEvents(), date);
        }
        else if(relation == 1){
            results = searcher.search(currentCalendar.getEvents(), date);
        } else {
            results = searcher.searchAfter(currentCalendar.getEvents(), date);
        }
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra("currentUser", user);
        intent.putExtra("currentCalendarIndex", currentCalendarIndex);
        intent.putExtra("searchResults", results);
        startActivity(intent);
    }
}
