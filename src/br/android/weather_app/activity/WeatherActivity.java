package br.android.weather_app.activity;

import java.util.List;

import retrofit.RestAdapter;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ListView;
import br.android.weather_app.R;
import br.android.weather_app.adapter.WeatherDayAdapter;
import br.android.weather_app.api.WeatherService;
import br.android.weather_app.api.model.Weather;
import br.android.weather_app.api.model.WeatherResponse;
import br.android.weather_app.helper.DialogHelper;
import br.android.weather_app.manager.ContentManager;
import br.android.weather_app.tasks.Notifiable;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * WeatherActivity.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class WeatherActivity extends SherlockActivity implements Notifiable {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	// Rest.
	private WeatherService mService;
	private String mCityName;
	private Dialog mDialog = null;
	
	// Adapter.
	private ListView mListView;
	private WeatherDayAdapter mAdapter;
	private List<Weather> mWeatherList;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		
		getExtras();
		setActionBar();
		setAdapter();
	}
	
	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_activity_weather, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
			case R.id.id_menu_refresh:
				// Shows a dialog for the user.
				String message = getString(R.string.activity_weather__loading_data) +
					" " + mCityName + "..."; 
				mDialog = DialogHelper.showProgressDialog(this, message);
				
				// Calls the API.
				setRetrofitService();
				ContentManager.getInstance().getWeatherResponse(this, mService, mCityName);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//--------------------------------------------------
	// Other Methods
	//--------------------------------------------------
	
	/**
	 * Gets the extras.
	 */
	public void getExtras() {
		Bundle extras = getIntent().getExtras(); 
		if (extras != null) {
			mCityName = extras.getString(MainActivity.CITY_NAME_EXTRA);
		}
	}
	
	/**
	 * Sets the {#link ActionBar}.
	 */
	public void setActionBar() {
		ActionBar action = getSupportActionBar();
		action.setTitle(mCityName);
		action.setDisplayHomeAsUpEnabled(true);
	}
	
	/**
	 * Adds values of the list, customizes adapter, and set's list view adapter and it's listener.
	 */
	public void setAdapter() {
		// Gets the Weather list.
		mWeatherList = ContentManager.getInstance().getWeatherList();
		
		// Sets the adapter.
	    mAdapter = new WeatherDayAdapter(this, mWeatherList);
		mListView = (ListView)findViewById(R.id.id_activity_weather__listview);
		mListView.setAdapter(mAdapter);
	}
	
	//--------------------------------------------------
	// API Methods
	//--------------------------------------------------

	/**
	 * Sets the {@link WeatherService} from the Retrofit.
	 */
	public void setRetrofitService() {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
			"http://api.worldweatheronline.com").build();

		mService = restAdapter.create(WeatherService.class);
	}
	
	/**
	 * Shows the {@link WeatherResponse} info for the user.
	 * 
	 * @param response
	 */
	public void setWeatherListInCache(WeatherResponse response) {
		ContentManager.getInstance().setWeatherList(response);
		if (mDialog != null) {
			mDialog.cancel();
		}
		setAdapter();
	}
	
	//--------------------------------------------------
	// Notifiable
	//--------------------------------------------------

	@Override
	public void taskFinished(int type, Object result) {
		if (type == ContentManager.FETCH_TASK.WEATHER) {
			WeatherResponse response = (WeatherResponse) result;
			setWeatherListInCache(response);
		}
	}
}