package br.android.weather_app.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import br.android.weather_app.AppConfiguration;
import br.android.weather_app.api.WeatherService;
import br.android.weather_app.api.model.CurrentCondition;
import br.android.weather_app.api.model.Request;
import br.android.weather_app.api.model.Weather;
import br.android.weather_app.api.model.WeatherResponse;
import br.android.weather_app.model.City;
import br.android.weather_app.tasks.AddCityAsyncTask;
import br.android.weather_app.tasks.GetCityListAsyncTask;
import br.android.weather_app.tasks.Notifiable;
import br.android.weather_app.tasks.RemoveCityAsyncTask;
import br.android.weather_app.tasks.UpdaterAsyncTask;
import br.android.weather_app.tasks.WeatherAsyncTask;

/**
 * ContentManager.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class ContentManager {

	//----------------------------------------------
	// Constants
	//----------------------------------------------
	
	// Fetch task types.
	public static final class FETCH_TASK {
		public static final int UPDATER = 1;
		public static final int WEATHER = 2;
		public static final int CITY_LIST = 3;
		public static final int CITY_ADD = 4;
		public static final int CITY_REMOVE = 5;
	}
	
	//----------------------------------------------
	// Statics
	//----------------------------------------------
	
	// The singleton instance.
	private static ContentManager sInstance = null;
	
	//----------------------------------------------
	// Attributes
	//----------------------------------------------

	// Cached values.
	private Boolean mDatabaseNeedsUpdate = false;
	private Boolean mCityAddedOk = false;
	private Boolean mCityRemovedOk = false;
	
	private List<City> mCityList;
	private WeatherResponse mWeatherResponse = null;
	private List<Weather> mWeatherList;
	private CurrentCondition mCurrentCondition;
	private String mCityFromRequest;
	
	// Notifiables map.
	private Map<Object, Notifiable> mTaskNotifiables = new HashMap<Object, Notifiable>();
	
	//----------------------------------------------
	// Constructor
	//----------------------------------------------
	
	/**
	 * Private constructor.
	 */
	private ContentManager() {}
	
	/**
	 * @return The singleton instance of ContentManager.
	 */
	public static ContentManager getInstance() {
		if (sInstance == null) {
			sInstance = new ContentManager();
		}
		return sInstance;
	}

	//----------------------------------------------
	// Global Methods
	//----------------------------------------------
	
	/**
	 * Cleans all cached content.
	 */
	public void clean() {
		mCityList = null;
		mWeatherResponse = null;
		mWeatherList = null;
		mCurrentCondition = null;
		mCityFromRequest = null;
	}
	
	//----------------------------------------------
	// Updater
	//----------------------------------------------

	/**
	 * Gets the database info from cache.
	 * 
	 * @return
	 */
	public Boolean getCachedDatabaseInfo() {
		return mDatabaseNeedsUpdate;
	}
	
	/**
	 * Gets the database info.
	 * 
	 * @param notifiable The notifiable object to be called.
	 */
	public void getDatabaseInfo(Notifiable notifiable, Context context) {
		UpdaterAsyncTask task = new UpdaterAsyncTask(context);
		if (notifiable != null) {
			mTaskNotifiables.put(task, notifiable);
		}
		task.execute();
	}
	
	//----------------------------------------------
	// City List
	//----------------------------------------------
	
	/**
	 * Gets the {@link City} list from cache.
	 * 
	 * @return
	 */
	public List<City> getCachedCityList() {
		return mCityList;
	}
	
	/**
	 * Gets the {@link City} list.
	 * 
	 * @param notifiable The notifiable object to be called.
	 */
	public void getCityList(Notifiable notifiable, Context context) {
		GetCityListAsyncTask task = new GetCityListAsyncTask(context, mDatabaseNeedsUpdate);
		if (notifiable != null) {
			mTaskNotifiables.put(task, notifiable);
		}
		task.execute();
	}
	
	/**
	 * Prints the {@link City} list.
	 */
	public void printCityList() {
		Log.i(AppConfiguration.COMMON_LOGGING_TAG, "Printing the city list read from the database.");
		for (City city : mCityList) {
			Log.i(AppConfiguration.COMMON_LOGGING_TAG, city.toString());
		}
	}
	
	//----------------------------------------------
	// Add City
	//----------------------------------------------
	
	/**
	 * Gets the database access status.
	 * 
	 * @return
	 */
	public Boolean getCityAddedStatus() {
		return mCityAddedOk;
	}
	
	/**
	 * Adds a {@link City} into the database.
	 * 
	 * @param notifiable The notifiable object to be called.
	 * @param context
	 * @param list 
	 */
	public void addCity(Notifiable notifiable, Context context, List<City> list) {
		AddCityAsyncTask task = new AddCityAsyncTask(context, list);
		if (notifiable != null) {
			mTaskNotifiables.put(task, notifiable);
		}
		task.execute();
	}
	
	//----------------------------------------------
	// Remove City
	//----------------------------------------------
	
	/**
	 * Gets the database access status.
	 * 
	 * @return
	 */
	public Boolean getCityRemovedStatus() {
		return mCityRemovedOk;
	}
	
	/**
	 * Removes a {@link City} from the database.
	 * 
	 * @param notifiable The notifiable object to be called.
	 * @param context
	 * @param city
	 */
	public void removeCity(Notifiable notifiable, Context context, City city) {
		RemoveCityAsyncTask task = new RemoveCityAsyncTask(context, city);
		if (notifiable != null) {
			mTaskNotifiables.put(task, notifiable);
		}
		task.execute();
	}
	
	//----------------------------------------------
	// Weather Response
	//----------------------------------------------

	/**
	 * Gets the {@link WeatherResponse} from cache.
	 * 
	 * @return
	 */
	public WeatherResponse getWeatherResponse() {
		return mWeatherResponse;
	}
	
	/**
	 * Gets the {@link WeatherResponse} from the API.
	 * 
	 * @param notifiable The notifiable object to be called.
	 * @param service The Retrofit service. 
	 * @param city The city to be searched.
	 */
	public void getWeatherResponse(Notifiable notifiable, WeatherService service, String city) {
		WeatherAsyncTask task = new WeatherAsyncTask(service, city);
		if (notifiable != null) {
			mTaskNotifiables.put(task, notifiable);
		}
		task.execute();
	}
	
	//----------------------------------------------
	// Current Condition
	//----------------------------------------------
	
	/**
	 * Sets the {@link CurrentCondition} list from the REST API.
	 * 
	 * @param response
	 */
	public void setCurrentCondition(WeatherResponse response) {
		List<CurrentCondition> currentConditionList = new ArrayList<CurrentCondition>();
		
		// Gets the CurrentCondition list.
		currentConditionList = response.getData().getCurrentCondition();
		if (currentConditionList != null) {
			mCurrentCondition = response.getData().getCurrentCondition().get(0);
		}
	}
	
	/**
	 * Gets the {@link CurrentCondition}.
	 * 
	 * @return
	 */
	public CurrentCondition getCurrentCondition() {
		return mCurrentCondition;
	}
	
	//----------------------------------------------
	// Weather List
	//----------------------------------------------

	/**
	 * Sets the {@link Weather} list from the REST API.
	 * 
	 * @param response
	 */
	public void setWeatherList(WeatherResponse response) {
		List<Weather> returnList = new ArrayList<Weather>();
		List<Weather> weatherList = response.getData().getWeather();
		
		// Gets the current city name.
		List<Request> requestList = response.getData().getRequest();
		if (requestList != null) {
			mCityFromRequest = requestList.get(0).getQuery();
		}
		
		// Checks the return from the REST API.
		if (weatherList != null) {
			// Weather List.
			for (Weather weather : weatherList) {
				returnList.add(weather);
			}
			mWeatherList = returnList;
		}
	}
	
	/**
	 * Gets the {@link Weather} list.
	 * 
	 * @return
	 */
	public List<Weather> getWeatherList() {
		return mWeatherList;
	}

	//----------------------------------------------
	// Request Model
	//----------------------------------------------
	
	/**
	 * Sets the {@link Request} list from the REST API.
	 * If the REST API returns valid data, this method returns True.
	 * 
	 * @param response
	 */
	public Boolean setRequestList(WeatherResponse response) {
		List<Request> requestList = response.getData().getRequest();
		Boolean loadedSuccessfully = true;
		
		// Checks the return from the REST API.
		if (requestList != null) {
			mCityFromRequest = requestList.get(0).getQuery();
		} else {
			loadedSuccessfully = false;
		}
		
		return loadedSuccessfully;
	}
	
	/**
	 * Gets the {@link City} retrieved from the {@link Request}.
	 *  
	 * @return
	 */
	public String getCityFromRequest() {
		return mCityFromRequest;
	}
	
	//----------------------------------------------
	// Tasks Handling
	//----------------------------------------------
	
	/**
	 * When a {@link AsyncTask} finished, the application flow comes to here.
	 * 
	 * @param task
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	public void taskFinished(Object task, Object result) {
		int taskType = getTaskType(task);
		
		// Gets current task result.
		if (FETCH_TASK.UPDATER == taskType) {
			// Puts the database info in the cache.
			mDatabaseNeedsUpdate = (Boolean)result;
		} else if (FETCH_TASK.WEATHER == taskType) {
			// Puts the API call info in the cache.
			mWeatherResponse = (WeatherResponse)result;
		} else if (FETCH_TASK.CITY_LIST == taskType) {
			// Puts the database data in the cache.
			mCityList = (List<City>)result;
			printCityList();
		} else if (FETCH_TASK.CITY_ADD == taskType) {
			// Gets the database return.
			mCityAddedOk = (Boolean)result;
		} else if (FETCH_TASK.CITY_REMOVE == taskType) {
			// Gets the database return.
			mCityRemovedOk = (Boolean)result;
		}
		
		// Removes performed task.
		Notifiable notifiable = mTaskNotifiables.get(task);
		if (notifiable != null) {
			notifiable.taskFinished(taskType, result);
			mTaskNotifiables.remove(task);
		}
	}
	
	/**
	 * Returns the task type.
	 * 
	 * @param task
	 * @return The task type.
	 */
	private int getTaskType(Object task) {
		if (task instanceof UpdaterAsyncTask) {
			return FETCH_TASK.UPDATER;
		} else if (task instanceof WeatherAsyncTask) {
			return FETCH_TASK.WEATHER;
		} else if (task instanceof GetCityListAsyncTask) {
			return FETCH_TASK.CITY_LIST;
		} else if (task instanceof AddCityAsyncTask) {
			return FETCH_TASK.CITY_ADD;
		} else if (task instanceof RemoveCityAsyncTask) {
			return FETCH_TASK.CITY_REMOVE;
		}
		return -1;
	}
}