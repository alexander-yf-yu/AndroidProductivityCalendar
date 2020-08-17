package com.example.phase2calendar;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.phase2calendar.logic.*;

public class ViewSeriesDetailsActivity extends AppCompatActivity {

    private User currentUser;
    private Calendar currentCalendar;
    private int currentCalendarIndex;
    private Series currentSeries;
    private int currentSeriesIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_series_details);

        setCurrentUser();
        setSeriesInfo();
    }

    private void setCurrentUser() {
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

    private void setSeriesInfo() {
        TextView titleView = findViewById(R.id.title_view);
        TextView descriptionView = findViewById(R.id.description_view);

        titleView.setText(currentSeries.getName());
        descriptionView.setText(currentSeries.getDescription());
    }

    public void openAddEvent(View view) {
        Intent intent = new Intent(this, AddEventToSeriesActivity.class);
        intent.putExtra("currentUser", currentUser);
        intent.putExtra("currentCalendarIndex", currentCalendarIndex);
        intent.putExtra("currentSeriesIndex", currentSeriesIndex);
        startActivity(intent);
    }

    public void openViewEvents(View view) {
        Intent intent = new Intent(this, ViewEventsInSeriesActivity.class);
        intent.putExtra("currentUser", currentUser);
        intent.putExtra("currentCalendarIndex", currentCalendarIndex);
        intent.putExtra("currentSeriesIndex", currentSeriesIndex);
        startActivity(intent);
    }

    /**
     * This supports the back button for the activity
     * @return boolean.
     */

    @Override
    public boolean onSupportNavigateUp() {
        Intent back = new Intent(this, SeriesListActivity.class);
        back.putExtra("currentUser", currentUser);
        back.putExtra("currentCalendarIndex", currentCalendarIndex);
        startActivity(back);
        return true;
    }
}
