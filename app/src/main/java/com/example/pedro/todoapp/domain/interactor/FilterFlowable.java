package com.example.pedro.todoapp.domain.interactor;

import java.util.ArrayList;
import java.util.List;

public class FilterFlowable<T> {

    private List<T> list;

    public FilterFlowable() {
        this.list = new ArrayList<>();
    }

    public List<T> filterList(List<T> newList) {
        newList.removeAll(list);
        list.addAll(newList);
        return newList;
    }

    public void itemRemoved(T object) {
        list.remove(object);
    }
}
