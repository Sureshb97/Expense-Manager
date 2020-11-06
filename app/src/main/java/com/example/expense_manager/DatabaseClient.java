package com.example.expense_manager;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient
{
    private Context ctx;
    private ExpenseDatabase expenseDatabase;
    private static DatabaseClient instance;

    private DatabaseClient(Context context)
    {
        ctx=context;
        expenseDatabase= Room.databaseBuilder(ctx,ExpenseDatabase.class,"expensedatabase.db").build();

    }

    public static  synchronized DatabaseClient getInstance(Context context)
    {
        if(instance==null)
        {
            instance=new DatabaseClient(context);
        }
        return instance;
    }

    public ExpenseDatabase getExpenseDatabase()
    {
        return expenseDatabase;
    }
}
