package com.example.rodrigo.weatherapp.api.model;

/**
 * WeatherResponse.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class WeatherResponse {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Data data;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public WeatherResponse() {}
	
	public WeatherResponse(Data data) {
		super();
		this.data = data;
	}
	
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