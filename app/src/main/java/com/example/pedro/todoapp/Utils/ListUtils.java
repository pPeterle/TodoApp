package com.example.pedro.todoapp.Utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    private ListUtils() { }

    public static <T> List<T> inverterOrder(List<T> list) {

        List<T> newList = new ArrayList<>();
        for (T item : list) {
            newList.add(0, item);
        }

        return newList;
    }
}
