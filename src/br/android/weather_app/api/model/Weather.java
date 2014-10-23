package br.android.weather_app.api.model;

import java.util.List;

/**
 * Weather.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
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
		return "Weather [date=" + date + ", precipMM=" + precipMM
			+ ", tempMaxC=" + tempMaxC + ", tempMaxF=" + tempMaxF
			+ ", tempMinC=" + tempMinC + ", tempMinF=" + tempMinF
			+ ", weatherCode=" + weatherCode + ", weatherDesc="
			+ weatherDesc + ", weatherIconUrl=" + weatherIconUrl
			+ ", winddir16Point=" + winddir16Point + ", winddirDegree="
			+ winddirDegree + ", winddirection=" + winddirection
			+ ", windspeedKmph=" + windspeedKmph + ", windspeedMiles="
			+ windspeedMiles + "]";
	}
	
	//--------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public Double getPrecipMM() {
		return precipMM;
	}
	public void setPrecipMM(Double precipMM) {
		this.precipMM = precipMM;
	}

	public Integer getTempMaxC() {
		return tempMaxC;
	}
	public void setTempMaxC(Integer tempMaxC) {
		this.tempMaxC = tempMaxC;
	}

	public Integer getTempMaxF() {
		return tempMaxF;
	}
	public void setTempMaxF(Integer tempMaxF) {
		this.tempMaxF = tempMaxF;
	}

	public Integer getTempMinC() {
		return tempMinC;
	}
	public void setTempMinC(Integer tempMinC) {
		this.tempMinC = tempMinC;
	}

	public Integer getTempMinF() {
		return tempMinF;
	}
	public void setTempMinF(Integer tempMinF) {
		this.tempMinF = tempMinF;
	}

	public Integer getWeatherCode() {
		return weatherCode;
	}
	public void setWeatherCode(Integer weatherCode) {
		this.weatherCode = weatherCode;
	}

	public List<WeatherDesc> getWeatherDesc() {
		return weatherDesc;
	}
	public void setWeatherDesc(List<WeatherDesc> weatherDesc) {
		this.weatherDesc = weatherDesc;
	}

	public List<WeatherIconUrl> getWeatherIconUrl() {
		return weatherIconUrl;
	}
	public void setWeatherIconUrl(List<WeatherIconUrl> weatherIconUrl) {
		this.weatherIconUrl = weatherIconUrl;
	}

	public String getWinddir16Point() {
		return winddir16Point;
	}
	public void setWinddir16Point(String winddir16Point) {
		this.winddir16Point = winddir16Point;
	}

	public Integer getWinddirDegree() {
		return winddirDegree;
	}
	public void setWinddirDegree(Integer winddirDegree) {
		this.winddirDegree = winddirDegree;
	}

	public String getWinddirection() {
		return winddirection;
	}
	public void setWinddirection(String winddirection) {
		this.winddirection = winddirection;
	}

	public Integer getWindspeedKmph() {
		return windspeedKmph;
	}
	public void setWindspeedKmph(Integer windspeedKmph) {
		this.windspeedKmph = windspeedKmph;
	}

	public Integer getWindspeedMiles() {
		return windspeedMiles;
	}
	public void setWindspeedMiles(Integer windspeedMiles) {
		this.windspeedMiles = windspeedMiles;
	}
}