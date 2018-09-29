package com.example.pedro.todoapp.data.dao;

import com.example.pedro.todoapp.data.entity.Table;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Flowable;

@Dao
public interface TableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTable(Table table);

    @Update
    void updateTable(Table table);

    @Delete
    void deleteTable(Table table);

    @Query("SELECT * FROM `table`")
    Flowable<List<Table>> getAllTables();
}
