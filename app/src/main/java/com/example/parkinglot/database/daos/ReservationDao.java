package com.example.parkinglot.database.daos;

import android.provider.BaseColumns;

import java.time.LocalDateTime;

public class ReservationDao {
    // Reservation table columns
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_RESERVATION = "reservation";
        public static final String RESERVATION_ID = "id";
        public static final String RESERVATION_TIME = "time";
        public static final String RESERVATION_USERID = "userId";
        public static final String RESERVATION_PARKINGID = "parkingId";

    }
}
