package br.android.weather_app.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.AsyncTask;
import br.android.weather_app.api.WeatherService;
import br.android.weather_app.api.model.CurrentCondition;
import br.android.weather_app.api.model.Request;
import br.android.weather_app.api.model.Weather;
import br.android.weather_app.api.model.WeatherResponse;
import br.android.weather_app.model.City;
import br.android.weather_app.tasks.Notifiable;
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
		public static final int WEATHER = 1;
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
		// Cleaning all cached contents.
		mWeatherResponse = null;
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
	public void taskFinished(Object task, Object result) {
		int taskType = getTaskType(task);
		
		// Gets current task result.
		if (FETCH_TASK.WEATHER == taskType) {
			// Puts the database info in the cache.
			mWeatherResponse = (WeatherResponse)result;
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
		if (task instanceof WeatherAsyncTask) {
			return FETCH_TASK.WEATHER;
		}
		return -1;
	}
}