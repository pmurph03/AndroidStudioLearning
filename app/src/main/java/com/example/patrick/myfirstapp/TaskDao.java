package com.example.patrick.myfirstapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();


    //If the table has more than one column, you can use
    // to replace a row.
    //@Insert(onConflict = OnConflictStrategy.REPLACE)

    //order column by Tasks name.
    @Query("SELECT * from task_table ORDER BY Tasks ASC")
    LiveData<List<Task>> getAllTasks();
}
