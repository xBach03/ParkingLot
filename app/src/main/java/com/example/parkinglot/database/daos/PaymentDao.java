package com.example.parkinglot.database.daos;

import android.provider.BaseColumns;

public class PaymentDao {
    // Payment table columns
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_PAYMENT = "payment";
        public static final String PAYMENT_ID = "id";
        public static final String PAYMENT_AMOUNT = "amount";
        public static final String PAYMENT_TIME = "time";
        public static final String PAYMENT_VEHICLEID = "vehicleId";
    }
}
