package com.example.phase2calendar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RegisteredActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        Button homeBtn = findViewById(R.id.home);
        homeBtn.setOnClickListener(v -> {
            Intent goHome = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(goHome);
        });

    }
}
