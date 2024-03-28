package com.example.parkinglot.database.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.parkinglot.database.entities.ParkingSpace;
import com.example.parkinglot.database.entities.Reservation;
import com.example.parkinglot.database.entities.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao {
    private SQLiteDatabase db;
    // Reservation table columns
    public static class ReservationEntry implements BaseColumns {
        public static final String TABLE_RESERVATION = "reservation";
        public static final String RESERVATION_ID = "id";
        public static final String RESERVATION_TIME = "time";
        public static final String RESERVATION_USERID = "userId";
        public static final String RESERVATION_PARKINGID = "parkingId";

    }
    public ReservationDao(SQLiteDatabase db) {
        this.db = db;
    }
    public List<Reservation> getAllReservations() {
        List<Reservation> allReservations = new ArrayList<>();

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                ReservationEntry.RESERVATION_ID + " DESC";
        Cursor cursor = db.query(
                ReservationEntry.RESERVATION_ID,   // The table to query
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
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(ReservationEntry.RESERVATION_ID));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow(ReservationEntry.RESERVATION_TIME));
                    LocalDateTime timeConverted = LocalDateTime.parse(time, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    int userId = cursor.getInt(cursor.getColumnIndexOrThrow(ReservationEntry.RESERVATION_USERID));
                    int parkingId = cursor.getInt(cursor.getColumnIndexOrThrow(ReservationEntry.RESERVATION_PARKINGID));
                    Reservation entry = new Reservation(id, timeConverted, userId, parkingId);
                    // Add ParkingSpace object to the list
                    allReservations.add(entry);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return allReservations;
    }
    public boolean reserve(int posId, User current) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ReservationEntry.RESERVATION_TIME, LocalDateTime.now().toString());
        values.put(ReservationEntry.RESERVATION_PARKINGID, posId);
        values.put(ReservationEntry.RESERVATION_USERID, current.getId());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ReservationEntry.TABLE_RESERVATION, null, values);

        return newRowId != -1;
    }
}
