package com.example.parkinglot.database.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.parkinglot.database.entities.ParkingSpace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ParkingspaceDao {
    private SQLiteDatabase db;
    // Parking space table columns
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_PARKING = "parking";
        public static final String PARKING_ID = "id";
        public static final String PARKING_STATUS = "status";
        public static final String PARKING_AREA = "area";
        public static final String PARKING_VEHICLEID = "vehicleId";
        public static final String PARKING_VEHICLETYPE = "vehicleType";
        public static final String PARKING_SPOT = "parkingSpot";

    }
    public ParkingspaceDao(SQLiteDatabase db) {
        this.db = db;
    }
    public long insertParkingspace() {
        ContentValues data = new ContentValues();
        Map<String, Integer> mp = new HashMap<>();
        mp.put("D3", 10);
        mp.put("C7", 15);
        mp.put("D8", 20);
        Random random = new Random();
        String[] vehicleTypes = {"Car", "Motorcycle", "Bike"};
        long row = 0;
        for(String c : mp.keySet()) {
            int num = mp.get(c);
            for(int i = 0; i < num; i++) {
                data.put(FeedEntry.PARKING_AREA, c);
                data.put(FeedEntry.PARKING_VEHICLEID, "null");
                data.put(FeedEntry.PARKING_STATUS, "available");
                data.put(FeedEntry.PARKING_SPOT, i + 100);
                data.put(FeedEntry.PARKING_VEHICLETYPE, vehicleTypes[random.nextInt(vehicleTypes.length)]);
                row = db.insert(FeedEntry.TABLE_PARKING, null, data);
            }
        }
        return row;
    }

    // Get parking spaces
    public List<ParkingSpace> getAvailableSpaces() {
        List<ParkingSpace> parkingSpaces = new ArrayList<>();

        // Columns to query:
        String[] projection = {
                FeedEntry.PARKING_AREA,
                FeedEntry.PARKING_STATUS,
                FeedEntry.PARKING_SPOT,
                FeedEntry.PARKING_VEHICLETYPE
        };

        // "= ?" -> query all available spaces (availability = 1)
        String selection = FeedEntry.PARKING_STATUS + " = ?";
        String[] selectionArgs = { "available" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FeedEntry.PARKING_AREA + " DESC";

        Cursor cursor = db.query(
                FeedEntry.TABLE_PARKING,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String parkingArea = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.PARKING_AREA));
                    String availability = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.PARKING_STATUS));
                    String vehicleType = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.PARKING_VEHICLETYPE));
                    String parkingSpot = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.PARKING_SPOT));
                    ParkingSpace parkingSpace = new ParkingSpace(availability, parkingArea, vehicleType, parkingSpot);
                    // Add ParkingSpace object to the list
                    parkingSpaces.add(parkingSpace);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return parkingSpaces;
    }
}
