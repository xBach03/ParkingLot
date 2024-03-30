package com.example.parkinglot.database.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.parkinglot.database.entities.ParkingSlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ParkingSlotDao {
    private SQLiteDatabase db;
    // Parking space table columns
    public static class ParkingEntry implements BaseColumns {
        public static final String TABLE_PARKING = "parking";
        public static final String PARKING_ID = "id";
        public static final String PARKING_STATUS = "status";
        public static final String PARKING_AREA = "area";
        public static final String PARKING_VEHICLEID = "vehicleId";
        public static final String PARKING_VEHICLETYPE = "vehicleType";
        public static final String PARKING_SPOT = "parkingSpot";

    }
    public ParkingSlotDao(SQLiteDatabase db) {
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
//        String[] availability = {"available", "reserved", "parked"};
        long row = 0;
        for(String c : mp.keySet()) {
            int num = mp.get(c);
            for(int i = 0; i < num; i++) {
                data.put(ParkingEntry.PARKING_AREA, c);
                data.put(ParkingEntry.PARKING_VEHICLEID, "null");
                data.put(ParkingEntry.PARKING_STATUS, "available");
                data.put(ParkingEntry.PARKING_SPOT, i + 100);
                data.put(ParkingEntry.PARKING_VEHICLETYPE, vehicleTypes[random.nextInt(vehicleTypes.length)]);
                row = db.insert(ParkingEntry.TABLE_PARKING, null, data);
            }
        }
        return row;
    }
    // Get all parking spaces
    public List<ParkingSlot> getAllSlots() {
        List<ParkingSlot> allSlots = new ArrayList<>();

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ParkingEntry.PARKING_AREA + " DESC";
        Cursor cursor = db.query(
                ParkingEntry.TABLE_PARKING,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int parkingId = cursor.getInt(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_ID));
                    int vehicleId = cursor.getInt(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_VEHICLEID));
                    String parkingArea = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_AREA));
                    String availability = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_STATUS));
                    String vehicleType = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_VEHICLETYPE));
                    String parkingSpot = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_SPOT));
                    ParkingSlot parkingSlot = new ParkingSlot(parkingId, availability, vehicleType, parkingSpot, parkingArea, vehicleId);
                    // Add ParkingSpace object to the list
                    allSlots.add(parkingSlot);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return allSlots;
    }

    // Get available parking spaces
    public List<ParkingSlot> getAvailableSlots() {
        List<ParkingSlot> availableSlots = new ArrayList<>();
        // "= ?" -> query all available spaces (status = available)
        String selection = ParkingEntry.PARKING_STATUS + " = ?";
        String[] selectionArgs = { "available" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ParkingEntry.PARKING_AREA + " DESC";

        Cursor cursor = db.query(
                ParkingEntry.TABLE_PARKING,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int parkingId = cursor.getInt(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_ID));
                    int vehicleId = cursor.getInt(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_VEHICLEID));
                    String parkingArea = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_AREA));
                    String availability = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_STATUS));
                    String vehicleType = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_VEHICLETYPE));
                    String parkingSpot = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_SPOT));
                    ParkingSlot parkingSlot = new ParkingSlot(parkingId, availability, vehicleType, parkingSpot, parkingArea, vehicleId);
                    // Add ParkingSpace object to the list
                    availableSlots.add(parkingSlot);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return availableSlots;
    }

    // Get reserved parking spaces
    public List<ParkingSlot> getReservedSlots() {
        List<ParkingSlot> reservedSlots = new ArrayList<>();
        // "= ?" -> query all available spaces (status = reserved)
        String selection = ParkingEntry.PARKING_STATUS + " = ?";
        String[] selectionArgs = { "reserved" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ParkingEntry.PARKING_AREA + " DESC";

        Cursor cursor = db.query(
                ParkingEntry.TABLE_PARKING,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int parkingId = cursor.getInt(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_ID));
                    int vehicleId = cursor.getInt(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_VEHICLEID));
                    String parkingArea = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_AREA));
                    String availability = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_STATUS));
                    String vehicleType = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_VEHICLETYPE));
                    String parkingSpot = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_SPOT));
                    ParkingSlot parkingSlot = new ParkingSlot(parkingId, availability, vehicleType, parkingSpot, parkingArea, vehicleId);
                    // Add ParkingSpace object to the list
                    reservedSlots.add(parkingSlot);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return reservedSlots;
    }

    // Get parked spaces
    public List<ParkingSlot> getParkedSlots() {
        List<ParkingSlot> parkedSlots = new ArrayList<>();
        // "= ?" -> query all available spaces (status = parked)
        String selection = ParkingEntry.PARKING_STATUS + " = ?";
        String[] selectionArgs = { "parked" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ParkingEntry.PARKING_AREA + " DESC";

        Cursor cursor = db.query(
                ParkingEntry.TABLE_PARKING,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int parkingId = cursor.getInt(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_ID));
                    int vehicleId = cursor.getInt(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_VEHICLEID));
                    String parkingArea = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_AREA));
                    String availability = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_STATUS));
                    String vehicleType = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_VEHICLETYPE));
                    String parkingSpot = cursor.getString(cursor.getColumnIndexOrThrow(ParkingEntry.PARKING_SPOT));
                    ParkingSlot parkingSlot = new ParkingSlot(parkingId, availability, vehicleType, parkingSpot, parkingArea, vehicleId);
                    // Add ParkingSpace object to the list
                    parkedSlots.add(parkingSlot);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return parkedSlots;
    }
    // Turns an available slot -> reserved slot
    public boolean reserveSlot(int id) {
        // New value for status column
        String status = "reserved";
        ContentValues value = new ContentValues();
        value.put(ParkingEntry.PARKING_STATUS, status);
        // Update row
        String selection = ParkingEntry.PARKING_ID + " = ?";
        String[] selectionArgs = { Integer.valueOf(id).toString() };
        int row = db.update(
                ParkingEntry.TABLE_PARKING,
                value,
                selection,
                selectionArgs);
        return row != 0;
    }

}
