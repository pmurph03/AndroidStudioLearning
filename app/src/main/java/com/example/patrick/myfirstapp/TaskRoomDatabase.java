package com.example.patrick.myfirstapp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Task.class}, version = 4)
@TypeConverters({TypeTransmogrifiers.class})
public abstract class TaskRoomDatabase extends RoomDatabase{
    public abstract TaskDao taskDao();

    private static TaskRoomDatabase INSTANCE;

    public static TaskRoomDatabase getDatabase(final Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (TaskRoomDatabase.class)
            {
                if (INSTANCE == null)
                {
                    //Create database.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskRoomDatabase.class,
                            "task_database").addCallback(sRoomDatabaseCallback).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase database)
        {
            super.onOpen(database);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void>{
        private final TaskDao mDao;
        PopulateDbAsync(TaskRoomDatabase database){
            mDao = database.taskDao();
        }

        @Override
        protected Void doInBackground(final Void... params)
        {
            mDao.deleteAll();
            Task task = new Task("Task1Name",1,TaskSchedule.Monthly);
            //task.getCompletions().add(Boolean.FALSE);
            mDao.insert(task);

            task = new Task("Task2Name",3,TaskSchedule.Daily);
          //  task.getCompletions().add(Boolean.TRUE);
            mDao.insert(task);
            return null;
        }
    }
}
