package com.example.phase2calendar.logic;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UserManager {

    public UserManager() {
    }

    public Object[] createUser(String username, String password, Context context) throws IOException {
        User user = new User(username, password);
        Object[] results = new Object[2];
        results[0] = user;
        results[1] = createUserFile(user, context);
        return results;
    }

    public boolean createUserFile(User user, Context context) throws IOException {
        File userFile = new File(context.getFilesDir(), user.getUsername().toLowerCase() + ".txt");
        boolean success = !userFile.exists();
        if (success) {
            UserWriter writer = new UserWriter();
            writer.writeUser(user, context);
        }
        return success;
    }

    public boolean deleteUser(String username, Context context) throws IOException {
        username = username.toLowerCase();
        File userFile = new File(context.getFilesDir(), username + ".txt");
        return userFile.delete();
    }
}
