package com.example.parkinglot.database.daos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.content.ContentValues;

import com.example.parkinglot.database.entities.Payment;
import com.example.parkinglot.database.entities.User;

import java.util.Random;

public class PaymentDao {
    private SQLiteDatabase db;
    // Payment table columns
    public static class PaymentEntry implements BaseColumns {
        public static final String TABLE_PAYMENT = "payment";
        public static final String PAYMENT_ID = "id";
        public static final String PAYMENT_BALANCE = "balance";
        public static final String PAYMENT_VALIDATIONDATE = "validationDate";
        public static final String PAYMENT_USERID = "userId";
    }
    public PaymentDao(SQLiteDatabase db) {
        this.db = db;
    }
    public static String generateRandomString() {
        // Initialize a StringBuilder to build the random string
        StringBuilder stringBuilder = new StringBuilder();

        // Create an instance of Random class
        Random random = new Random();

        // Generate 16 random digits (4 groups of 4 digits)
        for (int i = 0; i < 16; i++) {
            // Generate a random digit (0 to 9)
            int digit = random.nextInt(10);

            // Append the digit to the StringBuilder
            stringBuilder.append(digit);

            // Insert a space after every group of 4 digits
            if ((i + 1) % 4 == 0 && i != 15) {
                stringBuilder.append(" ");
            }
        }

        // Convert the StringBuilder to a String and return
        return stringBuilder.toString();
    }
    public void generateOnCreate(User user) {

        Random random = new Random();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PaymentEntry.PAYMENT_ID, generateRandomString());
        values.put(PaymentEntry.PAYMENT_BALANCE, random.nextInt());
        values.put(PaymentEntry.PAYMENT_BALANCE, Math.round(random.nextDouble() * 1000 * 100.0) / 100.0);
        values.put(PaymentEntry.PAYMENT_VALIDATIONDATE, "12/24");
        values.put(PaymentEntry.PAYMENT_USERID, user.getId());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(PaymentEntry.TABLE_PAYMENT, null, values);
    }
    public Payment getCurrentPayment(User user) {
        Payment payment = new Payment();
        // Query all columns from payment table
        String[] projection = {
                PaymentEntry.PAYMENT_ID,
                PaymentEntry.PAYMENT_BALANCE,
                PaymentEntry.PAYMENT_VALIDATIONDATE,
                PaymentEntry.PAYMENT_USERID
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = PaymentEntry.PAYMENT_USERID + " = ?";
        String[] selectionArgs = { Integer.valueOf(user.getId()).toString() };

        Cursor cursor = db.query(
                PaymentEntry.TABLE_PAYMENT,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String paymentId = cursor.getString(cursor.getColumnIndexOrThrow(PaymentEntry.PAYMENT_ID));
                    int userId = cursor.getInt(cursor.getColumnIndexOrThrow(PaymentEntry.PAYMENT_USERID));
                    double balance = cursor.getDouble(cursor.getColumnIndexOrThrow(PaymentEntry.PAYMENT_BALANCE));
                    String validDate = cursor.getString(cursor.getColumnIndexOrThrow(PaymentEntry.PAYMENT_VALIDATIONDATE));
                    payment.setId(paymentId);
                    payment.setUserId(userId);
                    payment.setBalance(balance);
                    payment.setValidationDate(validDate);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return payment;
    }
    public Payment transactById(String toId, String fromId, Double amount) {
        // Query destination user's payment
        Payment toPayment = new Payment();
        // Query all columns from payment table
        String[] toProjection = {
                PaymentEntry.PAYMENT_ID,
                PaymentEntry.PAYMENT_BALANCE,
                PaymentEntry.PAYMENT_VALIDATIONDATE,
                PaymentEntry.PAYMENT_USERID
        };

        // Filter results WHERE "title" = 'My Title'
        String toQuerySelection = PaymentEntry.PAYMENT_ID + " = ?";
        String[] toQuerySelectionArgs = { toId };

        Cursor toCursor = db.query(
                PaymentEntry.TABLE_PAYMENT,   // The table to query
                toProjection,             // The array of columns to return (pass null to get all)
                toQuerySelection,              // The columns for the WHERE clause
                toQuerySelectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );
        try {
            if (toCursor != null && toCursor.moveToFirst()) {
                do {
                    String paymentId = toCursor.getString(toCursor.getColumnIndexOrThrow(PaymentEntry.PAYMENT_ID));
                    int userId = toCursor.getInt(toCursor.getColumnIndexOrThrow(PaymentEntry.PAYMENT_USERID));
                    double balance = toCursor.getDouble(toCursor.getColumnIndexOrThrow(PaymentEntry.PAYMENT_BALANCE));
                    String validDate = toCursor.getString(toCursor.getColumnIndexOrThrow(PaymentEntry.PAYMENT_VALIDATIONDATE));
                    toPayment.setId(paymentId);
                    toPayment.setUserId(userId);
                    toPayment.setBalance(balance);
                    toPayment.setValidationDate(validDate);
                } while (toCursor.moveToNext());
            }
        } finally {
            if (toCursor != null) {
                toCursor.close();
            }
        }

        toPayment.setBalance(toPayment.getBalance() + amount);

        ContentValues toValues = new ContentValues();

        // Update toUser balance
        toValues.put(PaymentEntry.PAYMENT_BALANCE, toPayment.getBalance());

        // Which row to update, based on the title
        String toUpdateSelection = PaymentEntry.PAYMENT_ID + " = ?";
        String[] toUpdateSelectionArgs = { toId };

        int toCount = db.update(
                PaymentEntry.TABLE_PAYMENT,
                toValues,
                toUpdateSelection,
                toUpdateSelectionArgs);

        // Update for fromUser
        String[] fromProjection = {
                PaymentEntry.PAYMENT_BALANCE
        };
        // Query the fromUser's ID
        String fromQuerySelection = PaymentEntry.PAYMENT_ID + " = ?";
        String[] fromQuerySelectionArgs = { fromId };

        Cursor fromCursor = db.query(
                PaymentEntry.TABLE_PAYMENT,   // The table to query
                fromProjection,             // The array of columns to return (pass null to get all)
                fromQuerySelection,              // The columns for the WHERE clause
                fromQuerySelectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );
        double balance = 0;
        try {
            if (fromCursor != null && fromCursor.moveToFirst()) {
                do {
                    balance = fromCursor.getDouble(fromCursor.getColumnIndexOrThrow(PaymentEntry.PAYMENT_BALANCE));
                } while (fromCursor.moveToNext());
            }
        } finally {
            if (fromCursor != null) {
                fromCursor.close();
            }
        }

        ContentValues fromValues = new ContentValues();
        // Update fromUser balance
        fromValues.put(PaymentEntry.PAYMENT_BALANCE, balance - amount);

        // Which row to update, based on the title
        String updateSelection = PaymentEntry.PAYMENT_ID + " = ?";
        String[] updateSelectionArgs = { fromId };

        int fromCount = db.update(
                PaymentEntry.TABLE_PAYMENT,
                fromValues,
                updateSelection,
                updateSelectionArgs);
        return toPayment;
    }
}
