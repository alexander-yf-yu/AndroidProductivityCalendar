package com.example.phase2calendar;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.phase2calendar.logic.User;
import com.example.phase2calendar.logic.UserManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This activity is where you're taken to register
 */

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    /**
     * Supports register logic for button
     * Validates username / password combo
     *
     * iff
     *
     * password is confirmed
     * username and password are not empty
     * the username is unused and does not contain special chars
     */

    public void registerUser(View view) throws IOException {
        UserManager userManager = new UserManager();
        String pattern = "^[A-Za-z0-9]+$";
        EditText usernameField = (EditText) findViewById(R.id.editText);
        EditText passwordField = (EditText) findViewById(R.id.editText2);
        EditText confirmPasswordField = (EditText) findViewById(R.id.editText3);
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();

        if(username.length() == 0 || password.length() == 0){
            Toast.makeText(getApplicationContext(), "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if(!username.matches(pattern)){
            Toast.makeText(getApplicationContext(), "Username must only contain letters and numbers", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(confirmPassword)){
            Toast.makeText(getApplicationContext(), "Passwords must match", Toast.LENGTH_SHORT).show();
        } else {
            Object[] res = userManager.createUser(username, password, getApplicationContext());
            if(!(boolean) res[1]){
                Toast.makeText(getApplicationContext(), "This username is already in use", Toast.LENGTH_SHORT).show();
            } else {
                User currentUser = (User) res[0];
                currentUser.setContext(getApplicationContext());
                Intent intent = new Intent(this, RegisteredActivity.class);
                intent.putExtra("currentUser", currentUser);
                startActivity(intent);
            }
        }
    }
}
