package com.example.gotravel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TravelApp.db";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_PLACES = "places";
    public static final String COL_PLACE_ID = "id";
    public static final String COL_PLACE_NAME = "name";
    public static final String COL_PLACE_DESCRIPTION = "description";
    public static final String COL_PLACE_IMAGE = "image";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the Places table
        db.execSQL("CREATE TABLE " + TABLE_PLACES + " (" +
                COL_PLACE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PLACE_NAME + " TEXT, " +
                COL_PLACE_DESCRIPTION + " TEXT, " +
                COL_PLACE_IMAGE + " BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop and recreate the Places table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        onCreate(db);
    }

    // Insert a new place
    public boolean insertPlace(String name, String description, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PLACE_NAME, name);
        contentValues.put(COL_PLACE_DESCRIPTION, description);
        contentValues.put(COL_PLACE_IMAGE, image);

        long result = db.insert(TABLE_PLACES, null, contentValues);
        return result != -1;
    }

    // Retrieve all places
    public Cursor getAllPlaces() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PLACES, null);
    }

    // Search for a place by name
    public Cursor searchPlaceByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PLACES + " WHERE " + COL_PLACE_NAME + " LIKE ?", new String[]{"%" + name + "%"});
    }

    // Update an existing place by its ID
    public boolean updatePlace(int id, String name, String description, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PLACE_NAME, name);
        contentValues.put(COL_PLACE_DESCRIPTION, description);
        contentValues.put(COL_PLACE_IMAGE, image);

        int result = db.update(TABLE_PLACES, contentValues, COL_PLACE_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Delete a place by its ID
    public boolean deletePlace(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PLACES, COL_PLACE_ID + " = ?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}

