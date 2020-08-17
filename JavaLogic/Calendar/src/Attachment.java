package src;

import java.io.Serializable;

public abstract class Attachment implements Serializable {
    // An attachment for the events

    private String description;

    public Attachment(String description){
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

}
