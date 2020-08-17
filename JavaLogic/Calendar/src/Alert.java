package src;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Alert implements Serializable {

    private String description;
    private String name;
    private LocalDateTime time;

    public Alert(String description, String name, LocalDateTime time) {
        this.description = description;
        this.name = name;
        this.time = time;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

}