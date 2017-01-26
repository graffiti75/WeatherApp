package com.example.rodrigo.weatherapp.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * DbHelper.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 24, 2016
 */
public class DbHelper extends SQLiteOpenHelper {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private String mCreateTable;
    private String mTableName;

    //--------------------------------------------------
    // Constructor
    //--------------------------------------------------

    public DbHelper(Context context, String databaseName, Integer databaseVersion, String tableName, String createTable) {
        super(context, databaseName, null, databaseVersion);
        mCreateTable = createTable;
        mTableName = tableName;
    }

    //--------------------------------------------------
    // SQLite Open Helper
    //--------------------------------------------------

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(mCreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
            + newVersion + ". Old data will be destroyed");
        db.execSQL("DROP TABLE IF EXISTS " + mTableName);
        onCreate(db);
    }
}