package com.example.rodrigo.weatherapp.tasks;

import android.os.AsyncTask;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.api.WeatherService;
import com.example.rodrigo.weatherapp.api.model.WeatherResponse;
import com.example.rodrigo.weatherapp.manager.ContentManager;

/**
 * WeatherAsyncTask.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class WeatherAsyncTask extends AsyncTask<Void, Integer, WeatherResponse> {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	// The Retrofit service.
	private WeatherService mService;
	
	// The city to search for.
	private String mCity;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	/**
	 * Creates a new {@link WeatherAsyncTask} instance.
	 */
	public WeatherAsyncTask(WeatherService service, String city) {
		mService = service;
		mCity = city;
	}

	//----------------------------------------------
	// Async Task
	//----------------------------------------------
	
	@Override
	protected WeatherResponse doInBackground(Void... params) {
		WeatherResponse response = mService.getWeather(mCity, AppConfiguration.FORMAT, AppConfiguration.NUMBER_OF_DAYS, AppConfiguration.KEY);
		return response;
	}
	
	protected void onPostExecute(WeatherResponse result) {
		ContentManager.getInstance().taskFinished(this, result);
	}
}