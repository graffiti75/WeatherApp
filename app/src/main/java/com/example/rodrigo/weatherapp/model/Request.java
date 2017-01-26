package com.example.rodrigo.weatherapp.model;

/**
 * Request.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class Request {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private String query;
	private String type;

	//--------------------------------------------------
	// To String
	//--------------------------------------------------
	
	@Override
	public String toString() {
		return "Request [query=" + query + ", type=" + type + "]";
	}
	
	//--------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------
	
	public String getQuery() {
		return query;
	}
}