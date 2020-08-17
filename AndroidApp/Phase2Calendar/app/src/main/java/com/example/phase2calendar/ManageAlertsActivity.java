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
import com.example.phase2calendar.dialogs.MultipleAlertCreationDialog;
import com.example.phase2calendar.dialogs.SingleAlertCreationDialog;
import com.example.phase2calendar.logic.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ManageAlertsActivity extends AppCompatActivity implements SingleAlertCreationDialog.SingleAlertCreationDialogListener, MultipleAlertCreationDialog.MultipleAlertCreationDialogListener {

    private RecyclerView recyclerView;
    private GenericAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private User currentUser;
    private Calendar currentCalendar;
    private int currentCalendarIndex;
    private Event currentEvent;
    private int currentEventIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_alerts);

        setCurrentUser();
        setRecyclerView();
    }

    /**
     * Initialize activity with user info
     */

    public void setCurrentUser() {
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        currentUser.setContext(getApplicationContext());
        UserWriter userWriter = new UserWriter();
        currentUser.addObserver(userWriter);

        currentCalendarIndex = intent.getIntExtra("currentCalendarIndex", -1);
        currentEventIndex = intent.getIntExtra("currentEventIndex", -1);

        currentCalendar = currentUser.getCalendar(currentCalendarIndex);
        currentEvent = currentCalendar.getEvents().get(currentEventIndex);
    }

    /**
     * This default view shows all of the current event's alerts
     */

    public void setRecyclerView() {
        this.recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new GenericAdapter((ArrayList) currentEvent.getAllAlerts());

        recyclerView.setLayoutManager(this.layoutManager);
        recyclerView.setAdapter(this.adapter);

        adapter.setOnClickListener(new GenericAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                Intent newIntent = new Intent(ManageAlertsActivity.this, ViewAlertInfoActivity.class);
                newIntent.putExtra("currentCalendarIndex", currentCalendarIndex);
                newIntent.putExtra("currentUser", currentUser);
                newIntent.putExtra("currentEventIndex", currentEventIndex);
                newIntent.putExtra("currentAlertIndex", i);
                startActivity(newIntent);
            }
        });
    }

    /**
     * Sets up and display pop up for making a single alert
     */

    public void openSingleAlertDialog(View view) {
        SingleAlertCreationDialog dialog = new SingleAlertCreationDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title", null);
        bundle.putString("description", null);
        bundle.putString("startTime", null);
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "single alert creation");
    }

    /**
     * Sets up and display pop up for making a sequence of alerts
     */

    public void openManyAlertsDialog(View view) {
        MultipleAlertCreationDialog dialog = new MultipleAlertCreationDialog();
        dialog.show(getSupportFragmentManager(), "multiple alert creation");
    }

    /**
     *  Create a sequence of alerts, stored in field inputs
     *  Add these new alerts to calendar, and indicate to adapter changes
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void createAlerts(String title, String description, LocalDateTime start, int days, int hours, int minutes) {
        ArrayList<Triple<String, String, LocalDateTime>> inputs = new ArrayList<>();
        while(start.isBefore(currentEvent.getStartTime())) {
            inputs.add(new Triple<>(title, description, start));
            start = start.plusDays(days).plusHours(hours).plusMinutes(minutes);
        }
        currentUser.addAlertsToEventInCalendar(currentEvent, inputs, currentCalendar);
        adapter.notifyDataSetChanged();
    }

    /**
     * Passes responsibility down to calendar and updates alert list
     */

    @Override
    public void createSingleAlert(String title, String description, LocalDateTime start) {
        currentUser.addAlertToEventInCalendar(currentEvent, new Triple<>(title, description, start), currentCalendar);
        adapter.notifyItemInserted(currentEvent.getAllAlerts().size() - 1);
    }

    /**
     * This supports the back button for the activity
     * @return boolean.
     */

    @Override
    public boolean onSupportNavigateUp() {
        Intent back = new Intent(this, ViewEventDetailsActivity.class);
        back.putExtra("currentUser", currentUser);
        back.putExtra("currentCalendarIndex", currentCalendarIndex);
        back.putExtra("currentEventIndex", currentEventIndex);
        startActivity(back);
        return true;
    }
}
