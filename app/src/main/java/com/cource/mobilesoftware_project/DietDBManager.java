package com.cource.mobilesoftware_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DietDBManager extends SQLiteOpenHelper{
    static final String DB_NAME = "Diet.db";
    static final String TABLE_NAME ="Diet";
    Context context = null;
    private static DietDBManager dbManager = null;

    static final String CREATE_DB = " CREATE TABLE "
            + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " date TEXT NOT NULL, time TEXT NOT NULL, food_category TEXT NOT NULL, "
            + "food_name TEXT NOT NULL, food_cnt INTEGER NOT NULL, food_kcal INTEGER NOT NULL, food_summary TEXT NOT NULL, " +
            " bm BYTE NOT NULL);";

    public static DietDBManager getInstance(Context context) {
        if (dbManager == null) {
            dbManager = new DietDBManager(context, DB_NAME, null, 1);
        }
        return dbManager;
    }
    public DietDBManager(Context context, String dbName,
                         SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, version);
        this.context = context;
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
    }
    public long insert(ContentValues addValue) {
        return getWritableDatabase().insert(TABLE_NAME, null, addValue);
    }
    public Cursor query(String [] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        return getReadableDatabase().query(TABLE_NAME, columns,
                selection, selectionArgs, groupBy, having, orderBy);
    }
    public int delete(String whereClause, String[] whereArgs) {
        return getWritableDatabase().delete(TABLE_NAME, whereClause,
                whereArgs);
    }
//    public DietDBManager(Context context){
//        super(context, DB_NAME, null,1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(CREATE_DB);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
//        onCreate(sqLiteDatabase);
//    }

}
