package com.example.patrick.myfirstapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "task_table")
public class Task {
    //would allow for tasks of the same name, but different frequency.
    //@PrimaryKey(autoGenerate = true)
    //private int id;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Tasks")
    private String mTask;


    public Task(@NonNull String task)
    {
        this.mTask = task;
    }

    public String getTask(){return this.mTask;}

}
