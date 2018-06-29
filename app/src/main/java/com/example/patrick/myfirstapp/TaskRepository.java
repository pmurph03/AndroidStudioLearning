package com.example.patrick.myfirstapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.ReadablePeriod;
import org.joda.time.Weeks;

import java.util.ArrayList;
import java.util.Date;
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
               boolean IsOkayToAddPerSchedule = IsDateSeparatedBySchedule(task);

               if (IsOkayToAddPerSchedule)
               {
                   int missedDaysToAdd = ExtraCompletionsRequired(task);
                   for (int i = 0; i<missedDaysToAdd;i++)
                   {
                       Log.d("TEST","ADD TO: " + this.mTaskName);
                       //TODO: parameter for fail or sucesss on missed day?
                       task.getCompletions().add(false);
                   }
                   task.getCompletions().add(this.mIsComplete);

               }
                mAsyncTaskDao.updateTask(task);
            }
            return null;
        }
    }

    static int ExtraCompletionsRequired(Task task)
    {
        LocalDate currentLocal = new LocalDate();
        LocalDate prevLocal = new LocalDate(task.getDateCreated());
        //currentLocal = currentLocal.plusDays(3);

        Log.d("TEST","Current Local:" + currentLocal);
        Log.d("TEST","Prev localdate:" + prevLocal);

        int daysBetween = Days.daysBetween(prevLocal,currentLocal).getDays();
        Log.d("TEST","Days between localdate:" + daysBetween);
        int weeksBetween = Weeks.weeksBetween(prevLocal.dayOfWeek().withMinimumValue().minusDays(1),currentLocal.dayOfWeek().withMinimumValue().plusDays(1)).getWeeks();
        Log.d("TEST","Weeks between localdate:" + weeksBetween);
        int monthsBetween = Months.monthsBetween(prevLocal.dayOfMonth().withMinimumValue().minusDays(1),currentLocal.dayOfMonth().withMinimumValue().plusDays(1)).getMonths();
        Log.d("TEST","Months between localdate:" + monthsBetween);

        int completionsToAdd = 0;
        switch (task.getTaskSchedule())
        {
            case Daily:
                completionsToAdd = (Days.daysBetween(prevLocal,currentLocal).getDays()* task.getFrequency())- task.getCompletions().size() ;
                break;
            case Weekly:
                completionsToAdd = (Weeks.weeksBetween(prevLocal.dayOfWeek().withMinimumValue().minusDays(1),currentLocal.dayOfWeek().withMinimumValue().plusDays(1)).getWeeks()
                        * task.getFrequency())- task.getCompletions().size();
                break;
            case Monthly:

                completionsToAdd = (Months.monthsBetween(prevLocal.dayOfMonth().withMinimumValue().minusDays(1),currentLocal.dayOfMonth().withMinimumValue().plusDays(1)).getMonths()
                        *task.getFrequency())- task.getCompletions().size() ;
                break;
        }

        if (completionsToAdd<0)
        {
            completionsToAdd = 0;
        }
        Log.d("TEST","EXTRA COMPLETIONS TO ADD:" + completionsToAdd);
        return completionsToAdd;
    }




    static boolean IsDateSeparatedBySchedule(Task task)
    {
        LocalDate prevLocal = new LocalDate(task.getDateCreated());
        LocalDate currentLocal = new LocalDate();
      //  currentLocal = currentLocal.plusDays(3);
        int TaskFrequency = task.getFrequency();
        ArrayList<Boolean> completions = task.getCompletions();
        int numCompletionsExpected = 0;
        switch (task.getTaskSchedule())
        {
            case Daily:
            {

                numCompletionsExpected = (1+ Days.daysBetween(prevLocal,currentLocal).getDays())* task.getFrequency();
                Log.d("TEST","DAILY COMPLETIONS:" + numCompletionsExpected);
                break;
            }
            case Weekly:
            {
                numCompletionsExpected = (1+Weeks.weeksBetween(prevLocal.dayOfWeek().withMinimumValue().minusDays(1),currentLocal.dayOfWeek().withMinimumValue().plusDays(1)).getWeeks())
                        *TaskFrequency;
                Log.d("TEST","WEEKLY COMPLETIONS:" + numCompletionsExpected);
                break;
            }
            case Monthly:
            {
                numCompletionsExpected = (1+Months.monthsBetween(prevLocal.dayOfMonth().withMinimumValue().minusDays(1),currentLocal.dayOfMonth().withMinimumValue().plusDays(1)).getMonths())
                        *TaskFrequency;
                Log.d("TEST","MONTHLY COMPLETIONS:" + numCompletionsExpected);
                break;
            }

        }
        if (completions.size()>=numCompletionsExpected)
        {
            return false;
        }
        else {return true;}
    }
}
