package com.example.seedsmartapp.database;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "SeedSmart.db";
    private static final int DB_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE history (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId TEXT," +
                "crop TEXT," +
                "fertilizer TEXT," +
                "cost TEXT," +
                "time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS history");
        onCreate(db);
    }

    // =========================
    // ✅ INSERT DATA
    // =========================
    public void insertData(String userId, String crop, String fertilizer, String cost, String time) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("userId", userId);
        values.put("crop", crop);
        values.put("fertilizer", fertilizer);
        values.put("cost", cost);
        values.put("time", time);

        db.insert("history", null, values);
    }

    // =========================
    // ✅ GET ONLY USER DATA (NO userId RETURNED)
    // =========================
    public Cursor getUserData(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery(
                "SELECT id, crop, fertilizer, cost, time FROM history WHERE userId=? ORDER BY id DESC",
                new String[]{userId}
        );
    }

    // =========================
    // 🗑 DELETE SINGLE
    // =========================
    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("history", "id=?", new String[]{String.valueOf(id)});
    }

    // =========================
    // 🗑 DELETE ALL (ONLY CURRENT USER 🔥)
    // =========================
    public void deleteUserData(String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("history", "userId=?", new String[]{userId});
    }

    // (Optional - avoid using this in app)
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM history");
    }
}