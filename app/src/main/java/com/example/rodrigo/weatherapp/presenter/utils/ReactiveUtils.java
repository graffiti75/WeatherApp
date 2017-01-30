package com.example.rodrigo.weatherapp.presenter.utils;

import android.app.Activity;
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
import com.example.rodrigo.weatherapp.view.activity.LauncherActivity;
import com.example.rodrigo.weatherapp.view.activity.MainActivity;
import com.example.rodrigo.weatherapp.view.activity.WeatherActivity;
import com.example.rodrigo.weatherapp.view.activity.test.TestDatabaseActivity;
import com.example.rodrigo.weatherapp.view.activity.test.TestMainActivity;
import com.example.rodrigo.weatherapp.view.fragment.LoadingDialogApiFragment;
import com.example.rodrigo.weatherapp.view.fragment.LoadingDialogDatabaseFragment;

import java.util.List;
import java.util.concurrent.Callable;

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

	public static void getWeather(AppCompatActivity activity, WeatherService apiService, String cityName, Dialog dialog) {
		Observable<WeatherResponse> observable = apiService.getWeather(cityName, AppConfiguration.FORMAT,
				AppConfiguration.NUMBER_OF_DAYS, AppConfiguration.KEY);
		observable
			.compose(setupSchedulers())
			.subscribe(
				(response) -> setWeatherResponseInActivity(activity, response, cityName),
				(error) -> {
					dialog.dismiss();
					String message = activity.getString(R.string.fetch_city_error, cityName);
					Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
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
			.compose(setupSchedulers())
			.subscribe(
				(success) -> {
					dialog.dismiss();
					activity.removeCityFromAdapter(success, city);
				},
				(error) -> {
					dialog.dismiss();
					String message = activity.getString(R.string.database_error);
					Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
				},
				() -> dialog.dismiss()
			);
	}

	public static Callable<Boolean> removeCityFromDatabase(Activity activity, City city) {
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

	public static void insertCityList(LauncherActivity activity, List<City> list, Dialog dialog) {
		Observable<Boolean> observable = makeObservable(activity, insertCityListInDatabase(activity, list));
		observable
			.compose(setupSchedulers())
			.subscribe(
				(success) -> {
					dialog.dismiss();
					activity.callMainActivity(success);
				},
				(error) -> {
					dialog.dismiss();
					String message = activity.getString(R.string.database_error);
					Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
				},
				() -> dialog.dismiss()
			);
	}

	public static void insertCityList(MainActivity activity, List<City> list, City newCity, Dialog dialog) {
		Observable<Boolean> observable = makeObservable(activity, insertCityListInDatabase(activity, list));
		observable
			.compose(setupSchedulers())
			.subscribe(
				(success) -> {
					dialog.dismiss();
					activity.addCityIntoAdapter(newCity, success);
				},
				(error) -> {
					dialog.dismiss();
					String message = activity.getString(R.string.database_error);
					Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
				},
				() -> dialog.dismiss()
			);
	}

	private static Callable<Boolean> insertCityListInDatabase(Activity activity, List<City> list) {
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

	public static void getCityList(Activity activity, Boolean updateAdapter, Dialog dialog) {
		Observable<List<City>> observable = makeObservable(activity, getCityListFromDatabase(activity));
		observable
			.compose(setupSchedulers())
			.subscribe(
				(list) -> {
					dialog.dismiss();
					if (activity instanceof MainActivity) {
						MainActivity mainActivity = (MainActivity)activity;
						mainActivity.changeAdapter(list, updateAdapter);
					} else if (activity instanceof LauncherActivity) {
						LauncherActivity launcherActivity = (LauncherActivity)activity;
						if (list != null && list.size() > 0) {
							launcherActivity.callMainActivity(true);
						} else {
							launcherActivity.getCityListFromDatabase();
						}
					}
				},
				(error) -> {
					dialog.dismiss();
					String message = activity.getString(R.string.database_error);
					Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
				},
				() -> dialog.dismiss()
			);
	}

	private static Callable<List<City>> getCityListFromDatabase(Activity activity) {
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

	public static <T> Observable<T> makeObservable(Activity activity, final Callable<T> func) {
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

	public static <T> Observable.Transformer<T, T> setupSchedulers() {
		return observable -> observable
			.subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread());
	}

	//--------------------------------------------------
	// Tests
	//--------------------------------------------------

	public static void insertCityList(TestDatabaseActivity activity, List<City> list,
		LoadingDialogDatabaseFragment fragment) {
		Observable<Boolean> observable = makeObservable(activity, insertCityListInDatabase(activity, list));
		observable
			.compose(setupSchedulers())
			.subscribe(
				(success) -> {
					fragment.dismiss();
					activity.onLoadingFinished();
				},
				(error) -> {
					fragment.dismiss();
					String message = activity.getString(R.string.database_error);
					Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
				},
				() -> fragment.dismiss()
			);
	}

	public static void getWeather(TestMainActivity activity, WeatherService apiService,
		String cityName, LoadingDialogApiFragment fragment) {
		Observable<WeatherResponse> observable = apiService.getWeather(cityName, AppConfiguration.FORMAT,
			AppConfiguration.NUMBER_OF_DAYS, AppConfiguration.KEY);
		observable
			.compose(setupSchedulers())
			.subscribe(
				(response) -> {
					fragment.dismiss();
					activity.onLoadingFinished();
				},
				(error) -> {
					fragment.dismiss();
					String message = activity.getString(R.string.fetch_city_error, cityName);
					Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
				},
				() -> fragment.dismiss()
			);
	}
}