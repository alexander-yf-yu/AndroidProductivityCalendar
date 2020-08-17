package com.example.phase2calendar;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phase2calendar.adapters.GenericAdapter;
import com.example.phase2calendar.dialogs.DayGapSeriesCreationDialog;
import com.example.phase2calendar.dialogs.EmptySeriesCreationDialog;
import com.example.phase2calendar.logic.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class SeriesListActivity extends AppCompatActivity implements EmptySeriesCreationDialog.EmptySeriesCreationDialogListener, DayGapSeriesCreationDialog.DayGapSeriesCreationDialogListener {

    private User currentUser;
    private int currentCalendarIndex;
    private Calendar currentCalendar;

    private RecyclerView recyclerView;
    private GenericAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_list);

        setCurrentUser();
        setRecyclerView();
    }

    /**
     * Initializes field with current user info
     */

    private void setCurrentUser() {
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        currentUser.setContext(getApplicationContext());
        UserWriter userWriter = new UserWriter();
        currentUser.addObserver(userWriter);
        currentCalendarIndex = (int) intent.getSerializableExtra("currentCalendarIndex");
        currentCalendar = currentUser.getCalendar(currentCalendarIndex);
    }

    /**
     * This view shows all series
     */

    private void setRecyclerView() {
        this.recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new GenericAdapter((ArrayList) currentUser.getSeriesFromCalendar(currentCalendar));

        recyclerView.setLayoutManager(this.layoutManager);
        recyclerView.setAdapter(this.adapter);

        adapter.setOnClickListener(new GenericAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                Intent newIntent = new Intent(SeriesListActivity.this, ViewSeriesDetailsActivity.class);
                newIntent.putExtra("currentCalendarIndex", currentCalendarIndex);
                newIntent.putExtra("currentUser", currentUser);
                newIntent.putExtra("currentSeriesIndex", i);
                startActivity(newIntent);
            }
        });
    }

    public void openEmptySeriesCreationDialog(View view) {
        EmptySeriesCreationDialog dialog = new EmptySeriesCreationDialog();
        dialog.show(getSupportFragmentManager(), "new empty series");
    }

    public void openDayGapSeriesCreationDialog(View view) {
        DayGapSeriesCreationDialog dialog = new DayGapSeriesCreationDialog();
        dialog.show(getSupportFragmentManager(), "new day gap series");
    }

    @Override
    public void createEmptySeries(String title, String description) {
        SeriesFactory factory = new SeriesFactory();
        Series series = factory.createEmptySeries(title, description);
        currentUser.addSeriesToCalendar(series, currentCalendar);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void createDayGapSeries(String title, String description, LocalDateTime start, LocalTime end, int days, int numEvents) {
        SeriesFactory factory = new SeriesFactory();
        Series series = factory.createDayGapSeries(title, description, start, end, days, numEvents);
        currentUser.addSeriesToCalendar(series, currentCalendar);
    }

    /**
     * This supports the back button for the activity
     * @return boolean.
     */


    @Override
    public boolean onSupportNavigateUp() {
        Intent back = new Intent(this, MenuActivity.class);
        back.putExtra("currentUser", currentUser);
        back.putExtra("currentCalendarIndex", currentCalendarIndex);
        startActivity(back);
        return true;
    }
}
