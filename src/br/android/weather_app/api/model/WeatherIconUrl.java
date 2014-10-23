package br.android.weather_app.api.model;

/**
 * WeatherIconUrl.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class WeatherIconUrl {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private String value;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------

	public WeatherIconUrl() {}
	
	public WeatherIconUrl(String value) {
		super();
		this.value = value;
	}
	
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
	public void setValue(String value) {
		this.value = value;
	}
}