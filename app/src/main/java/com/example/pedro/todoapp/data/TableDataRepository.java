package com.example.pedro.todoapp.data;

import com.example.pedro.todoapp.data.dao.TableDao;
import com.example.pedro.todoapp.data.entity.Table;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TableDataRepository {

    private TableDao tableDao;

    @Inject
    public TableDataRepository(TableDao tableDao) {
        this.tableDao = tableDao;
    }

    public Completable insertTable(Table table) {
        /*return Completable.defer(() ->{
            tableDao.insertTable(table);
            return Completable.complete();
        });*/
        tableDao.insertTable(table);
        return null;
    }

    public Completable updateTable(Table table) {
        return Completable.defer(() ->{
            tableDao.updateTable(table);
            return Completable.complete();
        });
    }

    public Completable deleteTable(int id) {
        return Completable.defer(() ->{
            tableDao.deleteTable(id);
            return Completable.complete();
        });
    }

    public Flowable<List<Table>> getAllTables() {
        return tableDao.getAllTables();
    }
}
