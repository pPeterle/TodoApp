package com.example.pedro.todoapp.data.todo;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.pedro.todoapp.data.model.Task;

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
