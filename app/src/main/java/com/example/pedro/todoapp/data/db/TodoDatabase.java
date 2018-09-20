package com.example.pedro.todoapp.data.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.pedro.todoapp.data.dao.TasksDao;
import com.example.pedro.todoapp.data.entity.Task;

@Database(entities = Task.class, version = 1)
public abstract class TodoDatabase extends RoomDatabase {

    private static TodoDatabase INSTANCE;

    public abstract TasksDao tasksDao();

    private static final Object sLock = new Object();

    public static TodoDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        TodoDatabase.class, "Tasks.db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
            return INSTANCE;
        }
    }
}
