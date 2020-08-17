package com.example.phase2calendar;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.phase2calendar.dialogs.EventCreationDialog;
import com.example.phase2calendar.dialogs.TagCreationDialog;
import com.example.phase2calendar.dialogs.ViewMemoDialog;
import com.example.phase2calendar.logic.*;

import java.time.LocalDateTime;

public class ViewEventDetailsActivity extends AppCompatActivity implements EventCreationDialog.EventCreationDialogListener {

    private User currentUser;
    private Calendar currentCalendar;
    private int currentCalendarIndex;
    private Event currentEvent;
    private int currentEventIndex;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_details);

        setCurrentUser();
        setEventInfo();
    }

    private void setCurrentUser() {
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        currentUser.setContext(getApplicationContext());
        UserWriter userWriter = new UserWriter();
        currentUser.addObserver(userWriter);

        currentCalendarIndex = (int) intent.getSerializableExtra("currentCalendarIndex");
        currentCalendar = currentUser.getCalendar(currentCalendarIndex);

        currentEventIndex = (int) intent.getSerializableExtra("currentEventIndex");
        currentEvent = currentCalendar.getEvents().get(currentEventIndex);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setEventInfo() {
        TextView titleView = findViewById(R.id.title_view);
        TextView startView = findViewById(R.id.start_view);
        TextView endView = findViewById(R.id.end_view);
        TextView descriptionView = findViewById(R.id.description_view);

        DateFormatConverter converter = new DateFormatConverter();

        titleView.setText(currentEvent.getName());
        startView.setText("Starts: " + converter.convertLocalDateTime(currentEvent.getStartTime()));
        endView.setText("Ends: " + converter.convertLocalDateTime(currentEvent.getEndTime()));
        descriptionView.setText(currentEvent.getDescription());
    }

    /**
     * Set up and display event creation
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openDialog(View view) {
        EventCreationDialog dialog = new EventCreationDialog();
        Bundle args = new Bundle();
        args.putString("title", currentEvent.getName());
        args.putString("description", currentEvent.getDescription());
        args.putSerializable("startTime", currentEvent.getStartTime());
        args.putSerializable("endTime", currentEvent.getEndTime());
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "event creation dialog");
    }

    /**
     * Displays memo if event has one
     */

    public void openMemoDialog(View view){
        if(currentEvent.getMemo() == null){
            Toast.makeText(getApplicationContext(), "This event has no associated memo", Toast.LENGTH_SHORT).show();
        } else {
            ViewMemoDialog dialog = new ViewMemoDialog();
            Bundle args = new Bundle();
            args.putString("title", currentEvent.getMemo().getName());
            args.putString("description", currentEvent.getMemo().getDescription());
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(), "show memo");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void createEvent(String title, String description, LocalDateTime startTime, LocalDateTime endTime) {
        currentUser.editEventInCalendar(currentCalendar, currentEvent, title, description, startTime, endTime);
        setEventInfo();
    }

    public void deleteEvent(View view) {
        currentUser.removeEventFromCalendar(currentEvent, currentCalendar);
        Intent intent = new Intent(this, EventMenuActivity.class);
        intent.putExtra("currentUser", currentUser);
        intent.putExtra("currentCalendarIndex", currentCalendarIndex);
        startActivity(intent);
    }

    public void openShowTags(View view) {
        Intent intent = new Intent(this, ShowTagsActivity.class);
        intent.putExtra("currentUser", currentUser);
        intent.putExtra("currentCalendarIndex", currentCalendarIndex);
        intent.putExtra("currentEventIndex", currentEventIndex);
        startActivity(intent);
    }

    public void openManageAlerts(View view) {
        Intent intent = new Intent(this, ManageAlertsActivity.class);
        intent.putExtra("currentUser", currentUser);
        intent.putExtra("currentCalendarIndex", currentCalendarIndex);
        intent.putExtra("currentEventIndex", currentEventIndex);
        startActivity(intent);
    }

    /**
     * This supports the back button for the activity
     * @return boolean.
     */

    @Override
    public boolean onSupportNavigateUp() {
        Intent back = new Intent(this, EventMenuActivity.class);
        back.putExtra("currentUser", currentUser);
        back.putExtra("currentCalendarIndex", currentCalendarIndex);
        startActivity(back);
        return true;
    }

}
