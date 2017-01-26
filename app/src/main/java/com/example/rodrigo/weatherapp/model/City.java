package com.example.rodrigo.weatherapp.model;

/**
 * City.java class.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 24, 2017
 */
public class City {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Integer id;
	private String city;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public City() {}
	
	public City(Integer id, String city) {
		super();
		this.id = id;
		this.city = city;
	}
	
	//--------------------------------------------------
	// To String
	//--------------------------------------------------

	@Override
	public String toString() {
		return "City{" +
			"id=" + id +
			", city='" + city + '\'' +
			'}';
	}

	//--------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}