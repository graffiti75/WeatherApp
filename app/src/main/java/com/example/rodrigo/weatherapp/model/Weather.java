package com.example.rodrigo.weatherapp.model;

import java.util.List;

/**
 * Weather.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class Weather {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private String date;
	private Double precipMM;
	private Integer tempMaxC;
	private Integer tempMaxF;
	private Integer tempMinC;
	private Integer tempMinF;
	private Integer weatherCode;
	private List<WeatherDesc> weatherDesc;
	private List<WeatherIconUrl> weatherIconUrl;
	private String winddir16Point;
	private Integer winddirDegree;
	private String winddirection;
	private Integer windspeedKmph;
	private Integer windspeedMiles;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------

	public Weather() {}

	public Weather(String date, Double precipMM, Integer tempMaxC,
		Integer tempMaxF, Integer tempMinC, Integer tempMinF,
		Integer weatherCode, List<WeatherDesc> weatherDesc,
		List<WeatherIconUrl> weatherIconUrl, String winddir16Point,
		Integer winddirDegree, String winddirection, Integer windspeedKmph,
		Integer windspeedMiles) {
		super();
		this.date = date;
		this.precipMM = precipMM;
		this.tempMaxC = tempMaxC;
		this.tempMaxF = tempMaxF;
		this.tempMinC = tempMinC;
		this.tempMinF = tempMinF;
		this.weatherCode = weatherCode;
		this.weatherDesc = weatherDesc;
		this.weatherIconUrl = weatherIconUrl;
		this.winddir16Point = winddir16Point;
		this.winddirDegree = winddirDegree;
		this.winddirection = winddirection;
		this.windspeedKmph = windspeedKmph;
		this.windspeedMiles = windspeedMiles;
	}
	
	//--------------------------------------------------
	// To String
	//--------------------------------------------------

	@Override
	public String toString() {
		return "Weather{" +
			"date='" + date + '\'' +
			", precipMM=" + precipMM +
			", tempMaxC=" + tempMaxC +
			", tempMaxF=" + tempMaxF +
			", tempMinC=" + tempMinC +
			", tempMinF=" + tempMinF +
			", weatherCode=" + weatherCode +
			", weatherDesc=" + weatherDesc +
			", weatherIconUrl=" + weatherIconUrl +
			", winddir16Point='" + winddir16Point + '\'' +
			", winddirDegree=" + winddirDegree +
			", winddirection='" + winddirection + '\'' +
			", windspeedKmph=" + windspeedKmph +
			", windspeedMiles=" + windspeedMiles +
			'}';
	}

	//--------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------

	public String getDate() {
		return date;
	}

	public Double getPrecipMM() {
		return precipMM;
	}

	public Integer getTempMaxC() {
		return tempMaxC;
	}

	public Integer getTempMinC() {
		return tempMinC;
	}

	public List<WeatherDesc> getWeatherDesc() {
		return weatherDesc;
	}

	public List<WeatherIconUrl> getWeatherIconUrl() {
		return weatherIconUrl;
	}

	public String getWinddirection() {
		return winddirection;
	}

	public Integer getWindspeedKmph() {
		return windspeedKmph;
	}
}