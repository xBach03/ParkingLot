package com.example.parkinglot.database.daos;

import android.provider.BaseColumns;

public class VehicleDao {
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_VEHICLE = "vehicle";
        public static final String VEHICLE_ID = "id";
        public static final String VEHICLE_LICENSEPLATE = "licensePlate";
        public static final String VEHICLE_TYPE = "type";
        public static final String VEHICLE_COLOR = "color";
        public static final String VEHICLE_USERID = "userId";
    }

}
