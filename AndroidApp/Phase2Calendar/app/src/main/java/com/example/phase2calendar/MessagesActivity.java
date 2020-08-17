package com.example.phase2calendar;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phase2calendar.adapters.MessageAdapter;
import com.example.phase2calendar.dialogs.CalendarCreationDialog;
import com.example.phase2calendar.logic.Message;
import com.example.phase2calendar.logic.User;
import com.example.phase2calendar.logic.UserWriter;

public class MessagesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        currentUser.setContext(getApplicationContext());
        UserWriter userWriter = new UserWriter();
        currentUser.addObserver(userWriter);

        this.recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.adapter = new MessageAdapter(currentUser.getMessagesList());

        recyclerView.setLayoutManager(this.layoutManager);
        recyclerView.setAdapter(this.adapter);

        adapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int i) {
                currentUser.deleteMessage(currentUser.getMessagesList().get(i));
                adapter.notifyItemRemoved(i);
            }

            @Override
            public void onReplyClick(int i) {
                Intent intent = new Intent(MessagesActivity.this, ComposeMessageActivity.class);
                intent.putExtra("currentUser", currentUser);
                intent.putExtra("recipient", currentUser.getMessagesList().get(i).getSender().getUsername());
                startActivity(intent);
            }

            @Override
            public void onShowEventClick(int i) {
                Intent intent = new Intent(MessagesActivity.this, ViewEventFromMessageActivity.class);
                intent.putExtra("currentUser", currentUser);
                intent.putExtra("currentEvent", currentUser.getMessagesList().get(i).getEvent());
                intent.putExtra("senderName", currentUser.getMessagesList().get(i).getSender().getUsername());
                startActivity(intent);
            }
        });
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

    public void composeScreen(View view) {
        Intent intent = new Intent(this, ComposeMessageActivity.class);
        intent.putExtra("currentUser", currentUser);
        intent.putExtra("recipient", "");
        startActivity(intent);
    }
}
