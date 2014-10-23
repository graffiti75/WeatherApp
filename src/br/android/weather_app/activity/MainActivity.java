package br.android.weather_app.activity;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import br.android.weather_app.AppConfiguration;
import br.android.weather_app.R;
import br.android.weather_app.adapter.CityAdapter;
import br.android.weather_app.api.WeatherService;
import br.android.weather_app.api.WeatherServiceErrorHandler;
import br.android.weather_app.api.model.CurrentCondition;
import br.android.weather_app.api.model.Request;
import br.android.weather_app.api.model.WeatherResponse;
import br.android.weather_app.helper.DialogHelper;
import br.android.weather_app.helper.OnClickListenerCustomDialog;
import br.android.weather_app.manager.ContentManager;
import br.android.weather_app.model.City;
import br.android.weather_app.tasks.Notifiable;
import br.android.weather_app.utils.ActivityUtils;
import br.android.weather_app.utils.LayoutUtils;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * MainActivity.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 19/10/2014
 */
public class MainActivity extends SherlockActivity implements Notifiable, OnItemClickListener {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public void __________CONSTANTS__________() {};

	public static final String CITY_NAME_EXTRA = "city_name_extra";

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	public void __________ATTRIBUTES__________() {};
	
	// Current Condition.
	private LinearLayout mBackgroundLinearLayout;
	private TextView mObservationTimeTextView;
	private TextView mCurrentDayDescTextView;
	private ImageView mCurrentWeatherImageView;
	private TextView mTemperatureTextView;
	private TextView mPrecipTextView;
	private TextView mWindDirTextView;
	private TextView mWindSpeedTextView;
	
	// Layout.
	private Dialog mDialog = null;

	// Rest.
	private String mCityName;
	private WeatherService mService;
	private Boolean mCheckingCity = false;

	// Adapter.
	private List<City> mCityList = new ArrayList<City>();
	private CityAdapter mAdapter;
	private ListView mListView;
	private City mNewCity;
	private Integer mCityId;
	private Boolean mCityAddedOnDatabase = false;

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------

	public void _____ACTIVITY_LIFE_CYCLE_____() {};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setCurrentDay();
		setAdapter();
		registerForContextMenu(mListView);
	}
	
	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	public void __________MENU__________() {};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.id_menu_add:
				getCityDialog();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

   @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context_activity_main, menu);
    }
    
	@Override
    public boolean onContextItemSelected(android.view.MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    Integer position = info.position;
		
        switch (item.getItemId()) {
            case R.id.id_menu_delete:
            	removeCityFromDatabase(position);
                return true;
            default:
                return super.onContextItemSelected((android.view.MenuItem) item);
        }
    }
	    
	//--------------------------------------------------
	// Layout Methods
	//--------------------------------------------------

	public void _____LAYOUT_METHODS_____() {};
	
	/**
	 * Sets the {@link CurrentCondition} day.
	 */
	public void setCurrentDay() {
		Boolean userAllowGps = ContentManager.getInstance().getUserAllowGps();
		if (userAllowGps) {
			CurrentCondition current = ContentManager.getInstance().getCurrentCondition();
			setCurrentConditionLayout();
			setCurrentConditionData(current);			
		}
	}
	
	/**
	 * Sets the {@link CurrentCondition} layout.
	 */
	public void setCurrentConditionLayout() {
		mBackgroundLinearLayout = (LinearLayout)findViewById(R.id.id_current_condition_adapter__linear_layout);
		mBackgroundLinearLayout.setVisibility(View.VISIBLE);
		mObservationTimeTextView = (TextView)findViewById(R.id.id_current_condition_adapter__observation_time_text_view);
		mCurrentDayDescTextView = (TextView)findViewById(R.id.id_current_condition_adapter__current_day_desc_text_view);
		mCurrentWeatherImageView = (ImageView)findViewById(R.id.id_current_condition_adapter__current_weather_image_view);
		mTemperatureTextView = (TextView)findViewById(R.id.id_current_condition_adapter__temperature_text_view);
		mPrecipTextView = (TextView)findViewById(R.id.id_current_condition_adapter__precip_text_view);
		mWindDirTextView = (TextView)findViewById(R.id.id_current_condition_adapter__wind_dir_text_view);
		mWindSpeedTextView = (TextView)findViewById(R.id.id_current_condition_adapter__wind_speed_text_view);
	}
	
	/**
	 * Sets the {@link CurrentCondition} data.
	 * 
	 * @param current
	 */
	@SuppressLint("NewApi")
	public void setCurrentConditionData(CurrentCondition current) {
		// Background linear layout.
		Drawable color = LayoutUtils.getTemperatureColor(this, current.getTemp_C());
		mBackgroundLinearLayout.setBackground(color);
		
		// Observation time.
		String userCity = ContentManager.getInstance().getCityFromRequest();
		String text = userCity + ", " + current.getObservation_time();
		mObservationTimeTextView.setText(text);
		
		// Current day description.
		String currentDayDescription = current.getWeatherDesc().get(0).getValue();
		mCurrentDayDescTextView.setText(currentDayDescription);
		
		// Weather image.
		String weatherIconUrl = current.getWeatherIconUrl().get(0).getValue();
		LayoutUtils.setUniversalImage(this, weatherIconUrl, mCurrentWeatherImageView);
		
		// Temperature.
		String temperature = current.getTemp_C().toString() + "ºC";
		mTemperatureTextView.setText(temperature);
		
		// Precipitation.
		String precipitation = current.getPrecipMM().toString() + " mm";
		mPrecipTextView.setText(precipitation);
		
		// Wind Direction.
		String windDirection = current.getWinddir16Point();
		mWindDirTextView.setText(windDirection);

		// Wind Speed.
		String windSpeed = current.getWindspeedKmph().toString() + " km/h";
		mWindSpeedTextView.setText(windSpeed);
	}
	
	/**
	 * Adds values of the list, customizes adapter, and set's list view adapter
	 * and it's listener.
	 */
	public void setAdapter() {
		mCityList = ContentManager.getInstance().getCachedCityList();
		mListView = (ListView) findViewById(R.id.id_activity_main__listview);
		mAdapter = new CityAdapter(this, mCityList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	//--------------------------------------------------
	// API Methods
	//--------------------------------------------------
	
	public void _____API_METHODS_____() {};
	
	/**
	 * Sets the {@link WeatherService} from the Retrofit.
	 */
	public void setRetrofitService() {
		RestAdapter restAdapter = new RestAdapter.Builder()
			.setEndpoint("http://api.worldweatheronline.com")
			.setErrorHandler(new WeatherServiceErrorHandler())
			.build();

		mService = restAdapter.create(WeatherService.class);
	}
	
	/**
	 * Gets the {@link WeatherResponse} of a specific city.
	 */
	public void getWeatherInfo() {
		setRetrofitService();
		ContentManager.getInstance().getWeatherResponse(this, mService, mCityName);
	}

	/**
	 * Shows the {@link WeatherResponse} info for the user.
	 * 
	 * @param response
	 */
	public void setWeatherListInCache(WeatherResponse response) {
		ContentManager.getInstance().setWeatherList(response);
		openWeatherActivity();
	}

	/**
	 * Verifies the {@link Request} list from the API.
	 * 
	 * @param response
	 */
	public void setRequestListInCache(WeatherResponse response) {
		Boolean loadedSuccessfully = ContentManager.getInstance().setRequestList(response);
		String citySearched = ContentManager.getInstance().getCityFromRequest();
		Boolean cityRepeated = cityAlreadyInAdapter(citySearched);		
		
		// Checks if the city is already into the adapter.
		if (cityRepeated) {
			DialogHelper.showSimpleAlert(this, R.string.activity_main__repeated_city_dialog_title,
				R.string.activity_main__repeated_city_dialog_message);
			closeProgressDialog();
		} else {
			// Checks if the city was successfully added into the database.
			if (loadedSuccessfully) {
				// Adds the city into the adapter.
				addCityInDatabase();
				
				// Closes the current dialog.
				if (mDialog != null) {
					mDialog.cancel();
				}
			} else {
				// If city wasn't successfully added into the database, shows a error message for the user.
				DialogHelper.showSimpleAlert(this, R.string.activity_main__empty_city_dialog_title,
					R.string.activity_main__empty_city_dialog_message);
				closeProgressDialog();
				mCityAddedOnDatabase = false;
			}
			mCheckingCity = false;
		}
	}
	
	/**
	 * Gets the {@link City} info from the Api.
	 * 
	 * @param city
	 */
	public void getCityInfoFromApi(String cityName) {
		// Shows a dialog for the user.
		mCityName = cityName;
		String message = getString(R.string.activity_main__loading_data) +
			" " + mCityName + "..."; 
		mDialog = DialogHelper.showProgressDialog(MainActivity.this, message);
		
		// Calls the API to check if the city exists.
		mCheckingCity = true;
		setRetrofitService();
		ContentManager.getInstance().getWeatherResponse(this, mService, cityName);
	}

	//--------------------------------------------------
	// Insert or Remove Methods
	//--------------------------------------------------
	
	public void _____INSERT_REMOVE_METHODS_____() {};
	
	/**
	 * Adds the {@link City} into the database. 
	 */
	public void addCityInDatabase() {
		// Creates a new city.
		Integer listSize = mCityList.size();
		City lastElement = mCityList.get(listSize - 1);
		Integer index = lastElement.getId() + 1;
		String city = ContentManager.getInstance().getCityFromRequest();
		mNewCity = new City(index, city);
		
		// Adds the city into the database.
		List<City> list = new ArrayList<City>();
		list.add(mNewCity);
		ContentManager.getInstance().addCity(this, MainActivity.this, list);
	}
	
	/**
	 * Adds the {@link City} into the adapter.
	 */
	public void addCityIntoAdapter() {
		Log.i(AppConfiguration.COMMON_LOGGING_TAG,
			"[MainActivity].addCityIntoAdapter() -> Adding city " + mNewCity.toString());
		if (mCityAddedOnDatabase) {
			mCityList.add(mNewCity);
			mAdapter.notifyDataSetChanged();
		}
	}
	
	/**
	 * Removes the {@link City} from the database.
	 * 
	 * @param position
	 */
	public void removeCityFromDatabase(Integer position) {
		City city = mCityList.get(position);
		mCityId = city.getId();
		ContentManager.getInstance().removeCity(this, MainActivity.this, city);
	}
	
	/**
	 * Updates the {@link City} list adapter.
	 * 
	 * @param result
	 */
	public void updateCityAdapter(Object result) {
		mCityList.clear();
		mCityList.addAll((List<City>)result);
		mAdapter.notifyDataSetChanged();
	}
	
	//--------------------------------------------------
	// Other Methods
	//--------------------------------------------------
	
	public void _____OTHER_METHODS_____() {};
	
	/**
	 * Checks if the city typed by the user is already into the adatper.
	 * 
	 * @param cityName
	 * 
	 * @return
	 */
	public Boolean cityAlreadyInAdapter(String cityName) {
		Boolean isInside = false;
		String cityNameLowerCase = cityName.toLowerCase();
		
		for (City city : mCityList) {
			String currentCityName = city.getCity().toLowerCase();
			if (currentCityName.contains(cityNameLowerCase)) {
				isInside = true;
			}
		}
		return isInside;
	}
	
	/**
	 * Gets the city position into the list.
	 * 
	 * @return
	 */
	public Integer getCityPositionFromId() {
		Integer position = 0;
		City city = null;
		for (int i = 0; i < mCityList.size(); i++) {
			city = mCityList.get(i);
			if (city.getId() == mCityId) {
				position = i;
			}
		}
		return position;
	}
	
	/**
	 * Call the {@link Dialog} to adda {@link City} to the adapter.
	 */
	public void getCityDialog() {
		// Gets the city from the dialog.
		DialogHelper.showCustomDialog(this, R.layout.custom_dialog, R.string.activity_main__city_dialog_title,
			new OnClickListenerCustomDialog() {
				@Override
				public void onClickCallback(Context context, String city) {
					getCityInfoFromApi(city);
				}
			}
		);
	}
	
	/**
	 * Closes the current {@link ProgressDialog}.
	 */
	public void closeProgressDialog() {
		if (mDialog != null) {
			mDialog.cancel();
		}
	}
	
	/**
	 * Opens the {@link WeatherActivity}.
	 */
	public void openWeatherActivity() {
		// Extras.
		Bundle extras = new Bundle();
		String cityFromResquest = ContentManager.getInstance().getCityFromRequest();
		extras.putString(CITY_NAME_EXTRA, cityFromResquest);

		// Transition.
		closeProgressDialog();
		ActivityUtils.openActivity(this, WeatherActivity.class, extras);
		overridePendingTransition(R.anim.slide_up_from_outside, R.anim.slide_up_to_outside);
	}
	
	//--------------------------------------------------
	// Listener
	//--------------------------------------------------
	
	public void __________LISTENER__________() {};
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mCityName = mCityList.get(position).getCity();
		String message = getString(R.string.activity_main__loading_data) +
			" " + mCityName + "..."; 
		mDialog = DialogHelper.showProgressDialog(MainActivity.this, message);
		getWeatherInfo();
	}
	
	//--------------------------------------------------
	// Notifiable
	//--------------------------------------------------

	public void __________NOTIFIABLE__________() {};
	
	/**
	 * Do the actions for the return of the task called into {@link ContentManager}.
	 * 
	 * @param result
	 */
	public void weatherTask(Object result) {
		WeatherResponse response = (WeatherResponse)result;
		if (mCheckingCity) {
			setRequestListInCache(response);
		} else {
			setWeatherListInCache(response);
		}
	}
	
	/**
	 * Do the actions for the return of the task called into {@link ContentManager}.
	 * 
	 * @param result
	 */
	public void removeTask(Object result) {
		Boolean cityRemovedFromDatabase = (Boolean)result;
		// Gets the city from the database.
		if (cityRemovedFromDatabase) {
			ContentManager.getInstance().getCityList(this, MainActivity.this);
		}
	}
	
	@Override
	public void taskFinished(int type, Object result) {
		// Checks if is null.
		if (result != null) {
			if (type == ContentManager.FETCH_TASK.WEATHER) {
				weatherTask(result);
			} else if (type == ContentManager.FETCH_TASK.CITY_LIST) {
				updateCityAdapter(result);
			} else if (type == ContentManager.FETCH_TASK.CITY_ADD) {
				mCityAddedOnDatabase = (Boolean)result;
				addCityIntoAdapter();
			} else if (type == ContentManager.FETCH_TASK.CITY_REMOVE) {
				removeTask(result);
			}
		}
	}
}