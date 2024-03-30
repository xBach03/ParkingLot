package com.example.parkinglot.database.daos;

import android.provider.BaseColumns;

public class PaymentDao {
    // Payment table columns
    public static class PaymentEntry implements BaseColumns {
        public static final String TABLE_PAYMENT = "payment";
        public static final String PAYMENT_ID = "id";
        public static final String PAYMENT_BALANCE = "balance";
        public static final String PAYMENT_VALIDATIONDATE = "validationDate";
        public static final String PAYMENT_USERID = "userId";
    }
}
