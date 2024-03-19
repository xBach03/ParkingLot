package com.example.parkinglot.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.parkinglot.database.daos.ParkingspaceDao;
import com.example.parkinglot.database.daos.PaymentDao;
import com.example.parkinglot.database.daos.ReservationDao;
import com.example.parkinglot.database.daos.UserDao;
import com.example.parkinglot.database.daos.VehicleDao;
import com.example.parkinglot.database.entities.Payment;
import com.example.parkinglot.database.entities.Reservation;
import com.example.parkinglot.database.entities.User;

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
    public static final int DatabaseVersion = 1;

    // Create user table string
    public static final String createTableUser = "CREATE TABLE " + UserDao.FeedEntry.TABLE_USER + "( " +
            UserDao.FeedEntry.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            UserDao.FeedEntry.USER_USERNAME + " TEXT UNIQUE, " +
            UserDao.FeedEntry.USER_PASSWORD + " TEXT);";
    public static final String deleteTableUser = "DROP TABLE IF EXISTS " + UserDao.FeedEntry.TABLE_USER;
    public static final String createTableVehicle = "CREATE TABLE " + VehicleDao.FeedEntry.TABLE_VEHICLE + "( " +
            VehicleDao.FeedEntry.VEHICLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            VehicleDao.FeedEntry.VEHICLE_LICENSEPLATE + " TEXT UNIQUE, " +
            VehicleDao.FeedEntry.VEHICLE_COLOR + " TEXT, " +
            VehicleDao.FeedEntry.VEHICLE_TYPE + " TEXT, " +
            VehicleDao.FeedEntry.VEHICLE_USERID + " INTEGER, " +
            "FOREIGN KEY(" + VehicleDao.FeedEntry.VEHICLE_USERID + ") REFERENCES " +
            UserDao.FeedEntry.TABLE_USER + "(" + UserDao.FeedEntry.USER_ID + "));";
    public static final String createTableReservation = "CREATE TABLE " + ReservationDao.FeedEntry.TABLE_RESERVATION + "( " +
            ReservationDao.FeedEntry.RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ReservationDao.FeedEntry.RESERVATION_TIME + " TEXT, " +
            ReservationDao.FeedEntry.RESERVATION_USERID + " INTEGER, " +
            ReservationDao.FeedEntry.RESERVATION_PARKINGID + " INTEGER, " +
            "FOREIGN KEY(" + ReservationDao.FeedEntry.RESERVATION_USERID + ") REFERENCES " +
            UserDao.FeedEntry.TABLE_USER + "(" + UserDao.FeedEntry.USER_ID + "), " +
            "FOREIGN KEY(" + ReservationDao.FeedEntry.RESERVATION_PARKINGID + ") REFERENCES " +
            ParkingspaceDao.FeedEntry.TABLE_PARKING + "(" + ParkingspaceDao.FeedEntry.PARKING_ID + "));";
    public static final String createTablePayment = "CREATE TABLE " + PaymentDao.FeedEntry.TABLE_PAYMENT + "( " +
            PaymentDao.FeedEntry.PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PaymentDao.FeedEntry.PAYMENT_AMOUNT + " TEXT, " +
            PaymentDao.FeedEntry.PAYMENT_TIME +" TEXT, " +
            PaymentDao.FeedEntry.PAYMENT_VEHICLEID + " INTEGER, " +
            "FOREIGN KEY(" + PaymentDao.FeedEntry.PAYMENT_VEHICLEID + ") REFERENCES " +
            VehicleDao.FeedEntry.TABLE_VEHICLE + "(" + VehicleDao.FeedEntry.VEHICLE_ID + "));";
    public static final String createTableParkingspace = "CREATE TABLE " + ParkingspaceDao.FeedEntry.TABLE_PARKING + "( " +
            ParkingspaceDao.FeedEntry.PARKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ParkingspaceDao.FeedEntry.PARKING_AREA + " TEXT, " +
            ParkingspaceDao.FeedEntry.PARKING_AVAILABILITY + " TEXT, " +
            ParkingspaceDao.FeedEntry.PARKING_VEHICLEID + " TEXT, " +
            "FOREIGN KEY(" + ParkingspaceDao.FeedEntry.PARKING_VEHICLEID + ") REFERENCES " +
            VehicleDao.FeedEntry.TABLE_VEHICLE + "(" + VehicleDao.FeedEntry.VEHICLE_ID +"));";


    public DatabaseHelper(Context context) {
        super(context, DatabaseName, null, DatabaseVersion);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserDao.FeedEntry.TABLE_USER);
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
    }
    public void setDeleteTableUser(SQLiteDatabase db){
        db.execSQL(deleteTableUser);
    }
}
