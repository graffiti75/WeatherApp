package com.example.rodrigo.weatherapp;

import android.net.Uri;

/**
 * AppConfiguration.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 24, 2017
 */
public class AppConfiguration {

	//--------------------------------------------------
	// Database
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

	//----------------------------------------------
	// Default Data
	//----------------------------------------------

	public static final String[] DEFAULT_DATA = new String[] {
		"Dublin, Ireland",
		"London, United Kingdom",
		"New York, United States Of America",
		"Barcelona, Spain"
	};

	//----------------------------------------------
	// General Constants
	//----------------------------------------------	

	public static final String BASE_URL = "http://api.worldweatheronline.com";

	// The data format of the data from the API.
	public static final String FORMAT = "json";

	// The number of days to get the forecast.
	public static final String NUMBER_OF_DAYS = "5";
	
	// The Token of the World WeatherResponse Online API.
	public static final String KEY = "714be4de03b4ab70767d3335a6fa1651015c022f";

	//----------------------------------------------
	// Extras
	//----------------------------------------------

	public static final String CITY_NAME_EXTRA = "city_name_extra";

	//----------------------------------------------
	// Logging
	//----------------------------------------------
	
	// Tag for common log output.
	public static final String TAG = "weather_app";
}