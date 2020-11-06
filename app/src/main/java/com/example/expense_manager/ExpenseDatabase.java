package com.example.expense_manager;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters({Converters.class})
@Database(entities = {Expense.class},version = 1)
public  abstract class ExpenseDatabase extends RoomDatabase {
    public  abstract ExpenseDao expenseDao();
}
