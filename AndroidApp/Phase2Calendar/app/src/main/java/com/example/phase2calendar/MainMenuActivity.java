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
import com.example.phase2calendar.adapters.MessageAdapter;
import com.example.phase2calendar.dialogs.CalendarCreationDialog;
import com.example.phase2calendar.logic.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity implements CalendarCreationDialog.CalendarCreationDialogListener {

    private RecyclerView recyclerView;
    private GenericAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private User currentUser;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setCurrentUser();
        setRecyclerView();
    }

    /**
     * Initializes activity with user info
     *
     * Userwriter is used to serialize user info
     */

    public void setCurrentUser() {
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        currentUser.setContext(getApplicationContext());
        UserWriter userWriter = new UserWriter();
        currentUser.addObserver(userWriter);
    }

    /**
     * This default view shows the list of all calendars
     */

    public void setRecyclerView() {
        this.recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new GenericAdapter((ArrayList) currentUser.getCalendarsList());

        recyclerView.setLayoutManager(this.layoutManager);
        recyclerView.setAdapter(this.adapter);

        adapter.setOnClickListener(new GenericAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                Intent newIntent = new Intent(MainMenuActivity.this, MenuActivity.class);
                newIntent.putExtra("currentCalendarIndex", i);
                newIntent.putExtra("currentUser", currentUser);
                startActivity(newIntent);
            }
        });
    }

    /**
     * show dialogue for calendar creation
     */


    public void openDialog(View view) {
        CalendarCreationDialog dialog = new CalendarCreationDialog();
        dialog.show(getSupportFragmentManager(), "calendar creation dialog");
    }

    /**
     * Make a new calendar, update the current calendar list
     */

    @Override
    public void createCalendar(String title, String description) {
        Calendar cal = new Calendar(title, description);
        currentUser.addCalendar(cal);
        adapter.notifyItemInserted(currentUser.getCalendarsList().size()-1);
    }

    /**
     * This supports the back button for the activity
     * @return boolean.
     */

    @Override
    public boolean onSupportNavigateUp() {
        Intent back = new Intent(this, UserMenuActivity.class);
        back.putExtra("currentUser", currentUser);
        startActivity(back);
        return true;
    }
}
