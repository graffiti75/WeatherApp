package com.example.rodrigo.weatherapp.model.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * CityProvider.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 25, 2017
 */
public class CityProvider extends ContentProvider {

    //--------------------------------------------------
    // Constants
    //--------------------------------------------------

    // Fields for my content provider.
    public static final String PROVIDER_NAME = "com.example.rodrigo.weatherapp.model.database.CityProvider";
    public static final String CONTENT_PROVIDER_URL = "content://" + PROVIDER_NAME + "/arena";
    public static final Uri CONTENT_URI = Uri.parse(CONTENT_PROVIDER_URL);

    // Fields for the database.
    public static final String ID = "id";
    public static final String CITY_NAME = "city";

    // Database creation.
    public static final String DATABASE_NAME = "arena";
    public static final String TABLE_NAME = "city";
    public static final int DATABASE_VERSION = 1;
    public static final String CREATE_TABLE =
        "create table " + TABLE_NAME + " (id integer primary key, city text not null)";

    // Integer values used in content URI.
    public static final int CITY = 1;
    public static final int CITY_ID = 2;

    //--------------------------------------------------
    // Statics
    //--------------------------------------------------

    // Projection map for a query.
    public static HashMap<String, String> sCrossoverMap;

    // Maps content URI "patterns" to the integer values that were set above.
    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(PROVIDER_NAME, "arena", CITY);
        sUriMatcher.addURI(PROVIDER_NAME, "arena/#", CITY_ID);
    }

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    // Database declarations.
    private SQLiteDatabase mDatabase;
    private DbHelper mDbHelper;

    //--------------------------------------------------
    // Content Provider
    //--------------------------------------------------

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDbHelper = new DbHelper(context, DATABASE_NAME, DATABASE_VERSION, TABLE_NAME, CREATE_TABLE);

        // Permissions to be writable.
        mDatabase = mDbHelper.getWritableDatabase();
        if (mDatabase == null)
            return false;
        else
            return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // The TABLE_NAME to query on.
        queryBuilder.setTables(TABLE_NAME);

        switch (sUriMatcher.match(uri)) {
            // Maps all database column names.
            case CITY:
                queryBuilder.setProjectionMap(sCrossoverMap);
                break;
            case CITY_ID:
                queryBuilder.appendWhere(ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Cursor cursor = queryBuilder.query(mDatabase, projection, selection, selectionArgs, null, null, sortOrder);

        // Register to watch a content URI for changes.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row = mDatabase.insert(TABLE_NAME, "", values);
        // If record is added successfully.
        if (row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;
        switch (sUriMatcher.match(uri)) {
            case CITY:
                count = mDatabase.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            case CITY_ID:
                count = mDatabase.update(TABLE_NAME, values, ID + " = " + uri.getLastPathSegment() +
                    (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (sUriMatcher.match(uri)) {
            case CITY:
                // Delete all the records of the table.
                count = mDatabase.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case CITY_ID:
                // Gets the id.
                String id = uri.getLastPathSegment();
                count = mDatabase.delete(TABLE_NAME, ID + " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" +
                    selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            // Get all crossover records.
            case CITY:
                return "vnd.android.cursor.dir/vnd.example.arena";
            // Get a particular crossover record.
            case CITY_ID:
                return "vnd.android.cursor.item/vnd.example.arena";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    public void openDatabase(Context context) {
        mDbHelper = new DbHelper(context, DATABASE_NAME, DATABASE_VERSION, TABLE_NAME, CREATE_TABLE);

        // Permissions to be writable.
        mDatabase = mDbHelper.getWritableDatabase();
        mDatabase = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public void closeDatabase() {
        mDatabase.close();
    }
}