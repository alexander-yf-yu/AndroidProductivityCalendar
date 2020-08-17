package com.example.phase2calendar;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.phase2calendar.logic.Alert;
import com.example.phase2calendar.logic.User;
import com.example.phase2calendar.logic.UserWriter;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This class represents the menu after login
 *
 * Here you see you're username and can:
 *
 * view new alerts
 * Look at messages
 * checkout your calendars
 */

public class UserMenuActivity extends AppCompatActivity {

    private User currentUser;
    private ArrayList<Alert> alerts;
    private boolean refresher;
    Button alertsButton;
    TextView title;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        currentUser.setContext(getApplicationContext());
        UserWriter userWriter = new UserWriter();
        currentUser.addObserver(userWriter);

        this.title = findViewById(R.id.welcome_view);
        this.title.setText("Welcome, " + currentUser.getUsername() + "!");

        alertsButton = findViewById(R.id.alerts_button);
        refresher = true;
        setContent();
    }

    public void openMainMenu(View view){
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("currentUser", currentUser);
        refresher = false;
        startActivity(intent);
    }

    public void showMessages(View view){
        Intent intent = new Intent(this, MessagesActivity.class);
        intent.putExtra("currentUser", currentUser);
        refresher = false;
        startActivity(intent);
    }

    public void showAlerts(View view){
        Intent intent = new Intent(this, NewAlertsActivity.class);
        intent.putExtra("currentUser", currentUser);
        intent.putExtra("newAlerts", alerts);
        refresher = false;
        startActivity(intent);
    }

    /**
     * update time and trigger alerts if any
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setContent() {
        LocalDateTime now = LocalDateTime.now();
        alerts = currentUser.raiseAllAlerts(now);
        alertsButton.setText(alerts.size() + " New Alerts!");
        int holiday = currentUser.isHoliday(now);
        if (holiday >= 0) {
            title.setText(currentUser.getHoliday(holiday));
        }
        if (refresher){
            refresh(10000);
        }
    }

    private void refresh(int milliseconds) {
        final Handler handler = new Handler();

        final Runnable runnable = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                setContent();
            }
        };
        handler.postDelayed(runnable, milliseconds);
    }
}
