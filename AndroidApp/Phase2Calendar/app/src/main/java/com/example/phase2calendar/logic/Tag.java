package com.example.phase2calendar.logic;

import java.time.LocalDateTime;

public class Tag extends Attachment {
    //src.Tag class that stores a tag for a event
    private String description;

    public Tag(String tag) {
        super(tag);
    }

    @Override
    public String getName() { return null; }

    @Override
    public LocalDateTime getStartTime() { return null; }

    @Override
    public LocalDateTime getEndTime() { return null; }
}
