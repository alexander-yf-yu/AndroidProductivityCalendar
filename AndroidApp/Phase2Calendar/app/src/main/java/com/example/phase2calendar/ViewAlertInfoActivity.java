package com.example.phase2calendar;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.phase2calendar.dialogs.SingleAlertCreationDialog;
import com.example.phase2calendar.logic.*;

import java.time.LocalDateTime;

public class ViewAlertInfoActivity extends AppCompatActivity implements SingleAlertCreationDialog.SingleAlertCreationDialogListener {

    private User currentUser;
    private Calendar currentCalendar;
    private int currentCalendarIndex;
    private Event currentEvent;
    private int currentEventIndex;
    private Alert currentAlert;
    private int currentAlertIndex;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_alert_info);

        setCurrentUser();
        setAlertInfo();
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

        currentAlertIndex = (int) intent.getSerializableExtra("currentAlertIndex");
        currentAlert = currentEvent.getAllAlerts().get(currentAlertIndex);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setAlertInfo() {
        TextView titleView = findViewById(R.id.title_view);
        TextView startView = findViewById(R.id.start_view);
        TextView descriptionView = findViewById(R.id.description_view);

        DateFormatConverter converter = new DateFormatConverter();

        titleView.setText(currentEvent.getName());
        startView.setText("Starts: " + converter.convertLocalDateTime(currentAlert.getStartTime()));
        descriptionView.setText(currentEvent.getDescription());
    }

    /**
     * Set up and display alert creation dialog
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void openDialog(View view) {
        SingleAlertCreationDialog dialog = new SingleAlertCreationDialog();
        Bundle bundle = new Bundle();
        bundle.putString("title", currentAlert.getName());
        bundle.putString("description", currentAlert.getDescription());
        bundle.putSerializable("startTime", currentAlert.getStartTime());
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), "edit alert");
    }

    public void deleteAlert(View view) {
        Intent intent = new Intent(this, ManageAlertsActivity.class);
        currentUser.deleteAlert(currentEvent, currentAlert, currentCalendar);
        intent.putExtra("currentUser", currentUser);
        intent.putExtra("currentCalendarIndex", currentCalendarIndex);
        intent.putExtra("currentEventIndex", currentEventIndex);
        startActivity(intent);
    }

    @Override
    public void createSingleAlert(String title, String description, LocalDateTime start) {
        currentUser.editAlertInEventInCalendar(currentEvent, currentAlert, currentCalendar, title, description, start);
    }
}
