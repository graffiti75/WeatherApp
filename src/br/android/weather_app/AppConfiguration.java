package br.android.weather_app;

/**
 * AppConfiguration.java class.
 * Stores the application configuration params.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class AppConfiguration {
	
	//----------------------------------------------
	// General Constants
	//----------------------------------------------	
	
	// The data format of the data from the API.
	public static final String FORMAT = "json";

	// The number of days to get the forecast.
	public static final String NUMBER_OF_DAYS = "5";
	
	// The Token of the World WeatherResponse Online API.
	public static final String KEY = "714be4de03b4ab70767d3335a6fa1651015c022f";
	
	//----------------------------------------------
	// Database Settings
	//----------------------------------------------

	// Any time you make changes to the database objects, you have to increase the database version.
	public static final int DATABASE_VERSION = 1;
	
	// The name of the database file.
	public static final String DATABASE_NAME = "weather_app.db";
	
	//----------------------------------------------
	// Logging
	//----------------------------------------------
	
	// Tag for common log output.
	public static final String COMMON_LOGGING_TAG = "weather_app";
}