package com.cource.mobilesoftware_project;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.cource.mobilesoftware_project.MyContentProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/foods";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String _ID = "_id";
    static final String NAME = "food_name";
    static final String CNT = "food_cnt";
    static final String DATE = "date";
    static final String TIME = "time";
    static final String CATEGORY = "food_category";
    static final String SUMMERY = "food_summary";
    static final String KCAL = "food_kcal";
    static final String BYTE = "bm";

    public DietDBManager dbManager;
    public MyContentProvider(){

    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return dbManager.delete(selection, selectionArgs);
    }
    @Override
    public String getType(Uri uri) {
        return null;
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowid = dbManager.insert(values);
        return null;
    }
    @Override
    public boolean onCreate() {
        dbManager = DietDBManager.getInstance(getContext());
        return true;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return dbManager.query(projection, selection, selectionArgs, null, null, sortOrder);
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
