package com.example.phase2calendar.logic;

import java.io.Serializable;

public abstract class Attachment implements Serializable, Listable {
    // An attachment for the events

    private String description;

    public Attachment(String description){
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

}
