package com.example.phase2calendar;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phase2calendar.adapters.GenericAdapter;
import com.example.phase2calendar.adapters.HighlightAdapter;
import com.example.phase2calendar.logic.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ComposeMessageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HighlightAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        currentUser.setContext(getApplicationContext());
        UserWriter userWriter = new UserWriter();
        currentUser.addObserver(userWriter);

        String fill = intent.getStringExtra("recipient");
        EditText editRecipient = findViewById(R.id.recipient_edit);
        editRecipient.setText(fill);

        this.recyclerView = findViewById(R.id.select_event);
        recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new HighlightAdapter((ArrayList) currentUser.getAllEvents());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    /**
     * Supports send message button with logic,
     * and takes user to messages activity if message send is valid
     */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void send(View view) {
        EditText editRecipient = findViewById(R.id.recipient_edit);
        EditText editContent = findViewById(R.id.content_edit);
        String username = editRecipient.getText().toString();
        String content = editContent.getText().toString();

        File userFile = new File(getApplicationContext().getFilesDir(), username + ".txt");

        LoginValidator val = new LoginValidator();
        User recipient = val.instantiateUser(userFile);

        if(recipient != null) {
            recipient.setContext(getApplicationContext());
            UserWriter userWriter = new UserWriter();
            recipient.addObserver(userWriter);

            if (adapter.getSelectedItem() != null) {
                recipient.addMessage(new Message(content, (Event) adapter.getSelectedItem(), currentUser, recipient, LocalDateTime.now()));
            } else {
                recipient.addMessage(new Message(content, currentUser, recipient, LocalDateTime.now()));
            }

            Intent intent = new Intent(this, MessagesActivity.class);
            intent.putExtra("currentUser", currentUser);
            Toast.makeText(getApplicationContext(), "Message sent!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else if (recipient.getUsername().equals(currentUser.getUsername())){
            Toast.makeText(getApplicationContext(), "You cannot send a message to yourself", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "That is not a valid username", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This supports the back button for the activity
     * @return boolean.
     */

    @Override
    public boolean onSupportNavigateUp() {
        Intent back = new Intent(this, MessagesActivity.class);
        back.putExtra("currentUser", currentUser);
        startActivity(back);
        return true;
    }
}
