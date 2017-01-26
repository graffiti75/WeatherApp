package com.example.rodrigo.weatherapp.model;

/**
 * WeatherDesc.java class.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class WeatherDesc {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private String value;

	//--------------------------------------------------
	// To String
	//--------------------------------------------------
	
	@Override
	public String toString() {
		return "WeatherDesc [value=" + value + "]";
	}
	
	//--------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------
	
	public String getValue() {
		return value;
	}
}