package com.example.rodrigo.weatherapp.api.model;

import java.util.List;

/**
 * Data.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class Data {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private List<CurrentCondition> current_condition;
	private List<Request> request;
	private List<Weather> weather;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------

	public Data() {}
	
	public Data(List<CurrentCondition> current_condition, List<Request> request,
		List<Weather> weather) {
		super();
		this.current_condition = current_condition;
		this.request = request;
		this.weather = weather;
	}

	//--------------------------------------------------
	// To String
	//--------------------------------------------------

	@Override
	public String toString() {
		return "Data [current_condition=" + current_condition + ", request="
			+ request + ", weather=" + weather + "]";
	}
	
	//--------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------
	
	public List<CurrentCondition> getCurrentCondition() {
		return current_condition;
	}
	public void setCurrentCondition(List<CurrentCondition> currentCondition) {
		this.current_condition = currentCondition;
	}
	
	public List<Request> getRequest() {
		return request;
	}
	public void setRequest(List<Request> request) {
		this.request = request;
	}

	public List<Weather> getWeather() {
		return weather;
	}
	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}	
}