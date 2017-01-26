package com.example.rodrigo.weatherapp.model;

import java.util.List;

/**
 * Data.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class Data {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private List<CurrentCondition> current_condition;
	private List<Request> request;
	private List<Weather> weather;

	//--------------------------------------------------
	// To String
	//--------------------------------------------------

	@Override
	public String toString() {
		return "Data{" +
			"current_condition=" + current_condition +
			", request=" + request +
			", weather=" + weather +
			'}';
	}

	//--------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------
	
	public List<Request> getRequest() {
		return request;
	}

	public List<Weather> getWeather() {
		return weather;
	}
}