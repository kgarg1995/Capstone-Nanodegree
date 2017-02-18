package com.iotalabs.physics_101.handler.DBHandler;

import com.iotalabs.physics_101.dbcontracts.SubTopicsContract;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by karangarg on 05/02/17.
 */

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "physics.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + SubTopicsContract.SubTopicsEntry.TABLE_NAME
            + " (" + SubTopicsContract.SubTopicsEntry._ID + " INTEGER," +
            SubTopicsContract.SubTopicsEntry.HOURS_REQUIRED + " TEXT NOT NULL," +
            SubTopicsContract.SubTopicsEntry.SUB_TOPIC_DESCRIPTION + " TEXT NOT NULL," +
            SubTopicsContract.SubTopicsEntry.IMAGE_URL + " TEXT NOT NULL," +
            SubTopicsContract.SubTopicsEntry.SUB_TOPIC_NAME + " REAL NOT NULL," +
            SubTopicsContract.SubTopicsEntry.THUMBNAIL_URL + " TEXT NOT NULL, " +
            " UNIQUE (" + SubTopicsContract.SubTopicsEntry._ID + ") ON CONFLICT REPLACE );";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SubTopicsContract.SubTopicsEntry.TABLE_NAME);
        onCreate(db);
    }
}
