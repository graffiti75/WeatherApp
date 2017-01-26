package com.example.rodrigo.weatherapp.model;

/**
 * WeatherResponse.java class.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class WeatherResponse {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Data data;

	//--------------------------------------------------
	// To String
	//--------------------------------------------------
	
	@Override
	public String toString() {
		return "WeatherResponse [data=" + data + "]";
	}

	//--------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------
	
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
}