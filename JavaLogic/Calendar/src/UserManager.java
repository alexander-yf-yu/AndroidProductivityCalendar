package src;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UserManager {

    UserManager() {
    }

    public Object[] createUser(String username, String password) throws IOException {
        User user = new User(username, password);
        Object[] results = new Object[2];
        results[0] = user;
        results[1] = createUserFile(user);
        return results;
    }

    public boolean createUserFile(User user) throws IOException {
        File userFile = new File("Users/" + user.getUsername().toLowerCase() + ".txt");
        boolean success = userFile.createNewFile();
        if (success) {
            UserWriter writer = new UserWriter();
            writer.writeUser(user);
        }
        return success;
    }

    public boolean deleteUser(String username) throws IOException {
        username = username.toLowerCase();
        File userFile = new File("Users/" + username + ".txt");
        return userFile.delete();
    }
}
