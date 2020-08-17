package com.example.phase2calendar;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phase2calendar.adapters.GenericAdapter;
import com.example.phase2calendar.logic.Calendar;
import com.example.phase2calendar.logic.Series;
import com.example.phase2calendar.logic.User;
import com.example.phase2calendar.logic.UserWriter;

import java.util.ArrayList;

public class ViewEventsInSeriesActivity extends AppCompatActivity {

    private User currentUser;
    private Calendar currentCalendar;
    private int currentCalendarIndex;
    private Series currentSeries;
    private int currentSeriesIndex;

    private RecyclerView recyclerView;
    private GenericAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events_in_series);

        setCurrentUser();
        setRecyclerView();
    }

    /**
     * Initializes the activity with user / calendar data
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
     * This view displays the current series' events
     */

    public void setRecyclerView() {
        this.recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new GenericAdapter((ArrayList) currentSeries.getEvents());

        recyclerView.setLayoutManager(this.layoutManager);
        recyclerView.setAdapter(this.adapter);
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
