package com.example.parkinglot.database.daos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.parkinglot.database.entities.HistoryManager;
import com.example.parkinglot.database.entities.Reservation;
import com.example.parkinglot.database.entities.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoryManagerDao {
    SQLiteDatabase db;
    public static class HistoryEntry implements BaseColumns {
        public static final String TABLE_HISTORY = "history";
        public static final String HISTORY_ID = "id";
        public static final String HISTORY_START = "startTime";
        public static final String HISTORY_END = "endTime";
        public static final String HISTORY_MONEYPAID = "moneyPaid";
        public static final String HISTORY_USERID = "userId";
        public static final String HISTORY_VEHICLEID = "vehicleId";
    }
    public HistoryManagerDao(SQLiteDatabase db) {
        this.db = db;
    }
    public void generateOnCreate(User user) {
        for(int i = 0; i < 10; i++) {
            ContentValues values = new ContentValues();
            values.put(HistoryEntry.HISTORY_START, LocalDateTime.now().minusHours(i).toString());
            values.put(HistoryEntry.HISTORY_END, LocalDateTime.now().plusHours(i).toString());
            values.put(HistoryEntry.HISTORY_MONEYPAID, 13.45);
            values.put(HistoryEntry.HISTORY_USERID, user.getId());
            values.put(HistoryEntry.HISTORY_VEHICLEID, "29F1-59132");
            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(HistoryEntry.TABLE_HISTORY, null, values);
        }

    }
    public List<HistoryManager> getAll(User user) {

        List<HistoryManager> historyList = new ArrayList<>();
        // Query all from History table
        String[] projection = {
                HistoryEntry.HISTORY_ID,
                HistoryEntry.HISTORY_USERID,
                HistoryEntry.HISTORY_START,
                HistoryEntry.HISTORY_END,
                HistoryEntry.HISTORY_MONEYPAID,
                HistoryEntry.HISTORY_VEHICLEID
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = HistoryEntry.HISTORY_USERID + " = ?";
        String[] selectionArgs = { Integer.valueOf(user.getId()).toString() };


        Cursor cursor = db.query(
                HistoryEntry.TABLE_HISTORY,  // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(HistoryEntry.HISTORY_ID));
                    String startTime = cursor.getString(cursor.getColumnIndexOrThrow(HistoryEntry.HISTORY_START));
                    String endTime = cursor.getString(cursor.getColumnIndexOrThrow(HistoryEntry.HISTORY_END));
                    LocalDateTime timeStartConverted = LocalDateTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    LocalDateTime timeEndConverted = LocalDateTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    int userId = cursor.getInt(cursor.getColumnIndexOrThrow(HistoryEntry.HISTORY_USERID));
                    double moneyPaid = cursor.getDouble(cursor.getColumnIndexOrThrow(HistoryEntry.HISTORY_MONEYPAID));
                    String vehicleId = cursor.getString(cursor.getColumnIndexOrThrow(HistoryEntry.HISTORY_VEHICLEID));
                    HistoryManager entry = new HistoryManager(id, timeStartConverted, timeEndConverted, moneyPaid, userId, vehicleId);
                    // Add ParkingSpace object to the list
                    historyList.add(entry);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return historyList;
    }
}
