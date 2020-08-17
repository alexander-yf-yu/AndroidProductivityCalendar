package com.example.phase2calendar;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phase2calendar.adapters.HighlightAdapter;
import com.example.phase2calendar.logic.*;

import java.util.ArrayList;

public class AddEventToSeriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HighlightAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private User currentUser;
    private Calendar currentCalendar;
    private int currentCalendarIndex;
    private Series currentSeries;
    private int currentSeriesIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_to_series);

        setCurrentUser();
        setRecyclerView();
    }

    /**
     * Initializes user info
     */

    public void setCurrentUser() {
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        currentUser.setContext(getApplicationContext());
        UserWriter userWriter = new UserWriter();
        currentUser.addObserver(userWriter);

        currentCalendarIndex = (int) intent.getSerializableExtra("currentCalendarIndex");
        currentCalendar = currentUser.getCalendar(currentCalendarIndex);

        currentSeriesIndex = (int) intent.getSerializableExtra("currentSeriesIndex");
        currentSeries = currentCalendar.getSeries().get(currentSeriesIndex);
    }

    /**
     * This default screen shows calendar events
     */

    public void setRecyclerView() {
        this.recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new HighlightAdapter((ArrayList) currentCalendar.getEvents());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * Supports add event button
     * Adds event to calendar and sends user back to series if event was selected
     */

    public void addEvent(View view) {
        if(adapter.getSelectedItem() == null) {
            Toast.makeText(getApplicationContext(), "You must select an event.", Toast.LENGTH_SHORT).show();
        } else {
            currentUser.addEventToSeriesInCalendar((Event) adapter.getSelectedItem(), currentSeries, currentCalendar);
            Intent intent = new Intent(this, ViewSeriesDetailsActivity.class);
            intent.putExtra("currentUser", currentUser);
            intent.putExtra("currentCalendarIndex", currentCalendarIndex);
            intent.putExtra("currentSeriesIndex", currentSeriesIndex);
            startActivity(intent);
        }
    }

    /**
     * This supports the back button for the activity
     * @return boolean.
     */

    @Override
    public boolean onSupportNavigateUp() {
        Intent back = new Intent(this, ViewSeriesDetailsActivity.class);
        back.putExtra("currentUser", currentUser);
        back.putExtra("currentCalendarIndex", currentCalendarIndex);
        back.putExtra("currentSeriesIndex", currentSeriesIndex);
        startActivity(back);
        return true;
    }
}
