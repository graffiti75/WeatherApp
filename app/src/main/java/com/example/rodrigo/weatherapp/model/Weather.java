package com.example.rodrigo.weatherapp.model;

import java.util.Calendar;
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
		String parts[] = date.split("-");
		String dayOfMonth = parts[2];
		String currentDate = getCurrentWeekday(Integer.valueOf(dayOfMonth));

		String posfixDate = date.replace("-", "/");
		currentDate = currentDate + ", " + posfixDate;

		return currentDate;
	}

	private String getCurrentWeekday(Integer adapterDayOfMonth) {
		// Gets the current date.
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		// Compares the current date with the date from the adapter.
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		if (adapterDayOfMonth != dayOfMonth) {
			int difference = adapterDayOfMonth - dayOfMonth;
			dayOfWeek += difference;
			if (dayOfWeek != 7) {
				dayOfWeek = dayOfWeek % 7;
			}
		}

		// Gets the proper day of week string.
		String dayInString = getWeekdayString(dayOfWeek);
		return dayInString;
	}

	private String getWeekdayString(Integer dayOfWeek) {
		String dayInString = "";
		switch (dayOfWeek) {
			case 2:
				dayInString = "Monday";
				break;
			case 3:
				dayInString = "Tuesday";
				break;
			case 4:
				dayInString = "Wednesday";
				break;
			case 5:
				dayInString = "Thursday";
				break;
			case 6:
				dayInString = "Friday";
				break;
			case 7:
				dayInString = "Saturday";
				break;
			case 1:
				dayInString = "Sunday";
				break;
		}
		return dayInString;
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