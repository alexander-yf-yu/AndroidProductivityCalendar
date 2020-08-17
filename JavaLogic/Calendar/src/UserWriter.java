package src;

import java.util.Observable;
import java.util.Observer;
import java.io.*;

public class UserWriter implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        User user = (User) o;
        writeUser(user);
    }

    public void writeUser(User user) {
        File file = new File("Users/" + user.getUsername().toLowerCase() + ".txt");

        try {
            file.createNewFile();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file, false));
            objectOutputStream.writeObject(user);
            objectOutputStream.close();
        } catch (IOException e) {
            System.out.println("Something wrong with the new user, please check the location of file.");
            e.printStackTrace();
        }
    }
}
