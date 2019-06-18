package com.example.a51006705.sqliterecyclerview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//imported so that we don't need to write GroceryContract.GroceryEntry everytime
import com.example.a51006705.sqliterecyclerview.GroceryContract.*;
/**
 * Created by 51006705 on 17-06-2019.
 */
//This class is for SQlite
public class GroceryDBHelper extends SQLiteOpenHelper {

    //create two constants
    public static final String DATABASE_NAME = "grocerylist.db";
    public static final int DATABASE_VERSION = 1;

    //constructor
    public GroceryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_GROCERYLIST_TABLE = "CREATE TABLE "+ GroceryEntry.TABLE_NAME+" ("+
                GroceryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                GroceryEntry.COLUMN_NAME +" TEXT NOT NULL, "+
                GroceryEntry.COLOUMN_AMOUNT +" INTEGER NOT NULL, "+
                GroceryEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_GROCERYLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ GroceryContract.GroceryEntry.TABLE_NAME);
        onCreate(db);

    }
}
