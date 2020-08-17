package com.example.phase2calendar.logic;

import java.util.ArrayList;

public interface EventSearcher<T> {
    ArrayList<Event> search(ArrayList<Event> list, T args);
}
