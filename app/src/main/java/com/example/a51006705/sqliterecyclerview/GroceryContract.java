package com.example.a51006705.sqliterecyclerview;

import android.provider.BaseColumns;

/**
 * Created by 51006705 on 17-06-2019.
 */
//This class is just for some Strings like table name, column name


public class GroceryContract {

    //Blank Constructor
    private GroceryContract(){}

    //We create separate static inner class for each and every table.
    //Here we have only one table so we will make one inner class

    //BaseColumns provide "ID column for our table"
    public static final class GroceryEntry implements BaseColumns{

        public static final String TABLE_NAME = "groceryList";
        public static final String COLUMN_NAME = "name";
        public static final String COLOUMN_AMOUNT = "amount";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
