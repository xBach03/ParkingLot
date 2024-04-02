package com.example.parkinglot.database.entities;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.parkinglot.database.DatabaseHelper;
import com.example.parkinglot.database.daos.UserDao;

public class AuthenticationManager {
    private static AuthenticationManager instance;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    private User currentUser; // Store the current user

    // Private constructor to prevent instantiation from outside
    private AuthenticationManager(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    public static synchronized AuthenticationManager getInstance(Context context) {
        if (instance == null) {
            instance = new AuthenticationManager(context);
        }
        return instance;
    }

    // Method to log in user
    public void loginUser(String username, String password) {
        // Query all from user table
        String[] projection = {
                UserDao.UserEntry.USER_ID,
                UserDao.UserEntry.USER_USERNAME,
                UserDao.UserEntry.USER_PASSWORD
        };

        // Query to find current user
        String selection = UserDao.UserEntry.USER_USERNAME + " = ? AND " +UserDao.UserEntry.USER_PASSWORD + " = ?";
        String[] selectionArgs = { username, password };


        Cursor cursor = db.query(
                UserDao.UserEntry.TABLE_USER,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        // Check username and password against your authentication system
        // If authentication is successful, set the currentUser
        if (cursor.getCount() > 0) {
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow(UserDao.UserEntry.USER_ID));
                        currentUser = new User(id, username, password);
                    } while (cursor.moveToNext());
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }

    // Method to log out user
    public void logoutUser() {
        currentUser = null;
    }

    // Method to check if a user is logged in
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    // Method to get the current logged-in user
    public User getCurrentUser() {
        return currentUser;
    }

}
