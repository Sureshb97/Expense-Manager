package com.example.expense_manager;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.io.Serializable;
import java.sql.Date;

@Entity(tableName = "Expense")
public class Expense implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="Date")
    @TypeConverters({Converters.class})
    private Date date;

    @ColumnInfo(name = "Category")
    private String category;

    @ColumnInfo(name = "Amount")
    private int amount;

    @ColumnInfo( name = "Comments" )
    private String comments;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }
}
