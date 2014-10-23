package br.android.weather_app.api;

import retrofit.http.GET;
import retrofit.http.Query;
import br.android.weather_app.api.model.WeatherResponse;

/**
 * GitHubService.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public interface WeatherService {
	@GET("/free/v1/weather.ashx")
	WeatherResponse getWeather(
		@Query("q") String q, // London
		@Query("format") String format, // json
		@Query("num_of_days") String numOfDays, // 5
		@Query("key") String key); // 714be4de03b4ab70767d3335a6fa1651015c022f
}