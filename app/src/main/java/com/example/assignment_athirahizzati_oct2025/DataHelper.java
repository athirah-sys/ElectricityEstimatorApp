package com.example.assignment_athirahizzati_oct2025;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "electricity.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE electricity (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "month TEXT NOT NULL, " +
                "units INTEGER NOT NULL, " +
                "totalCharges REAL NOT NULL, " +
                "rebate REAL NOT NULL, " +
                "finalCost REAL NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For now, drop and recreate (simple)
        db.execSQL("DROP TABLE IF EXISTS electricity");
        onCreate(db);
    }
}
