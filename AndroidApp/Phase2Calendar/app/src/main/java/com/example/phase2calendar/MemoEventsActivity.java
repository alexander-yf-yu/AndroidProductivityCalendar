package com.example.phase2calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.phase2calendar.adapters.GenericAdapter;
import com.example.phase2calendar.logic.AttachmentSearcher;
import com.example.phase2calendar.logic.Calendar;
import com.example.phase2calendar.logic.Event;
import com.example.phase2calendar.logic.Memo;
import com.example.phase2calendar.logic.User;
import com.example.phase2calendar.logic.UserWriter;

import java.util.ArrayList;

public class MemoEventsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GenericAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private User currentUser;
    private Calendar currentCalendar;
    private Memo currentMemo;
    private int currentCalendarIndex;
    private int currentMemoIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_events);

        setCurrentUser();
        setRecyclerView();
    }

    private void setCurrentUser() {
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        currentUser.setContext(getApplicationContext());
        UserWriter userWriter = new UserWriter();
        currentUser.addObserver(userWriter);
        currentCalendarIndex = (int) intent.getSerializableExtra("currentCalendarIndex");
        currentCalendar = currentUser.getCalendar(currentCalendarIndex);
        currentMemoIndex = (int) intent.getSerializableExtra("currentMemoIndex");
        currentMemo = currentCalendar.getMemos().get(currentMemoIndex);
    }

    /**
     * This default view shows all the events that have the memo from currentMemo
     */

    private void setRecyclerView() {
        this.recyclerView = findViewById(R.id.recyclerView_memoEvents);
        recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        AttachmentSearcher searcher2 = new AttachmentSearcher();
        ArrayList memoEvents = searcher2.searchByMemo(currentCalendar.getEvents(), currentMemo);
        this.adapter = new GenericAdapter(memoEvents);

        recyclerView.setLayoutManager(this.layoutManager);
        recyclerView.setAdapter(this.adapter);

        adapter.setOnClickListener(i -> {
            Intent newIntent = new Intent(MemoEventsActivity.this, ViewEventDetailsActivity.class);
            newIntent.putExtra("currentCalendarIndex", currentCalendarIndex);
            newIntent.putExtra("currentUser", currentUser);
            newIntent.putExtra("currentEventIndex", i);
            startActivity(newIntent);
        });
    }

    public boolean onSupportNavigateUp() {
        Intent back = new Intent(this, ViewMemoDetailsActivity.class);
        back.putExtra("currentUser", currentUser);
        back.putExtra("currentCalendarIndex", currentCalendarIndex);
        back.putExtra("currentMemoIndex", currentMemoIndex);
        startActivity(back);
        return true;
    }

}
