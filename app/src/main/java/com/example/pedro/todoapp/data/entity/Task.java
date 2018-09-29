package com.example.pedro.todoapp.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "tasks",
        foreignKeys = @ForeignKey(entity = Table.class,
                parentColumns = "id",
                childColumns = "tableId",
                onDelete = CASCADE))
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int tableId;

    private String title;

    private boolean completed;

    public Task(String title, int tableId, boolean completed) {
        this.title = title;
        this.completed = completed;
        this.tableId = tableId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                tableId == task.tableId &&
                completed == task.completed &&
                Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, tableId, title, completed);
    }
}
