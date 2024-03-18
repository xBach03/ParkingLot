package com.example.parkinglot.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.parkinglot.database.entities.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DatabaseName = "LotParker";
    public static final int DatabaseVersion = 1;

    private static final String TABLE_USER = "user";
    private static final String USER_ID = "id";
    private static final String USER_USERNAME = "username";
    private static final String USER_PASSWORD = "password";
    // Create user table string
    public static final String CreateTableUser = "CREATE TABLE " + TABLE_USER +
            "(" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_USERNAME + " TEXT, " + USER_PASSWORD + " TEXT)";
    public DatabaseHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    // Oncreate -> create table user
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateTableUser);
    }

}
