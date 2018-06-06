package com.example.patrick.myfirstapp;

import android.arch.persistence.room.TypeConverter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

    @TypeConverter
    public static Date fromTimeStamp(Long value)
    {
        return value == null ? null: new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date)
    {
        return date == null ? null : date.getTime();
    }

  //  @TypeConverter
  //  public static int[]
    @TypeConverter
    public static ArrayList<Boolean> fromString(String value)
    {
        if (value == null)
        {
            return new ArrayList<Boolean>();
        }
        else
        {
          Type boolList = new TypeToken<ArrayList<Boolean>>(){}.getType();
          return new Gson().fromJson(value, boolList);
        }
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Boolean> list)
    {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}
