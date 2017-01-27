package com.example.rodrigo.weatherapp.controller.utils;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.model.City;
import com.example.rodrigo.weatherapp.model.WeatherResponse;
import com.example.rodrigo.weatherapp.model.api.WeatherService;
import com.example.rodrigo.weatherapp.model.database.CityProvider;
import com.example.rodrigo.weatherapp.model.database.DatabaseUtils;
import com.example.rodrigo.weatherapp.view.activity.MainActivity;
import com.example.rodrigo.weatherapp.view.activity.WeatherActivity;

import java.util.List;
import java.util.concurrent.Callable;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * ActivityUtils class.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class ReactiveUtils {

	//--------------------------------------------------
	// Retrofit
	//--------------------------------------------------

	public static void getWeather(AppCompatActivity activity, String cityName, Dialog dialog) {
		// Retrofit.
		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl(AppConfiguration.BASE_URL)
			.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create())
			.build();
		WeatherService service = retrofit.create(WeatherService.class);

		// Rx.
		Observable<WeatherResponse> observable = service.getWeather(cityName, AppConfiguration.FORMAT,
				AppConfiguration.NUMBER_OF_DAYS, AppConfiguration.KEY);
		observable
			.subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				(response) -> setWeatherResponseInActivity(activity, response, cityName),
				(error) -> {
					String message = activity.getString(R.string.fetch_city_error, cityName);
					Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
					dialog.dismiss();
				},
				() -> dialog.dismiss()
			);
	}

	private static void setWeatherResponseInActivity(AppCompatActivity activity, WeatherResponse response,
		String cityName) {
		if (activity instanceof MainActivity) {
			String cityFromApi = response.getData().getRequest().get(0).getQuery();
			if (!Utils.isEmpty(cityFromApi)) {
				MainActivity mainActivity = (MainActivity) activity;
				mainActivity.citySearchedExists(cityName);
			}
		} else if (activity instanceof WeatherActivity) {
			WeatherActivity weatherActivity = (WeatherActivity)activity;
			weatherActivity.setAdapter(response);
		}
	}

	//--------------------------------------------------
	// Remove City From Database
	//--------------------------------------------------

	public static void removeCity(MainActivity activity, City city, Dialog dialog) {
		Observable<Boolean> observable = makeObservable(activity, removeCityFromDatabase(activity, city));
		observable
			.subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				(success) -> {
					activity.removeCityFromAdapter(success, city);
					dialog.dismiss();
				},
				(error) -> {
					String message = activity.getString(R.string.database_error);
					Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
					dialog.dismiss();
				},
				() -> dialog.dismiss()
			);
	}

	private static Callable<Boolean> removeCityFromDatabase(MainActivity activity, City city) {
		return () -> {
			CityProvider database = DatabaseUtils.openDatabase(activity);
			Boolean success = DatabaseUtils.deleteCity(activity, city.getId());
			DatabaseUtils.closeDatabase(database);
			return success;
		};
	}

	//--------------------------------------------------
	// Insert City List In Database
	//--------------------------------------------------

	public static void insertCityList(MainActivity activity, List<City> list, City newCity, Dialog dialog) {
		Observable<Boolean> observable = makeObservable(activity, insertCityListInDatabase(activity, list));
		observable
			.subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				(success) -> {
					activity.addCityIntoAdapter(newCity, success);
					dialog.dismiss();
				},
				(error) -> {
					String message = activity.getString(R.string.database_error);
					Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
					dialog.dismiss();
				},
				() -> dialog.dismiss()
			);
	}

	private static Callable<Boolean> insertCityListInDatabase(MainActivity activity, List<City> list) {
		return () -> {
			CityProvider database = DatabaseUtils.openDatabase(activity);
			Boolean success = DatabaseUtils.insertCityList(activity, list);
			DatabaseUtils.closeDatabase(database);
			return success;
		};
	}

	//--------------------------------------------------
	// Read City List From Database
	//--------------------------------------------------

	public static void getCityList(MainActivity activity, Boolean updateAdapter, Dialog dialog) {
		Observable<List<City>> observable = makeObservable(activity, getCityListFromDatabase(activity));
		observable
			.subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				(list) -> {
					activity.changeAdapter(list, updateAdapter);
					dialog.dismiss();
				},
				(error) -> {
					String message = activity.getString(R.string.database_error);
					Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
					dialog.dismiss();
				},
				() -> dialog.dismiss()
			);
	}

	private static Callable<List<City>> getCityListFromDatabase(MainActivity activity) {
		return () -> {
			CityProvider database = DatabaseUtils.openDatabase(activity);
			List<City> list = DatabaseUtils.getCityList(activity);
			DatabaseUtils.closeDatabase(database);
			return list;
		};
	}

	//--------------------------------------------------
	// Common Database Methods
	//--------------------------------------------------

	private static <T> Observable<T> makeObservable(MainActivity activity, final Callable<T> func) {
		return Observable.create(new Observable.OnSubscribe<T>() {
			@Override
			public void call(Subscriber<? super T> subscriber) {
				try {
					subscriber.onNext(func.call());
				} catch(Exception ex) {
					Log.e(AppConfiguration.TAG, activity.getString(R.string.database_error), ex);
				}
			}
		});
	}
}