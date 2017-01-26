package com.example.rodrigo.weatherapp.model.api;

import com.example.rodrigo.weatherapp.model.WeatherResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * GitHubService.java class.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 24, 2017
 */
public interface WeatherService {
	@GET("/free/v1/weather.ashx")
	Observable<WeatherResponse> getWeather(
		@Query("q") String q,						// London
		@Query("format") String format,				// json
		@Query("num_of_days") String numOfDays,		// 5
		@Query("key") String key);					// 714be4de03b4ab70767d3335a6fa1651015c022f
}