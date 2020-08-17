package com.example.phase2calendar.logic;

import android.content.Context;

import java.util.Observable;
import java.util.Observer;
import java.io.*;

public class UserWriter implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        User user = (User) o;
        Context context = (Context) arg;
        writeUser(user, context);
    }

    public void writeUser(User user, Context context) {
        String filename = user.getUsername().toLowerCase() + ".txt";

        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
            objectOutputStream.writeObject(user);
            objectOutputStream.close();
            fos.close();
        } catch (IOException e) {
            System.out.println("Something wrong with the new user, please check the location of file.");
            e.printStackTrace();
        }
    }
}
