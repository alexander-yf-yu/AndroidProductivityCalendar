package com.example.phase2calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.phase2calendar.adapters.GenericAdapter;
import com.example.phase2calendar.dialogs.EventCreationDialog;
import com.example.phase2calendar.dialogs.MemoCreationDialog;
import com.example.phase2calendar.logic.Calendar;
import com.example.phase2calendar.logic.Event;
import com.example.phase2calendar.logic.Memo;
import com.example.phase2calendar.logic.User;
import com.example.phase2calendar.logic.UserWriter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MemoListActivity extends AppCompatActivity implements MemoCreationDialog.MemoCreationDialogListener{

    private RecyclerView recyclerView;
    private GenericAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private User currentUser;
    private Calendar currentCalendar;
    private int currentCalendarIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);

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
    }

    private void setRecyclerView() {
        this.recyclerView = findViewById(R.id.recyclerViewMemo);
        recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new GenericAdapter((ArrayList) currentUser.getMemosFromCalendar(currentCalendar));

        recyclerView.setLayoutManager(this.layoutManager);
        recyclerView.setAdapter(this.adapter);

        adapter.setOnClickListener(new GenericAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                Intent newIntent = new Intent(MemoListActivity.this, ViewMemoDetailsActivity.class);
                newIntent.putExtra("currentCalendarIndex", currentCalendarIndex);
                newIntent.putExtra("currentUser", currentUser);
                newIntent.putExtra("currentMemoIndex", i);
                startActivity(newIntent);
            }
        });
    }

    /**
     * sets up and displays memo creation dialogue
     */

    public void openDialog(View view) {
        MemoCreationDialog dialog = new MemoCreationDialog();
        Bundle args = new Bundle();
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "memo creation dialog");
    }

    /**
     * This creates a new memo from a title and description and updates memo list in recycler
     * @param description memo description
     * @param title memo title
     */

    @Override
    public void createMemo(String title, String description) {
        Memo memo = new Memo(title, description);
        currentUser.addMemoToCalendar(memo, currentCalendar);
        adapter.notifyItemInserted(currentUser.getMemosFromCalendar(currentCalendar).size()-1);
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
