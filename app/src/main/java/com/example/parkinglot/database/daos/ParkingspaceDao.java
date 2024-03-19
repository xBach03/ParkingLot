package com.example.parkinglot.database.daos;

import android.provider.BaseColumns;

public class ParkingspaceDao {
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_PARKING = "parking";
        public static final String PARKING_ID = "id";
        public static final String PARKING_AVAILABILITY = "availability";
        public static final String PARKING_AREA = "area";
        public static final String PARKING_VEHICLEID = "vehicleId";

    }
}
