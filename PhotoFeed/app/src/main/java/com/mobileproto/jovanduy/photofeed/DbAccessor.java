package com.mobileproto.jovanduy.photofeed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Class to modify the database
 */
public class DbAccessor {

    public FeedReaderDbHelper dbHelper;

    public DbAccessor(Context context) {
        this.dbHelper = new FeedReaderDbHelper(context);
    }

    /**
     * add a url to the database
     * @param url to be added to database
     */
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

    /**
     * reads all the data from the database
     * @return an ArrayList<String> containing all of the urls in the database
     */
    public ArrayList<String> readFromDb() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnsToQuery = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_URL
        };

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
        try {
            images.add(cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_URL)));
        } catch (Exception e){
            Log.e("Error!", e.getMessage());
        }
        while (cursor.moveToNext()) {
            images.add(cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_URL)));
        }
        return images;
    }

    /**
     * deletes a given row (image) from the database
     * @param position row to be deleted
     */
    public void deleteFromDb(int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columnsToQuery = {
                FeedReaderContract.FeedEntry._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_URL
        };
        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                columnsToQuery,
                null,
                null,
                null,
                null,
                null
        ); // search database for the correct row in order to get its id
        cursor.moveToPosition(position);
        long pos = cursor.getLong(
                cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
        String selection = FeedReaderContract.FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(pos) };
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }

}
