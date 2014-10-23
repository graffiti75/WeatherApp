package br.android.weather_app.tasks;

import android.os.AsyncTask;
import br.android.weather_app.api.WeatherService;
import br.android.weather_app.api.model.WeatherResponse;
import br.android.weather_app.manager.ContentManager;

/**
 * WeatherAsyncTask.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 14/10/2014
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
		WeatherResponse repos = mService.getWeather(mCity, "json", "5", "714be4de03b4ab70767d3335a6fa1651015c022f");
		return repos;
	}
	
	protected void onPostExecute(WeatherResponse result) {
		ContentManager.getInstance().taskFinished(this, result);
	};
}