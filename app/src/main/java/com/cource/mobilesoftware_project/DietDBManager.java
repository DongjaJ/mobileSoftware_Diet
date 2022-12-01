package com.cource.mobilesoftware_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DietDBManager extends SQLiteOpenHelper{
    static final String DB_NAME = "Diet.db";
    static final String TABLE_NAME ="Diet";

    static final String CREATE_DB = " CREATE TABLE "
            + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " date TEXT NOT NULL, time TEXT NOT NULL, food_category TEXT NOT NULL, "
            + "food_name TEXT NOT NULL, food_cnt INTEGER NOT NULL, food_calory INTEGER NOT NULL, food_summary TEXT NOT NULL, " +
            " img_name TEXT NOT NULL);";

    public DietDBManager(Context context){
        super(context, DB_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
