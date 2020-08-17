package com.example.phase2calendar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phase2calendar.adapters.HighlightAdapter;
import com.example.phase2calendar.logic.Calendar;
import com.example.phase2calendar.logic.Event;
import com.example.phase2calendar.logic.User;
import com.example.phase2calendar.logic.UserWriter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class SelectCalendarToAddEventActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HighlightAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    
    private User currentUser;
    private Event currentEvent;
    private String senderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_calendar_to_add_event);

        Intent intent = getIntent();
        currentEvent = (Event) intent.getSerializableExtra("currentEvent");
        currentUser = (User) intent.getSerializableExtra("currentUser");
        senderName = intent.getStringExtra("senderName");
        currentUser.setContext(getApplicationContext());
        UserWriter userWriter = new UserWriter();
        currentUser.addObserver(userWriter);

        this.recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new HighlightAdapter((ArrayList) currentUser.getCalendarsList());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * This supports the event creation button with logic
     */

    public void addEvent(View view) {
        Calendar selectedCalendar = (Calendar) adapter.getSelectedItem();

        if(selectedCalendar != null){
            currentUser.addEventToCalendar(currentEvent, selectedCalendar);

            Intent intent = new Intent(this, ViewEventFromMessageActivity.class);
            intent.putExtra("currentUser", currentUser);
            intent.putExtra("currentEvent", currentEvent);
            intent.putExtra("senderName", senderName);
            Toast.makeText(getApplicationContext(), "Event added!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Please select a calendar", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This supports the back button for the activity
     * @return boolean.
     */

    @Override
    public boolean onSupportNavigateUp() {
        Intent back = new Intent(this, ViewEventFromMessageActivity.class);
        back.putExtra("currentUser", currentUser);
        back.putExtra("currentEvent", currentEvent);
        back.putExtra("senderName", senderName);
        startActivity(back);
        return true;
    }

}
