package com.example.phase2calendar.logic;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class SeriesFactory {

    public Series createEmptySeries(String name, String description) {
        return new Series(name, description);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Series createDayGapSeries(String name, String description, LocalDateTime startDateTime, LocalTime endTime, int dayGap, int numEvents) {
        return new Series(name, description, startDateTime, endTime, dayGap, numEvents);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Series createSpecificDaySeries(String name, String description, LocalDateTime startDateTime, LocalTime endTime, ArrayList<DayOfWeek> days, int numEvents) {
        return new Series(name, description, startDateTime, endTime, days, numEvents);
    }
}
