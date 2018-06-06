package com.example.patrick.myfirstapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @NonNull
    @ColumnInfo(name = "Date Created")
    private Date mDateCreated;

    @NonNull
    @ColumnInfo(name = "Completions")
    private ArrayList<Boolean> mCompletions;

    public Task(@NonNull String task, @NonNull Integer frequency, @NonNull TaskSchedule taskSchedule)
    {
        this.mTask = task;
        this.mFrequency = frequency;
        this.mTaskSchedule = taskSchedule;
        this.mDateCreated = new Date();
        this.mCompletions = new ArrayList<Boolean>();
    }

    public String getTask(){return this.mTask;}
    public Integer getFrequency(){return this.mFrequency;}
    public void setFrequency(Integer value){this.mFrequency = value;}
    public TaskSchedule getTaskSchedule(){return this.mTaskSchedule;}
    public Date getDateCreated(){return this.mDateCreated;}
    public void setDateCreated(Date date){this.mDateCreated = date;}
    public ArrayList<Boolean> getCompletions(){return this.mCompletions;}
    public void setCompletions(ArrayList<Boolean> completions){this.mCompletions = completions;}

}
