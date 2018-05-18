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
    @NonNull
    @ColumnInfo(name = "Frequency")
    private Integer mFrequency;

    @NonNull
    @ColumnInfo(name = "Schedule")
    private TaskSchedule mTaskSchedule;


    public Task(@NonNull String task, @NonNull Integer frequency, @NonNull TaskSchedule taskSchedule)
    {
        this.mTask = task;
        this.mFrequency = frequency;
        this.mTaskSchedule = taskSchedule;
    }

    public String getTask(){return this.mTask;}
    public Integer getFrequency(){return this.mFrequency;}
    public TaskSchedule getTaskSchedule(){return this.mTaskSchedule;}

}
