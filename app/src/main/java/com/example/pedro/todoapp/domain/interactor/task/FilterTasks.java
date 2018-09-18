package com.example.pedro.todoapp.domain.interactor.task;

import java.util.ArrayList;
import java.util.List;

public class FilterTasks<T> {

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
