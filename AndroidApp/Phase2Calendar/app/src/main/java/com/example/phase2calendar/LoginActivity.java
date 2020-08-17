package com.example.phase2calendar;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.phase2calendar.logic.LoginValidator;
import com.example.phase2calendar.logic.User;
import com.example.phase2calendar.logic.UserWriter;

import java.util.ArrayList;

/**
 * This activity is where you are taken after you login:
 *
 * You're login is either successful, in which case you'd go back
 *
 * or
 *
 * You're taken to the main menu (Usermenuactivity)
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * Provides logic for button for login,
     * Sends user to usermenuactivity iff login successful
     */

    public void loginUser(View view) {
        EditText usernameField = findViewById(R.id.editText);
        EditText passwordField = findViewById(R.id.editText2);
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        LoginValidator loginValidator = new LoginValidator();
        User currentUser = loginValidator.validate(username, password, getApplicationContext());

        if(currentUser == null){
            // Incorrect login credentials
            Toast.makeText(getApplicationContext(), "Your login credentials are incorrect", Toast.LENGTH_SHORT).show();
        } else {
            // Successful login
            currentUser.setContext(getApplicationContext());
            Intent intent = new Intent(this, UserMenuActivity.class);
            intent.putExtra("currentUser", currentUser);
            startActivity(intent);
        }
    }
}
