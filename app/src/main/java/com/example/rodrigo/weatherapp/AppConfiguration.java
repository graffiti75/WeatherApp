package com.example.rodrigo.weatherapp;

import android.support.v4.app.NotificationCompatExtras;

/**
 * AppConfiguration.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 24, 2017
 */
public class AppConfiguration {

	//----------------------------------------------
	// Default Data
	//----------------------------------------------

	public static final String[] DEFAULT_DATA = new String[] {
		"Dublin, Ireland",
		"London, United Kingdom",
		"New York, United States Of America",
		"Barcelona, Spain",
		"Curitiba",
		"Rio",
		"Ponta Grossa"
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