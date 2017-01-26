package com.example.rodrigo.weatherapp.model;

/**
 * WeatherIconUrl.java class.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class WeatherIconUrl {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private String value;

	//--------------------------------------------------
	// To String
	//--------------------------------------------------
	
	@Override
	public String toString() {
		return "WeatherIconUrl [value=" + value + "]";
	}
	
	//--------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------
	
	public String getValue() {
		return value;
	}
}