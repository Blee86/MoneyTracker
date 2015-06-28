package com.developing.yosublee.moneytracker_dev.DBAadapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by yosublee on 5/5/15.
 */
public class DBAadapter {
    private static final String TAG = "DBAadapter";

    private final Context context;
    protected DatabaseHelper DBHelper;
    protected SQLiteDatabase db;

    // Variables for SQL //////////////////////////////////
    public static final String DATABASE_NAME = "MoneyTracker";
    public static final int DATABASE_VERSION = 1;

    // Queries for creating tables
    public static final String CREATE_TABLE_TRANSACTION = "CREATE TABLE IF NOT EXISTS moneyTransaction(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "amount INTEGER NOT NULL, " +
            "paymentmethod TEXT)";
    public static final String CREATE_TABLE_TRANSACTION_PAYMENTMETHOD = "CREATE TABLE IF NOT EXISTS " +
            "paymentMethod(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL)";

    public static final String CREATE_TABLE_ACCOUNT = "CREATE TABLE IF NOT EXISTS " +
            "account(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL, " +
            "type INTEGER, " +
            "balance INTEGER NOT NULL, " +
            "paymentDate INTEGER, " +
            "payFrom INTEGER, " +
            "billingDate INTEGER)";

    public static final String CREATE_TABLE_ACCOUNT_TYPE = "CREATE TABLE IF NOT EXISTS " +
            "accountTypes(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL, " +
            "scheduledSetUp TEXT NOT NULL)";

    public static final String CREATE_TABLE_TRANSACTION_CATEGORY = "CREATE TABLE IF NOT EXISTS " +
            "category(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL)";

    public static final String CREATE_TABLE_TRANSACTION_TAG = "CREATE TABLE IF NOT EXISTS " +
            "tag(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL)";

    public static final String CREATE_TABLE_BUDGET = "";

    public static final String CREATE_TABLE_CATEGORY = "";
    private static final String CREATE_TABLE_CATEGORY_SUB = "";

    // Initial Data in Tables //////////////////////////////////
    // Account Types
    public static final String INITIAL_DATA_ACCOUNT_TYPE1 = "INSERT OR IGNORE INTO accountTypes(name, scheduledSetUp) " +
            "VALUES('Bank Account', 'no')";
    public static final String INITIAL_DATA_ACCOUNT_TYPE2 = "INSERT OR IGNORE INTO accountTypes(name, scheduledSetUp) " +
            "VALUES('Credit Card', 'yes')";
    public static final String INITIAL_DATA_ACCOUNT_TYPE3 = "INSERT OR IGNORE INTO accountTypes(name, scheduledSetUp) " +
            "VALUES('Debit Card', 'no')";
    public static final String INITIAL_DATA_ACCOUNT_TYPE4 = "INSERT OR IGNORE INTO accountTypes(name, scheduledSetUp) " +
            "VALUES('Cash', 'no')";


    // Queries for deleting the tables
    public static final String DROP_TABLE_TRANSACTION = "DROP TABLE IF EXISTS moneyTransaction";

    public DBAadapter(Context context) {
        this.context = context;
        this.DBHelper = new DatabaseHelper(this.context);
    }

    public DBAadapter open() throws SQLException {
        this.db = this.DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.DBHelper.close();
    }

    ////////////////////////////////////////
    // Private Helper Class
    ////////////////////////////////////////

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_TRANSACTION);
            db.execSQL(CREATE_TABLE_TRANSACTION_PAYMENTMETHOD);
            db.execSQL(CREATE_TABLE_ACCOUNT);
            db.execSQL(CREATE_TABLE_ACCOUNT_TYPE);
            db.execSQL(INITIAL_DATA_ACCOUNT_TYPE1);
            db.execSQL(INITIAL_DATA_ACCOUNT_TYPE2);
            db.execSQL(INITIAL_DATA_ACCOUNT_TYPE3);
            db.execSQL(INITIAL_DATA_ACCOUNT_TYPE4);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from " + oldVersion + " to " + newVersion);

            // Destroy Old tables
            db.execSQL(DROP_TABLE_TRANSACTION);

            // Recreate new Database
            onCreate(db);
        }
    }
}
