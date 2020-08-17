package com.example.phase2calendar.logic;

import java.time.LocalDateTime;

public interface Listable {
    public String getName();
    public String getDescription();
    public LocalDateTime getStartTime();
    public LocalDateTime getEndTime();
}
