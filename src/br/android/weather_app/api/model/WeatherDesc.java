package br.android.weather_app.api.model;

/**
 * WeatherDesc.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class WeatherDesc {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private String value;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------

	public WeatherDesc() {}
	
	public WeatherDesc(String value) {
		super();
		this.value = value;
	}
	
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

	public void setValue(String value) {
		this.value = value;
	}
}