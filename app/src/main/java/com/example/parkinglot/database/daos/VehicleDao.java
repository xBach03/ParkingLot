package com.example.parkinglot.database.daos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class VehicleDao {
    // Vehicle table columns
    public static class VehicleEntry implements BaseColumns {
        public static final String TABLE_VEHICLE = "vehicle";
        public static final String VEHICLE_ID = "id";
        public static final String VEHICLE_TYPE = "type";
        public static final String VEHICLE_COLOR = "color";
        public static final String VEHICLE_USERID = "userId";
    }
    SQLiteDatabase db;
    public VehicleDao(SQLiteDatabase db) {
        this.db = db;
    }
    public boolean scanPlate(String licensePlate, int userId) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(VehicleEntry.VEHICLE_ID, licensePlate);
        values.put(VehicleEntry.VEHICLE_USERID, userId);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(VehicleEntry.TABLE_VEHICLE, null, values);
        return newRowId != -1;
    }

}
