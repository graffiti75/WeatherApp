package com.example.rodrigo.weatherapp.model;

import java.util.List;

/**
 * CurrentCondition.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class CurrentCondition {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	private Integer cloudcover;
	private Integer humidity;
	private String observation_time;
	private Double precipMM;
	private Integer pressure;
	private Integer temp_C;
	private Integer temp_F;
	private Integer visibility;
	private Integer weatherCode;
	private List<WeatherDesc> weatherDesc;
	private List<WeatherIconUrl> weatherIconUrl;
	private String winddir16Point;
	private Integer winddirDegree;
	private Integer windspeedKmph;
	private Integer windspeenMiles;

	//--------------------------------------------------
	// To String
	//--------------------------------------------------

	@Override
	public String toString() {
		return "CurrentCondition{" +
			"cloudcover=" + cloudcover +
			", humidity=" + humidity +
			", observation_time='" + observation_time + '\'' +
			", precipMM=" + precipMM +
			", pressure=" + pressure +
			", temp_C=" + temp_C +
			", temp_F=" + temp_F +
			", visibility=" + visibility +
			", weatherCode=" + weatherCode +
			", weatherDesc=" + weatherDesc +
			", weatherIconUrl=" + weatherIconUrl +
			", winddir16Point='" + winddir16Point + '\'' +
			", winddirDegree=" + winddirDegree +
			", windspeedKmph=" + windspeedKmph +
			", windspeenMiles=" + windspeenMiles +
			'}';
	}
}