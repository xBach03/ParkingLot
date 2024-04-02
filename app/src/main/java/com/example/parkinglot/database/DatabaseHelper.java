package com.example.parkinglot.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.parkinglot.database.daos.HistoryManagerDao;
import com.example.parkinglot.database.daos.ParkingSlotDao;
import com.example.parkinglot.database.daos.PaymentDao;
import com.example.parkinglot.database.daos.ReservationDao;
import com.example.parkinglot.database.daos.UserDao;
import com.example.parkinglot.database.daos.VehicleDao;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Singleton pattern
    private static DatabaseHelper instance;
    private DatabaseHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }
    public static final String DatabaseName = "LotParker";
    public static final int DatabaseVersion = 18;

    // Create user table string
    public static final String createTableUser = "CREATE TABLE " + UserDao.UserEntry.TABLE_USER + "( " +
            UserDao.UserEntry.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            UserDao.UserEntry.USER_USERNAME + " TEXT UNIQUE, " +
            UserDao.UserEntry.USER_PASSWORD + " TEXT);";

    public static final String createTableVehicle = "CREATE TABLE " + VehicleDao.VehicleEntry.TABLE_VEHICLE + "( " +
            VehicleDao.VehicleEntry.VEHICLE_ID + " TEXT PRIMARY KEY, " +
            VehicleDao.VehicleEntry.VEHICLE_COLOR + " TEXT, " +
            VehicleDao.VehicleEntry.VEHICLE_TYPE + " TEXT, " +
            VehicleDao.VehicleEntry.VEHICLE_USERID + " INTEGER, " +
            "FOREIGN KEY(" + VehicleDao.VehicleEntry.VEHICLE_USERID + ") REFERENCES " +
            UserDao.UserEntry.TABLE_USER + "(" + UserDao.UserEntry.USER_ID + "));";

    public static final String createTableReservation = "CREATE TABLE " + ReservationDao.ReservationEntry.TABLE_RESERVATION + "( " +
            ReservationDao.ReservationEntry.RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ReservationDao.ReservationEntry.RESERVATION_RESERVEDTIME + " TEXT, " +
            ReservationDao.ReservationEntry.RESERVATION_STARTTIME + " TEXT, " +
            ReservationDao.ReservationEntry.RESERVATION_USERID + " INTEGER, " +
            ReservationDao.ReservationEntry.RESERVATION_PARKINGID + " INTEGER, " +
            "FOREIGN KEY(" + ReservationDao.ReservationEntry.RESERVATION_USERID + ") REFERENCES " +
            UserDao.UserEntry.TABLE_USER + "(" + UserDao.UserEntry.USER_ID + "), " +
            "FOREIGN KEY(" + ReservationDao.ReservationEntry.RESERVATION_PARKINGID + ") REFERENCES " +
            ParkingSlotDao.ParkingEntry.TABLE_PARKING + "(" + ParkingSlotDao.ParkingEntry.PARKING_ID + "));";

    public static final String createTablePayment = "CREATE TABLE " + PaymentDao.PaymentEntry.TABLE_PAYMENT + "( " +
            PaymentDao.PaymentEntry.PAYMENT_ID + " TEXT PRIMARY KEY, " +
            PaymentDao.PaymentEntry.PAYMENT_BALANCE + " REAL, " +
            PaymentDao.PaymentEntry.PAYMENT_VALIDATIONDATE +" TEXT, " +
            PaymentDao.PaymentEntry.PAYMENT_USERID + " INTEGER, " +
            "FOREIGN KEY(" + PaymentDao.PaymentEntry.PAYMENT_USERID + ") REFERENCES " +
            UserDao.UserEntry.TABLE_USER + "(" + UserDao.UserEntry.USER_ID + "));";

    public static final String createTableParkingspace = "CREATE TABLE " + ParkingSlotDao.ParkingEntry.TABLE_PARKING + "( " +
            ParkingSlotDao.ParkingEntry.PARKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ParkingSlotDao.ParkingEntry.PARKING_AREA + " TEXT, " +
            ParkingSlotDao.ParkingEntry.PARKING_STATUS + " TEXT, " +
            ParkingSlotDao.ParkingEntry.PARKING_VEHICLETYPE + " TEXT, " +
            ParkingSlotDao.ParkingEntry.PARKING_VEHICLEID + " TEXT, " +
            ParkingSlotDao.ParkingEntry.PARKING_SPOT + " TEXT, " +
            "FOREIGN KEY(" + ParkingSlotDao.ParkingEntry.PARKING_VEHICLEID + ") REFERENCES " +
            VehicleDao.VehicleEntry.TABLE_VEHICLE + "(" + VehicleDao.VehicleEntry.VEHICLE_ID +"));";
    public static final String createTableHistory = "CREATE TABLE " + HistoryManagerDao.HistoryEntry.TABLE_HISTORY + "( " +
            HistoryManagerDao.HistoryEntry.HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            HistoryManagerDao.HistoryEntry.HISTORY_START + " TEXT, " +
            HistoryManagerDao.HistoryEntry.HISTORY_END + " TEXT, " +
            HistoryManagerDao.HistoryEntry.HISTORY_MONEYPAID + " REAL, " +
            HistoryManagerDao.HistoryEntry.HISTORY_VEHICLEID + " TEXT, " +
            HistoryManagerDao.HistoryEntry.HISTORY_USERID + " INT, " +
            "FOREIGN KEY(" + HistoryManagerDao.HistoryEntry.HISTORY_USERID + ") REFERENCES " +
            UserDao.UserEntry.TABLE_USER + "(" + UserDao.UserEntry.USER_ID + "), " +
            "FOREIGN KEY(" + HistoryManagerDao.HistoryEntry.HISTORY_VEHICLEID + ") REFERENCES " +
            VehicleDao.VehicleEntry.TABLE_VEHICLE + "(" + VehicleDao.VehicleEntry.VEHICLE_ID + "));";
    public static final String deleteTableUser = "DROP TABLE IF EXISTS " + UserDao.UserEntry.TABLE_USER;
    public static final String deleteTableVehicle = "DROP TABLE IF EXISTS " + VehicleDao.VehicleEntry.TABLE_VEHICLE;
    public static final String deleteTableReservation = "DROP TABLE IF EXISTS " + ReservationDao.ReservationEntry.TABLE_RESERVATION;
    public static final String deleteTablePayment = "DROP TABLE IF EXISTS " + PaymentDao.PaymentEntry.TABLE_PAYMENT;
    public static final String deleteTableParkingSpace = "DROP TABLE IF EXISTS " + ParkingSlotDao.ParkingEntry.TABLE_PARKING;
    public static final String deleteTableHistory = "DROP TABLE IF EXISTS " + HistoryManagerDao.HistoryEntry.TABLE_HISTORY;

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(deleteTableUser);
        db.execSQL(deleteTableVehicle);
        db.execSQL(deleteTableParkingSpace);
        db.execSQL(deleteTableReservation);
        db.execSQL(deleteTablePayment);
        db.execSQL(deleteTableHistory);
        onCreate(db);
    }

    // Oncreate -> create table user
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableUser);
        db.execSQL(createTableVehicle);
        db.execSQL(createTableParkingspace);
        db.execSQL(createTableReservation);
        db.execSQL(createTablePayment);
        db.execSQL(createTableHistory);
        ParkingSlotDao parker = new ParkingSlotDao(db);
        long result = parker.insertParkingspace();
    }
    public void setDeleteTableUser(SQLiteDatabase db){
        db.execSQL(deleteTableUser);
    }

}
