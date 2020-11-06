package com.example.expense_manager;

public class ListItem
{
    private String itemName;
    private int itemImageID;

    public ListItem(String itemName, int itemImageID)
    {
        this.itemName = itemName;
        this.itemImageID = itemImageID;
    }

    public String getItemName()
    {
        return itemName;
    }

    public int getItemImageID()
    {
        return itemImageID;
    }
}
