package com.example.parkinglot.database.daos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.parkinglot.database.entities.User;

public class UserDao {
    private SQLiteDatabase db;
    // User table columns
    public static class UserEntry implements BaseColumns {
        public static final String TABLE_USER = "user";
        public static final String USER_ID = "id";
        public static final String USER_USERNAME = "username";
        public static final String USER_PASSWORD = "password";
    }
    public UserDao(SQLiteDatabase database){
        db = database;
    }
    // Insert user into table
    public boolean insertUser(User user) {
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UserEntry.USER_USERNAME,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserEntry.USER_USERNAME + " = ?";
        String[] selectionArgs = { user.getUserName() };

        Cursor cursor = db.query(
                UserEntry.TABLE_USER,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null
        );
        int check = cursor.getCount();
        cursor.close();
        long insert = -1;
        if(check == 0) {
            ContentValues values = new ContentValues();
            values.put("username", user.getUserName());
            values.put("password", user.getPassword());
            insert = db.insert("user", null, values);
        }
        return insert != -1;
    }
    // Check for login information, if an account is registered
    public boolean userLoginCheck(User user) {

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UserEntry.USER_USERNAME,
                UserEntry.USER_PASSWORD
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserEntry.USER_USERNAME + " = ? AND " + UserEntry.USER_PASSWORD + " = ?" ;
        String[] selectionArgs = { user.getUserName(), user.getPassword() };


        Cursor cursor = db.query(
                UserEntry.TABLE_USER,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null
        );
        int check = cursor.getCount();
        cursor.close();
        return check > 0;
    }
    public String getUserName(int id) {
        String userName = "";
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UserEntry.USER_USERNAME,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = UserEntry.USER_ID + " = ?";
        String[] selectionArgs = { Integer.valueOf(id).toString() };

        Cursor cursor = db.query(
                UserEntry.TABLE_USER,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null
        );
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    userName = cursor.getString(cursor.getColumnIndexOrThrow(UserEntry.USER_USERNAME));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return userName;
    }
}
