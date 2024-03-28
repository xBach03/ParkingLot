package com.example.parkinglot.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.parkinglot.database.daos.ParkingspaceDao;
import com.example.parkinglot.database.daos.PaymentDao;
import com.example.parkinglot.database.daos.ReservationDao;
import com.example.parkinglot.database.daos.UserDao;
import com.example.parkinglot.database.daos.VehicleDao;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;
    // Singleton pattern
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }
    public static final String DatabaseName = "LotParker";
    public static final int DatabaseVersion = 11;

    // Create user table string
    public static final String createTableUser = "CREATE TABLE " + UserDao.UserEntry.TABLE_USER + "( " +
            UserDao.UserEntry.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            UserDao.UserEntry.USER_USERNAME + " TEXT UNIQUE, " +
            UserDao.UserEntry.USER_PASSWORD + " TEXT);";
    public static final String deleteTableUser = "DROP TABLE IF EXISTS " + UserDao.UserEntry.TABLE_USER;
    public static final String createTableVehicle = "CREATE TABLE " + VehicleDao.VehicleEntry.TABLE_VEHICLE + "( " +
            VehicleDao.VehicleEntry.VEHICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            VehicleDao.VehicleEntry.VEHICLE_LICENSEPLATE + " TEXT UNIQUE, " +
            VehicleDao.VehicleEntry.VEHICLE_COLOR + " TEXT, " +
            VehicleDao.VehicleEntry.VEHICLE_TYPE + " TEXT, " +
            VehicleDao.VehicleEntry.VEHICLE_USERID + " INTEGER, " +
            "FOREIGN KEY(" + VehicleDao.VehicleEntry.VEHICLE_USERID + ") REFERENCES " +
            UserDao.UserEntry.TABLE_USER + "(" + UserDao.UserEntry.USER_ID + "));";
    public static final String deleteTableVehicle = "DROP TABLE IF EXISTS " + VehicleDao.VehicleEntry.TABLE_VEHICLE;
    public static final String createTableReservation = "CREATE TABLE " + ReservationDao.ReservationEntry.TABLE_RESERVATION + "( " +
            ReservationDao.ReservationEntry.RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ReservationDao.ReservationEntry.RESERVATION_TIME + " TEXT, " +
            ReservationDao.ReservationEntry.RESERVATION_USERID + " INTEGER, " +
            ReservationDao.ReservationEntry.RESERVATION_PARKINGID + " INTEGER, " +
            "FOREIGN KEY(" + ReservationDao.ReservationEntry.RESERVATION_USERID + ") REFERENCES " +
            UserDao.UserEntry.TABLE_USER + "(" + UserDao.UserEntry.USER_ID + "), " +
            "FOREIGN KEY(" + ReservationDao.ReservationEntry.RESERVATION_PARKINGID + ") REFERENCES " +
            ParkingspaceDao.ParkingEntry.TABLE_PARKING + "(" + ParkingspaceDao.ParkingEntry.PARKING_ID + "));";
    public static final String deleteTableReservation = "DROP TABLE IF EXISTS " + ReservationDao.ReservationEntry.TABLE_RESERVATION;
    public static final String createTablePayment = "CREATE TABLE " + PaymentDao.PaymentEntry.TABLE_PAYMENT + "( " +
            PaymentDao.PaymentEntry.PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PaymentDao.PaymentEntry.PAYMENT_AMOUNT + " TEXT, " +
            PaymentDao.PaymentEntry.PAYMENT_TIME +" TEXT, " +
            PaymentDao.PaymentEntry.PAYMENT_VEHICLEID + " INTEGER, " +
            "FOREIGN KEY(" + PaymentDao.PaymentEntry.PAYMENT_VEHICLEID + ") REFERENCES " +
            VehicleDao.VehicleEntry.TABLE_VEHICLE + "(" + VehicleDao.VehicleEntry.VEHICLE_ID + "));";
    public static final String deleteTablePayment = "DROP TABLE IF EXISTS " + PaymentDao.PaymentEntry.TABLE_PAYMENT;
    public static final String createTableParkingspace = "CREATE TABLE " + ParkingspaceDao.ParkingEntry.TABLE_PARKING + "( " +
            ParkingspaceDao.ParkingEntry.PARKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ParkingspaceDao.ParkingEntry.PARKING_AREA + " TEXT, " +
            ParkingspaceDao.ParkingEntry.PARKING_STATUS + " TEXT, " +
            ParkingspaceDao.ParkingEntry.PARKING_VEHICLETYPE + " TEXT, " +
            ParkingspaceDao.ParkingEntry.PARKING_VEHICLEID + " TEXT, " +
            ParkingspaceDao.ParkingEntry.PARKING_SPOT + " TEXT, " +
            "FOREIGN KEY(" + ParkingspaceDao.ParkingEntry.PARKING_VEHICLEID + ") REFERENCES " +
            VehicleDao.VehicleEntry.TABLE_VEHICLE + "(" + VehicleDao.VehicleEntry.VEHICLE_ID +"));";
    public static final String deleteTableParkingSpace = "DROP TABLE IF EXISTS " + ParkingspaceDao.ParkingEntry.TABLE_PARKING;

    public DatabaseHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(deleteTableUser);
        db.execSQL(deleteTableVehicle);
        db.execSQL(deleteTableParkingSpace);
        db.execSQL(deleteTableReservation);
        db.execSQL(deleteTablePayment);
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
        ParkingspaceDao parker = new ParkingspaceDao(db);
        long result = parker.insertParkingspace();
    }
    public void setDeleteTableUser(SQLiteDatabase db){
        db.execSQL(deleteTableUser);
    }

}
