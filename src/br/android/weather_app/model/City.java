package br.android.weather_app.model;

/**
 * City.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 18/10/2014
 */
public class City {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private String city;
	private Boolean isVisible;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public City() {}
	
	public City(String city, Boolean isVisible) {
		super();
		this.city = city;
		this.isVisible = isVisible;
	}
	
	//--------------------------------------------------
	// To String
	//--------------------------------------------------
	
	@Override
	public String toString() {
		return "City [city=" + city + ", isVisible=" + isVisible + "]";
	}

	//--------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public Boolean isVisible() {
		return isVisible;
	}
	public void setVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
}