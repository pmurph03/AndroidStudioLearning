package com.example.patrick.myfirstapp;

import android.arch.persistence.room.TypeConverter;

public class TypeTransmogrifiers {
    @TypeConverter
    public static int toInteger(TaskSchedule ts)
    {
        return ts.ordinal();
    }

    @TypeConverter
    public static TaskSchedule fromInteger(int value)
    {
        switch(value)
        {
            case 0:
                return TaskSchedule.Daily;
            case 1:
                return TaskSchedule.Weekly;
            case 2:
                return TaskSchedule.Monthly;
        }
        return TaskSchedule.Daily;
    }
}
