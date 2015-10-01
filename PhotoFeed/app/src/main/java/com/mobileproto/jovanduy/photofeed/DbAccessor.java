package com.mobileproto.jovanduy.photofeed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Jordan on 9/30/15.
 */
public class DbAccessor {

    public FeedReaderDbHelper dbHelper;

    public DbAccessor(Context context) {
        this.dbHelper = new FeedReaderDbHelper(context);
    }

    public void writeToDb(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_URL, url);
        try {
            db.insertOrThrow(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        } catch (Exception ex) {
            Log.e("Database Service", "Error occurred inserting into db", ex);
        }
    }

    public ArrayList<String> readFromDb() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnsToQuery = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_URL
        };
        //String sortOrder = FeedReaderContract.FeedEntry._ID + " DESC";

        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                columnsToQuery,
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        ArrayList<String> images = new ArrayList<>();
        images.add(cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_URL)));
        while (cursor.moveToNext()) {
            images.add(cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_URL)));
        }
        return images;
    }

    public void deleteFromDb(int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        String[] columnsToQuery = {
//                FeedReaderContract.FeedEntry._ID,
//                FeedReaderContract.FeedEntry.COLUMN_NAME_URL
//        };
//        Cursor cursor = db.query(
//                FeedReaderContract.FeedEntry.TABLE_NAME,
//                columnsToQuery,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//        cursor.moveToPosition(position);
//        long pos = cursor.getLong(
//                cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
        String selection = FeedReaderContract.FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(position) };
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

}
