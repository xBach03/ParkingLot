package com.example.parkinglot.database.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.parkinglot.database.DatabaseHelper;
import com.example.parkinglot.database.entities.User;

public class UserDao {
    private SQLiteDatabase db;
    public UserDao(SQLiteDatabase database){
        db = database;
    }
    // Insert user into table
    public boolean insertUser(User user, Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUserName());
        values.put("password", user.getPassword());
        long insert = db.insert("user", null, values);
        return insert != -1;
    }
}
