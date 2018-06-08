package com.example.patrick.myfirstapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

public class TaskRepository {
    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTasks;

    TaskRepository(Application application)
    {
        TaskRoomDatabase database = TaskRoomDatabase.getDatabase(application);

        mTaskDao = database.taskDao();
        mAllTasks = mTaskDao.getAllTasks();
    }

    LiveData<List<Task>> getAllTasks(){
        return mAllTasks;
    }

    public void insert(Task task)
    {
        new insertAsyncTask(mTaskDao).execute(task);
    }
    private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao mAsyncTaskDao;

        insertAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void updateTask(Task task) { new insertUpdateUserAsyncTask(mTaskDao).execute(task); }
    public static class insertUpdateUserAsyncTask extends AsyncTask<Task, Void, Void>{
        private TaskDao mAsyncTaskDao;
        insertUpdateUserAsyncTask(TaskDao dao){mAsyncTaskDao = dao;}
        @Override
        protected Void doInBackground(Task... params)
        {
            mAsyncTaskDao.updateTask(params[0]);
            return null;
        }
    }


    public void updateTasKCompletionByName(String name, boolean isComplete){
        new updateTaskCompletionByNameAsyncTask(name,isComplete,mTaskDao).execute();
    }
    public static class updateTaskCompletionByNameAsyncTask extends AsyncTask<String, Void, Void>
    {
        private TaskDao mAsyncTaskDao;
        private String mTaskName;
        private boolean mIsComplete;
        updateTaskCompletionByNameAsyncTask(String taskName, boolean isComplete, TaskDao dao)
        {
            mTaskName = taskName;
            mIsComplete = isComplete;
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(String... params) {
            Task task = mAsyncTaskDao.getTaskByName(this.mTaskName);
            if (task==null)
            {
                Log.d("TEST","NO TASK WITH NAME FOUND OF: " + this.mTaskName);
            }
            else
            {
                task.getCompletions().add(this.mIsComplete);
                mAsyncTaskDao.updateTask(task);
            }
            return null;
        }
    }

    //TODO: get task by name with callback and async tasks / figure out how that works.
   /* public Task getTaskByName(String name){new getTaskByNameAsyncTask().execute(name);
    }
    public static class getTaskByNameAsyncTask extends AsyncTask<String,Void,Void>
    {
        @Override
        protected Void doInBackground(String... params)
        {
            return null;
        }

    }*/



}
