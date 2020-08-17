package src;

import java.util.ArrayList;

public interface EventSearcher<T> {
    ArrayList<Event> search(ArrayList<Event> list, T args);
}
