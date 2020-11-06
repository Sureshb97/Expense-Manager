package com.example.expense_manager;


import androidx.room.TypeConverter;
import java.sql.Date;

public class Converters
{
    @TypeConverter
    public static Date fromTimestamp(String value)
    {
        return value == null ? null : Date.valueOf(value);
    }

    @TypeConverter
    public static String dateToTimestamp(Date date)
    {
        return date == null ? null : String.valueOf(date);
    }
}
