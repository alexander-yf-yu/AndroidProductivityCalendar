package com.example.phase2calendar.logic;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class represents an alert object.
 */

public class Alert implements Serializable, Listable {

    private String description;
    private String name;
    private LocalDateTime time;

    public Alert(String description, String name, LocalDateTime time) {
        this.description = description;
        this.name = name;
        this.time = time;
    }

    public LocalDateTime getStartTime() {
        return time;
    }

    public void setStartTime(LocalDateTime startTime) { this.time = startTime; }

    public LocalDateTime getEndTime(){ return null; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

}