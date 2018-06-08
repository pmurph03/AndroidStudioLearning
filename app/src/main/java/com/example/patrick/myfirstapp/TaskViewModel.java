package com.example.patrick.myfirstapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository mRepository;

    private LiveData<List<Task>> mAllTasks;

    public TaskViewModel(Application application)
    {
        super(application);
        mRepository = new TaskRepository(application);
        mAllTasks = mRepository.getAllTasks();
    }



    LiveData<List<Task>> getAllTasks(){return mAllTasks;}

    public void insert(Task task){mRepository.insert(task);}

    public void update(Task task){mRepository.updateTask(task);}

    public void updateTaskCompletionByName(String name, boolean isComplete){mRepository.updateTasKCompletionByName(name,isComplete);}

    //public Task getTaskByName(String name){ return null;}
}
