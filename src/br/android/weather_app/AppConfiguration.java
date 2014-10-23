package br.android.weather_app;

/**
 * Stores the application configuration params.
 * 
 * @author Rodrigo Cericatto
 * @since 14/10/2014
 */
public class AppConfiguration {
	
	//----------------------------------------------
	// General Constants
	//----------------------------------------------	
	
	// The API url to call the weather informations.
	public static final String API_URL = "http://api.worldweatheronline.com/free/v1";

	// The service url to get the weather informations.
	public static final String SERVICE_URL = "/weather.ashx?q=London&format=json&num_of_days=5&key=";
	
	// The Token of the World WeatherResponse Online API.
	public static final String TOKEN = "714be4de03b4ab70767d3335a6fa1651015c022f";

	//----------------------------------------------
	// Database Settings
	//----------------------------------------------

	// Any time you make changes to the database objects, you have to increase the database version.
	public static final int DATABASE_VERSION = 1;
	
	// The name of the database file.
	public static final String DATABASE_NAME = "weather_app.db";
	
	// Flag to get data from server or from database.
	public static Boolean sDatabaseNeedsUpdate = false;
	
	//----------------------------------------------
	// Logging
	//----------------------------------------------
	
	// Tag for common log output.
	public static final String COMMON_LOGGING_TAG = "weather_app";
}