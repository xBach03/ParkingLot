package com.example.parkinglot.database.daos;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.parkinglot.database.entities.Payment;
import com.example.parkinglot.database.entities.User;

import java.time.LocalDateTime;

public class TransactionDao {
    private SQLiteDatabase db;
    public static class TransactionEntry implements BaseColumns {
        public static final String TABLE_TRANSACTION = "transactions";
        public static final String TRANSACTION_ID = "id";
        public static final String TRANSACTION_AMOUNT = "amount";
        public static final String TRANSACTION_TIME = "time";
        public static final String TRANSACTION_FROMUSER = "fromUser";
        public static final String TRANSACTION_TOUSER = "toUser";
    }
    public TransactionDao(SQLiteDatabase db) {
        this.db = db;
    }
    public boolean insertTransaction(String fromUser, String toUser, double amount) {
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(TransactionEntry.TRANSACTION_AMOUNT, amount);
        values.put(TransactionEntry.TRANSACTION_FROMUSER, fromUser);
        values.put(TransactionEntry.TRANSACTION_TOUSER, toUser);
        values.put(TransactionEntry.TRANSACTION_TIME, LocalDateTime.now().toString());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TransactionEntry.TABLE_TRANSACTION, null, values);
        return newRowId != -1;
    }
}
