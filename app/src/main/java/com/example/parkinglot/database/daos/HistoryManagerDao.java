package com.example.parkinglot.database.daos;

import android.provider.BaseColumns;

public class HistoryManagerDao {
    public static class HistoryEntry implements BaseColumns {
        public static final String TABLE_HISTORY = "history";
        public static final String HISTORY_ID = "id";
        public static final String HISTORY_START = "startTime";
        public static final String HISTORY_END = "endTime";
        public static final String HISTORY_MONEYPAID = "moneyPaid";
        public static final String HISTORY_USERID = "userId";
        public static final String HISTORY_VEHICLEID = "vehicleId";
    }
}
