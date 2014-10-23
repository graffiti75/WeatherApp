package br.android.weather_app.api.model;

import java.util.List;

/**
 * CurrentCondition.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
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
	// Constructor
	//--------------------------------------------------

	public CurrentCondition() {}
	
	public CurrentCondition(Integer cloudcover, Integer humidity,
		String observation_time, Double precipMM, Integer pressure,
		Integer temp_C, Integer temp_F, Integer visibility,
		Integer weatherCode, List<WeatherDesc> weatherDesc,
		List<WeatherIconUrl> weatherIconUrl, String winddir16Point,
		Integer winddirDegree, Integer windspeedKmph, Integer windspeenMiles) {
		super();
		this.cloudcover = cloudcover;
		this.humidity = humidity;
		this.observation_time = observation_time;
		this.precipMM = precipMM;
		this.pressure = pressure;
		this.temp_C = temp_C;
		this.temp_F = temp_F;
		this.visibility = visibility;
		this.weatherCode = weatherCode;
		this.weatherDesc = weatherDesc;
		this.weatherIconUrl = weatherIconUrl;
		this.winddir16Point = winddir16Point;
		this.winddirDegree = winddirDegree;
		this.windspeedKmph = windspeedKmph;
		this.windspeenMiles = windspeenMiles;
	}

	//--------------------------------------------------
	// To String
	//--------------------------------------------------
	
	@Override
	public String toString() {
		return "CurrentCondition [cloudcover=" + cloudcover + ", humidity="
			+ humidity + ", observation_time=" + observation_time
			+ ", precipMM=" + precipMM + ", pressure=" + pressure
			+ ", temp_C=" + temp_C + ", temp_F=" + temp_F + ", visibility="
			+ visibility + ", weatherCode=" + weatherCode
			+ ", weatherDesc=" + weatherDesc + ", weatherIconUrl="
			+ weatherIconUrl + ", winddir16Point=" + winddir16Point
			+ ", winddirDegree=" + winddirDegree + ", windspeedKmph="
			+ windspeedKmph + ", windspeenMiles=" + windspeenMiles + "]";
	}
	
	//--------------------------------------------------
	// Getters and Setters
	//--------------------------------------------------
	
	public Integer getCloudcover() {
		return cloudcover;
	}
	public void setCloudcover(Integer cloudcover) {
		this.cloudcover = cloudcover;
	}

	public Integer getHumidity() {
		return humidity;
	}
	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

	public String getObservation_time() {
		return observation_time;
	}
	public void setObservation_time(String observation_time) {
		this.observation_time = observation_time;
	}

	public Double getPrecipMM() {
		return precipMM;
	}
	public void setPrecipMM(Double precipMM) {
		this.precipMM = precipMM;
	}

	public Integer getPressure() {
		return pressure;
	}
	public void setPressure(Integer pressure) {
		this.pressure = pressure;
	}

	public Integer getTemp_C() {
		return temp_C;
	}
	public void setTemp_C(Integer temp_C) {
		this.temp_C = temp_C;
	}

	public Integer getTemp_F() {
		return temp_F;
	}
	public void setTemp_F(Integer temp_F) {
		this.temp_F = temp_F;
	}

	public Integer getVisibility() {
		return visibility;
	}
	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
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

	public Integer getWindspeedKmph() {
		return windspeedKmph;
	}
	public void setWindspeedKmph(Integer windspeedKmph) {
		this.windspeedKmph = windspeedKmph;
	}

	public Integer getWindspeenMiles() {
		return windspeenMiles;
	}
	public void setWindspeenMiles(Integer windspeenMiles) {
		this.windspeenMiles = windspeenMiles;
	}	
}