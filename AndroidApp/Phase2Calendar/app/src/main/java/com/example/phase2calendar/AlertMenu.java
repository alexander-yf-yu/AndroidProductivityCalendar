package com.example.phase2calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2calendar.logic.User;

public class AlertMenu extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_menu);

        Intent intent = getIntent();
        this.user = (User) intent.getSerializableExtra("currentUser");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * This supports the back button for the activity
     * @return boolean.
     */

    @Override
    public boolean onSupportNavigateUp() {
        Intent back = new Intent(this, MenuActivity.class);
        back.putExtra("currentUser", user);
        startActivity(back);
        return true;
    }

    /**
     * Takes the user to the alert creation activity
     */

    public void newAlert(View view)
    {
        Intent newAlert = new Intent(this, NewAlertActivity.class);
        newAlert.putExtra("currentUser", user);
        startActivity(newAlert);
    }
}
