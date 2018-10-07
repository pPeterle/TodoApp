package com.example.pedro.todoapp.data.db;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.util.Log;

import com.example.pedro.todoapp.data.dao.TableDao;
import com.example.pedro.todoapp.data.dao.TaskDao;
import com.example.pedro.todoapp.data.entity.Table;
import com.example.pedro.todoapp.data.entity.Task;

import java.util.concurrent.Executors;

@Database(entities = {Task.class, Table.class}, version = 1)
public abstract class TodoDatabase extends RoomDatabase {

    private static TodoDatabase INSTANCE;

    public abstract TaskDao tasksDao();
    public abstract TableDao tableDao();

    private static final Object sLock = new Object();

    public static TodoDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TodoDatabase.class, "Tasks.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                Log.i("test", "database criado com sucesso");
                            }
                        })
                        .build();
            }
            return INSTANCE;
        }
    }
}
