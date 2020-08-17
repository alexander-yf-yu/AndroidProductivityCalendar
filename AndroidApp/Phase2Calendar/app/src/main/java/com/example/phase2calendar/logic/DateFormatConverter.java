package com.example.phase2calendar.logic;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class DateFormatConverter {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String convertLocalDateTime(LocalDateTime date) {
        String converted = "";
        converted = converted + date.getDayOfMonth() + "/";
        converted = converted + date.getMonthValue() + "/";
        converted = converted + date.getYear() + " ";
        converted = converted + date.getHour() + ":";
        converted = converted + date.getMinute();

        return converted;
    }
}
