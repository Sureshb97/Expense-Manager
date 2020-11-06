package com.example.expense_manager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.sql.Date;

@Dao
public interface ExpenseDao
{
     @Insert
     void insert(Expense expense);

     @Update
     void update(Expense expense);

     @Delete
     void delete(Expense expense);

     @Query("SELECT SUM(Amount) FROM expense WHERE Category=:CATEGORY AND Date= :date")
     int getSumQuery(Date date, String CATEGORY);

     @Query(" DELETE FROM expense")
     void clearAll();
}
