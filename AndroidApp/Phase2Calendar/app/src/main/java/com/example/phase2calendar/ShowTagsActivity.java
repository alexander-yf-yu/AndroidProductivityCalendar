package com.example.phase2calendar;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phase2calendar.adapters.GenericAdapter;
import com.example.phase2calendar.dialogs.TagCreationDialog;
import com.example.phase2calendar.logic.*;

import java.util.ArrayList;

public class ShowTagsActivity extends AppCompatActivity implements TagCreationDialog.TagCreationDialogListener {

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
        setContentView(R.layout.activity_show_tags);

        setCurrentUser();
        setRecyclerView();
    }

    /**
     * Set user / calendar specific fields
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
     * This recycler view shows the tags associated with the event
     */

    public void setRecyclerView() {
        this.recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new GenericAdapter((ArrayList) currentEvent.getAttachments());

        recyclerView.setLayoutManager(this.layoutManager);
        recyclerView.setAdapter(this.adapter);
    }

    public void openDialog(View view) {
        TagCreationDialog dialog = new TagCreationDialog();
        dialog.show(getSupportFragmentManager(), "tag creation dialog");
    }

    @Override
    public void createTag(String description) {
        Tag tag = new Tag(description);
        currentUser.addTagToEventInCalendar(currentEvent, tag, currentCalendar);
        adapter.notifyItemInserted(currentEvent.getAttachments().size() - 1);
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
