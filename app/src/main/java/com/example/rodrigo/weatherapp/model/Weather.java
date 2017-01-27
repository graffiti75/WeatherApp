package com.example.rodrigo.weatherapp.model;

import com.example.rodrigo.weatherapp.presenter.utils.Utils;

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
		String parts[] = date.split("-");
		String dayOfMonth = parts[2];
		String currentDate = Utils.getCurrentWeekday(Integer.valueOf(dayOfMonth));

		String posfixDate = date.replace("-", "/");
		currentDate = currentDate + ", " + posfixDate;

		return currentDate;
	}

	public String getPrecipMM() {
		return precipMM.toString() + " mm";
	}

	public String getTempMaxC() {
		return tempMaxC.toString() + "ºC";
	}

	public String getTempMinC() {
		return tempMinC.toString() + "ºC";
	}

	public String getWeatherDesc() {
		return weatherDesc.get(0).getValue();
	}

	public String getWeatherIconUrl() {
		return weatherIconUrl.get(0).getValue();
	}

	public String getWinddirection() {
		return winddirection;
	}

	public String getWindspeedKmph() {
		return windspeedKmph.toString() + " km/h";
	}
}