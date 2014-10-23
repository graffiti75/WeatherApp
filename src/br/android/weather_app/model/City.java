package br.android.weather_app.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * City.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 18/10/2014
 */
@DatabaseTable
public class City extends Entity  {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	@DatabaseField(id = true)
	private Integer id;
	
	@DatabaseField
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
		return "City [id=" + id + ", city=" + city + "]";
	}

	//--------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------
	
	@Override
	public int getId() {
		return id;
	}
	@Override
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