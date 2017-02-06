package com.iotalabs.physics_101.contentproviders;

import com.iotalabs.physics_101.dbcontracts.SubTopicsContract;
import com.iotalabs.physics_101.handler.DBHandler.DBHandler;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by karangarg on 05/02/17.
 */

public class Provider extends ContentProvider {

    public static final UriMatcher mUriMatcher = buildUriMatcher();
    private DBHandler dbHandler;

    static final int SUB_TOPICS = 100;

    static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SubTopicsContract.CONTENT_AUTHORITY;
        uriMatcher.addURI(authority, SubTopicsContract.PATH_SUB_TOPICS, SUB_TOPICS);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        dbHandler = new DBHandler(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = mUriMatcher.match(uri);

        switch (match) {
        case SUB_TOPICS:
            return SubTopicsContract.SubTopicsEntry.CONTENT_TYPE;
        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
        String sortOrder) {
        Cursor returnCursor;
        switch (mUriMatcher.match(uri)) {
        case SUB_TOPICS: {
            returnCursor = dbHandler.getReadableDatabase().query(
                SubTopicsContract.SubTopicsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
            );
            break;
        }

        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase sqLiteDatabase = dbHandler.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
        case SUB_TOPICS: {
            long _id = sqLiteDatabase.insert(SubTopicsContract.SubTopicsEntry.TABLE_NAME, null, values);
            if (_id > 0) {
                returnUri = SubTopicsContract.SubTopicsEntry.buildSubTopicsUri(_id);
            } else {
                throw new SQLiteException("Failed to insert row into " + uri);
            }
            break;
        }

        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        sqLiteDatabase.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase sqLiteDatabase = dbHandler.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        int rowsDeleted;

        if (null == selection) {
            selection = "1";
        }

        switch (match) {
        case SUB_TOPICS:
            rowsDeleted = sqLiteDatabase.delete(
                SubTopicsContract.SubTopicsEntry.TABLE_NAME, selection, selectionArgs);
            break;
        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        sqLiteDatabase.close();
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase sqLiteDatabase = dbHandler.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
        case SUB_TOPICS:
            rowsUpdated = sqLiteDatabase.update(
                SubTopicsContract.SubTopicsEntry.TABLE_NAME, values, selection, selectionArgs);
            break;
        default:
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        sqLiteDatabase.close();
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase sqLiteDatabase = dbHandler.getWritableDatabase();
        final int match = mUriMatcher.match(uri);
        switch (match) {
        case SUB_TOPICS:
            sqLiteDatabase.beginTransaction();
            int returnCount = 0;
            try {
                for (ContentValues value : values) {
                    long _id = sqLiteDatabase.insert(SubTopicsContract.SubTopicsEntry.TABLE_NAME, null, value);
                    if (_id != -1) {
                        ++returnCount;
                    }
                }
                sqLiteDatabase.setTransactionSuccessful();
            } finally {
                sqLiteDatabase.endTransaction();
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return returnCount;
        default:
            return super.bulkInsert(uri, values);
        }
    }
}
