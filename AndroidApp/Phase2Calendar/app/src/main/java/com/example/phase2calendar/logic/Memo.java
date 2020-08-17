package com.example.phase2calendar.logic;

import java.time.LocalDateTime;

public class Memo extends Attachment {
    //src.Memo class that stores a memo for a event
    private String name;

    public Memo (String name, String info){
        super(info);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public LocalDateTime getStartTime() { return null; }

    @Override
    public LocalDateTime getEndTime() { return null; }


}
