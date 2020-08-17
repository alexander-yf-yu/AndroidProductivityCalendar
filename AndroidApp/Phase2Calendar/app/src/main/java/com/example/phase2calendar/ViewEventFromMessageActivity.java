package com.example.phase2calendar;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.phase2calendar.logic.DateFormatConverter;
import com.example.phase2calendar.logic.Event;
import com.example.phase2calendar.logic.User;
import com.example.phase2calendar.logic.UserWriter;

import java.text.DateFormat;

public class ViewEventFromMessageActivity extends AppCompatActivity {

    private Event currentEvent;
    private User currentUser;
    private String senderName;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_from_message);

        Intent intent = getIntent();
        currentEvent = (Event) intent.getSerializableExtra("currentEvent");
        currentUser = (User) intent.getSerializableExtra("currentUser");
        senderName = intent.getStringExtra("senderName");
        currentUser.setContext(getApplicationContext());
        UserWriter userWriter = new UserWriter();
        currentUser.addObserver(userWriter);

        TextView senderView = findViewById(R.id.sender_view);
        TextView titleView = findViewById(R.id.title_view);
        TextView startView = findViewById(R.id.start_view);
        TextView endView = findViewById(R.id.end_view);
        TextView descriptionView = findViewById(R.id.description_view);

        DateFormatConverter dateFormatConverter = new DateFormatConverter();

        senderView.setText("Event from: " + senderName);
        titleView.setText(currentEvent.getName());
        startView.setText("Starts: " + dateFormatConverter.convertLocalDateTime(currentEvent.getStartTime()));
        endView.setText("Ends: " + dateFormatConverter.convertLocalDateTime(currentEvent.getEndTime()));
        descriptionView.setText(currentEvent.getDescription());
    }


    public void chooseCalendar(View view) {
        Intent intent = new Intent(this, SelectCalendarToAddEventActivity.class);
        intent.putExtra("currentUser", currentUser);
        intent.putExtra("currentEvent", currentEvent);
        intent.putExtra("senderName", senderName);
        startActivity(intent);
    }

    /**
     * This supports the back button for the activity
     * @return boolean.
     */

    @Override
    public boolean onSupportNavigateUp() {
        Intent back = new Intent(this, MessagesActivity.class);
        back.putExtra("currentUser", currentUser);
        startActivity(back);
        return true;
    }
}
