package com.example.phase2calendar;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This activity is the first thing the user sees
 *
 * From here you can:
 *
 * Login or register
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Launch login activity
     */

    public void loginScreen(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Launch register activity
     */

    public void registerScreen(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
