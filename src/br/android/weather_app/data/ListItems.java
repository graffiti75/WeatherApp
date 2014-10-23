package br.android.weather_app.data;

import java.util.ArrayList;
import java.util.List;

import br.android.weather_app.api.model.Weather;
import br.android.weather_app.api.model.WeatherDesc;
import br.android.weather_app.api.model.WeatherIconUrl;
import br.android.weather_app.model.City;

/**
 * ListItems.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class ListItems {
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Gets a {@link Weather} list.
	 * 
	 * @return
	 */
	public static List<Weather> getWeatherList() {
		List<Weather> weatherList = new ArrayList<Weather>();
		
		// Element #1.
		List<WeatherDesc> weatherDescList = new ArrayList<WeatherDesc>();
		List<WeatherIconUrl> weatherIconUrlList = new ArrayList<WeatherIconUrl>();
		weatherDescList.add(new WeatherDesc("Cloudy"));
		weatherIconUrlList.add(new WeatherIconUrl("http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0003_white_cloud.png"));
		
		Weather weather = new Weather("2014-10-12", 8.1, 15, 59, 8, 46, 119, weatherDescList, weatherIconUrlList,		
			"ENE", 61, "ENE", 24, 15);
		weatherList.add(weather);

		// Element #2.
		weatherDescList = new ArrayList<WeatherDesc>();
		weatherIconUrlList = new ArrayList<WeatherIconUrl>();
		weatherDescList.add(new WeatherDesc("Light drizzly"));
		weatherIconUrlList.add(new WeatherIconUrl("http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0017_cloudy_with_light_rain.png"));
		
		weather = new Weather("2014-10-13", 5.4, 16, 60, 7, 45, 256, weatherDescList, weatherIconUrlList, 
			"WSW", 248, "WSW", 23, 14);
		weatherList.add(weather);
		
		// Element #3.
		weatherDescList = new ArrayList<WeatherDesc>();
		weatherIconUrlList = new ArrayList<WeatherIconUrl>();
		weatherDescList.add(new WeatherDesc("Patchy rain nearby"));
		weatherIconUrlList.add(new WeatherIconUrl("http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0009_light_rain_showers.png"));
		
		weather = new Weather("2014-10-14", 0.0, 16, 58, 6, 43, 176, weatherDescList, weatherIconUrlList, 
			"W", 274, "W", 17, 11);
		weatherList.add(weather);

		// Element #4.
		weatherDescList = new ArrayList<WeatherDesc>();
		weatherIconUrlList = new ArrayList<WeatherIconUrl>();
		weatherDescList.add(new WeatherDesc("Cloudy"));
		weatherIconUrlList.add(new WeatherIconUrl("http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0003_white_cloud.png"));
		
		weather = new Weather("2014-10-15", 2.4, 16, 61, 13, 56, 119, weatherDescList, weatherIconUrlList,
			"SSE", 167, "SSE", 15, 10);
		weatherList.add(weather);

		// Element #5.
		weatherDescList = new ArrayList<WeatherDesc>();
		weatherIconUrlList = new ArrayList<WeatherIconUrl>();
		weatherDescList.add(new WeatherDesc("Cloudy"));
		weatherIconUrlList.add(new WeatherIconUrl("http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0003_white_cloud.png"));		

		weather = new Weather("2014-10-15", 2.4, 16, 61, 13, 56, 119, weatherDescList, weatherIconUrlList, 
			"SSE", 167, "SSE", 15, 10);
		weatherList.add(weather);

		// Element #6.
		weatherDescList = new ArrayList<WeatherDesc>();
		weatherIconUrlList = new ArrayList<WeatherIconUrl>();
		weatherDescList.add(new WeatherDesc("Sunny"));
		weatherIconUrlList.add(new WeatherIconUrl("http://cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0001_sunny.png"));
		
		weather = new Weather("2014-10-16", 1.7, 20, 68, 13, 56, 113, weatherDescList, weatherIconUrlList,
			"SW", 234, "SW", 24, 15);
		weatherList.add(weather);
		
		return weatherList;
	}
	
	/**
	 * Gets the city list.
	 * 
	 * @return
	 */
	public static List<City> getCityList() {
		List<City> cityList = new ArrayList<City>();
		cityList.add(new City("Dublin, Ireland", true));
		cityList.add(new City("London, United Kingdom", true));
		cityList.add(new City("New York, United States Of America", true));
		cityList.add(new City("Barcelona, Spain", true));
		
		return cityList;
	}
}