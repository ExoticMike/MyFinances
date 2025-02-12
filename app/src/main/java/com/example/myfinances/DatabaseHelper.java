package com.example.myfinances;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "financial_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "financial_objects";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ACCOUNT_NUMBER = "account_number";
    private static final String COLUMN_INITIAL_BALANCE = "initial_balance";
    private static final String COLUMN_CURRENT_BALANCE = "current_balance";
    private static final String COLUMN_INTEREST_RATE = "interest_rate";
    private static final String COLUMN_PAYMENT_AMOUNT = "payment_amount";
    private static final String COLUMN_TYPE = "type";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ACCOUNT_NUMBER + " TEXT, " +
                COLUMN_INITIAL_BALANCE + " REAL, " +
                COLUMN_CURRENT_BALANCE + " REAL, " +
                COLUMN_INTEREST_RATE + " REAL, " +
                COLUMN_PAYMENT_AMOUNT + " REAL, " +
                COLUMN_TYPE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertFinancialObject(FinancialObject obj, String type, Double interestRate, Double paymentAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ACCOUNT_NUMBER, obj.getAccountNumber());
        values.put(COLUMN_INITIAL_BALANCE, obj.getInitialBalance());
        values.put(COLUMN_CURRENT_BALANCE, obj.getCurrentBalance());
        values.put(COLUMN_INTEREST_RATE, interestRate);
        values.put(COLUMN_PAYMENT_AMOUNT, paymentAmount);
        values.put(COLUMN_TYPE, type);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
}

