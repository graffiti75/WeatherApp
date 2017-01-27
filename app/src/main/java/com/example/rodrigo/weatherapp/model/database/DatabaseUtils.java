package com.example.rodrigo.weatherapp.model.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.rodrigo.weatherapp.controller.utils.Utils;
import com.example.rodrigo.weatherapp.model.City;

import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseUtils.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class DatabaseUtils {

    //--------------------------------------------------
    // Low Level Database Methods
    //--------------------------------------------------

    public static Boolean insertCityList(Context context, List<City> list) {
        ContentValues values = new ContentValues();
        Uri uri = null;
        for (City item : list) {
            values.put(CityProvider.ID, item.getId());
            values.put(CityProvider.CITY_NAME, item.getCity());
            uri = context.getContentResolver().insert(CityProvider.CONTENT_URI, values);
        }

        Boolean result = false;
        if (!Utils.isEmpty(uri.toString())) {
            result = true;
        }
        return result;
    }

    public static Boolean deleteCity(Context context, Integer id) {
        Uri uri = Uri.parse(CityProvider.CONTENT_PROVIDER_URL);
        Boolean result = false;
        String where = "id = " + id;
        int rowsAffected = context.getContentResolver().delete(uri, where, null);

        if (rowsAffected > 0) result = true;
        return result;
    }

    public static City getCity(Context context, Integer _id) {
        Uri cities = Uri.parse(CityProvider.CONTENT_PROVIDER_URL);
        City city = null;
        Cursor cursor = context.getContentResolver().query(cities, null,
            CityProvider.ID + " = " + _id, null, null);
        if (cursor != null) {
            if (!cursor.moveToFirst()) {
                city = null;
            } else {
                do {
                    city = getCityFromCursor(cursor);
                } while (cursor.moveToNext());
            }
        }
        return city;
    }

    public static List<City> getCityList(Context context) {
        Uri cities = Uri.parse(CityProvider.CONTENT_PROVIDER_URL);
        List<City> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(cities, null, null, null, null);
        if (cursor != null) {
            if (!cursor.moveToFirst()) {
                list = null;
            } else {
                do {
                    list.add(getCityFromCursor(cursor));
                } while (cursor.moveToNext());
            }
        }
        return list;
    }

    //--------------------------------------------------
    // Cursor Methods
    //--------------------------------------------------

    private static City getCityFromCursor(Cursor cursor) {
        City city = new City();
        Integer id = cursor.getInt(cursor.getColumnIndex(CityProvider.ID));
        String cityName = cursor.getString(cursor.getColumnIndex(CityProvider.CITY_NAME));

        city.setId(id);
        city.setCity(cityName);

        return city;
    }

    //--------------------------------------------------
    // High Level Database Methods
    //--------------------------------------------------

    public static CityProvider openDatabase(Activity activity) {
        CityProvider database = new CityProvider();
        database.openDatabase(activity);
        return database;
    }

    public static void closeDatabase(CityProvider database) {
        database.closeDatabase();
    }

    public static City getCity(Activity activity) {
        openDatabase(activity);
        City city = getCity(activity, 1);
        return city;
    }
}