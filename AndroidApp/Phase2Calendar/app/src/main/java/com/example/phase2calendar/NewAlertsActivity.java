package com.example.phase2calendar;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phase2calendar.adapters.GenericAdapter;
import com.example.phase2calendar.logic.Alert;
import com.example.phase2calendar.logic.Calendar;
import com.example.phase2calendar.logic.User;
import com.example.phase2calendar.logic.UserWriter;

import java.util.ArrayList;

public class NewAlertsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GenericAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private User currentUser;
    private ArrayList<Alert> alerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_alerts);

        setCurrentUser();
        setRecyclerView();
    }

    /**
     * Initialize user info
     */


    private void setCurrentUser() {
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        currentUser.setContext(getApplicationContext());
        UserWriter userWriter = new UserWriter();
        currentUser.addObserver(userWriter);
        alerts = (ArrayList<Alert>) intent.getSerializableExtra("newAlerts");
    }

    /**
     * This recycler view shows this events' alerts
     */

    private void setRecyclerView() {
        this.recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new GenericAdapter((ArrayList) alerts);

        recyclerView.setLayoutManager(this.layoutManager);
        recyclerView.setAdapter(this.adapter);
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
