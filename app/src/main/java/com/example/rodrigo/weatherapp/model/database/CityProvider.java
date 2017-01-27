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

import com.example.rodrigo.weatherapp.AppConfiguration;

import java.util.HashMap;

/**
 * CityProvider.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 25, 2017
 */
public class CityProvider extends ContentProvider {

    //--------------------------------------------------
    // Statics
    //--------------------------------------------------

    // Projection map for a query.
    public static HashMap<String, String> sCrossoverMap;

    // Maps content URI "patterns" to the integer values that were set above.
    private static final UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AppConfiguration.PROVIDER_NAME, "arena", AppConfiguration.CITY);
        sUriMatcher.addURI(AppConfiguration.PROVIDER_NAME, "arena/#", AppConfiguration.CITY_ID);
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
        mDbHelper = new DbHelper(context, AppConfiguration.DATABASE_NAME,
            AppConfiguration.DATABASE_VERSION, AppConfiguration.TABLE_NAME, AppConfiguration.CREATE_TABLE);

        // Permissions to be writable.
        mDatabase = mDbHelper.getWritableDatabase();
        if (mDatabase == null)
            return false;
        else
            return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
        String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // The TABLE_NAME to query on.
        queryBuilder.setTables(AppConfiguration.TABLE_NAME);

        switch (sUriMatcher.match(uri)) {
            // Maps all database column names.
            case AppConfiguration.CITY:
                queryBuilder.setProjectionMap(sCrossoverMap);
                break;
            case AppConfiguration.CITY_ID:
                queryBuilder.appendWhere(AppConfiguration.ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Cursor cursor = queryBuilder.query(mDatabase, projection, selection, selectionArgs, null,
            null, sortOrder);

        // Register to watch a content URI for changes.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row = mDatabase.insert(AppConfiguration.TABLE_NAME, "", values);
        // If record is added successfully.
        if (row > 0) {
            Uri newUri = ContentUris.withAppendedId(AppConfiguration.CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;
        switch (sUriMatcher.match(uri)) {
            case AppConfiguration.CITY:
                count = mDatabase.update(AppConfiguration.TABLE_NAME, values, selection, selectionArgs);
                break;
            case AppConfiguration.CITY_ID:
                count = mDatabase.update(AppConfiguration.TABLE_NAME, values,
                    AppConfiguration.ID + " = " + uri.getLastPathSegment() +
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
            case AppConfiguration.CITY:
                // Delete all the records of the table.
                count = mDatabase.delete(AppConfiguration.TABLE_NAME, selection, selectionArgs);
                break;
            case AppConfiguration.CITY_ID:
                // Gets the id.
                String id = uri.getLastPathSegment();
                count = mDatabase.delete(AppConfiguration.TABLE_NAME, AppConfiguration.ID + " = " +
                    id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
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
            case AppConfiguration.CITY:
                return "vnd.android.cursor.dir/vnd.example.arena";
            // Get a particular crossover record.
            case AppConfiguration.CITY_ID:
                return "vnd.android.cursor.item/vnd.example.arena";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    public void openDatabase(Context context) {
        mDbHelper = new DbHelper(context, AppConfiguration.DATABASE_NAME,
            AppConfiguration.DATABASE_VERSION, AppConfiguration.TABLE_NAME, AppConfiguration.CREATE_TABLE);

        // Permissions to be writable.
        mDatabase = mDbHelper.getWritableDatabase();
        mDatabase = context.openOrCreateDatabase(AppConfiguration.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public void closeDatabase() {
        mDatabase.close();
    }
}