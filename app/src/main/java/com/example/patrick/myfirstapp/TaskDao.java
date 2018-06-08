package com.example.patrick.myfirstapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface TaskDao {
    @Insert(onConflict = IGNORE)
    void insert(Task task);

    @Query("DELETE FROM task_table")
    void deleteAll();

    @Delete
    void deleteTask(Task task);

    @Update(onConflict = REPLACE)
    void updateTask(Task task);
    //If the table has more than one column, you can use
    // to replace a row.
    //@Insert(onConflict = OnConflictStrategy.REPLACE)

    //order column by Tasks name.
    @Query("SELECT * from task_table ORDER BY Tasks ASC")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM task_table WHERE Tasks LIKE :name")
    Task getTaskByName(String name);

}
