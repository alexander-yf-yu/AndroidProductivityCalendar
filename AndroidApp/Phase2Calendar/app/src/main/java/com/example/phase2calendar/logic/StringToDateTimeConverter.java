package com.example.phase2calendar.logic;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class StringToDateTimeConverter {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime secondLowest(String[] tokens) {
        int[] startTimeAndDay = new int[6];
        for (int i = 0; i < tokens.length; i++) {
            startTimeAndDay[i] = Integer.parseInt(tokens[i]);
        }
        return LocalDateTime.of(startTimeAndDay[0],startTimeAndDay[1],startTimeAndDay[2],startTimeAndDay[3],startTimeAndDay[4],startTimeAndDay[5]);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalTime secondLowestTime(String[] tokens) {
        int[] startTime = new int[3];
        for (int i = 0; i < tokens.length; i++) {
            startTime[i] = Integer.parseInt(tokens[i]);
        }
        return LocalTime.of(startTime[0], startTime[1], startTime[2]);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime dateOnly(String[] tokens){
        int[] date = new int[3];
        for (int i = 0; i < tokens.length; i++) {
            date[i] = Integer.parseInt(tokens[i]);
        }
        return LocalDateTime.of(date[0], date[1], date[2], 0, 0, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<DayOfWeek> toDayOfWeek(String[] tokens){
        ArrayList<DayOfWeek> list = new ArrayList<>();
        for(String day:tokens){
            switch (day){
                case "1": list.add(DayOfWeek.MONDAY); break;
                case "2": list.add(DayOfWeek.TUESDAY); break;
                case "3": list.add(DayOfWeek.WEDNESDAY); break;
                case "4": list.add(DayOfWeek.THURSDAY); break;
                case "5": list.add(DayOfWeek.FRIDAY); break;
                case "6": list.add(DayOfWeek.SATURDAY); break;
                case "7": list.add(DayOfWeek.SUNDAY); break;
            }
        }
        return list;
    }
}
