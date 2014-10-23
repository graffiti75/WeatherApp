package br.android.weather_app.api;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.accounts.NetworkErrorException;

/**
 * WeatherServiceErrorHandler.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 20/10/2014
 */
public class WeatherServiceErrorHandler implements ErrorHandler {
	@Override
	public Throwable handleError(RetrofitError error) {
		Response response = error.getResponse();
		if (response != null && response.getStatus() != 200) {
			return new NetworkErrorException();
		}
		return error;
	}
}