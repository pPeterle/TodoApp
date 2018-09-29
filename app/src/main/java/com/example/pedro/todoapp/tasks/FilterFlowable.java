package com.example.pedro.todoapp.tasks;

import java.util.ArrayList;
import java.util.List;

public class FilterFlowable<T> {

    private List<T> list = new ArrayList<>();

    public List<T> getDifferentItens(List<T> newList) {
        newList.removeAll(list);
        list.addAll(newList);
        return newList;
    }

    public void itemRemoved(T object) {
        list.remove(object);
    }
}