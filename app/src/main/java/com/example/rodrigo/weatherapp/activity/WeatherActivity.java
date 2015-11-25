package com.example.rodrigo.weatherapp.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.adapter.WeatherDayAdapter;
import com.example.rodrigo.weatherapp.api.WeatherService;
import com.example.rodrigo.weatherapp.api.model.Weather;
import com.example.rodrigo.weatherapp.api.model.WeatherResponse;
import com.example.rodrigo.weatherapp.helper.DialogHelper;
import com.example.rodrigo.weatherapp.manager.ContentManager;
import com.example.rodrigo.weatherapp.tasks.Notifiable;
import com.example.rodrigo.weatherapp.utils.Utils;

import java.util.List;

import retrofit.RestAdapter;

/**
 * WeatherActivity.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class WeatherActivity extends AppCompatActivity implements Notifiable {
	
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
		getMenuInflater().inflate(R.menu.menu_activity_weather, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
			case R.id.id_menu_refresh:
				refreshList();
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
	 * Adds values of the list, customizes adapter, and set's list view adapter and it's mListener.
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
		RestAdapter restAdapter = new RestAdapter.Builder()
			.setEndpoint("http://api.worldweatheronline.com")
			.build();

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
	
	/**
	 * Refreshes the {@link Weather} list.
	 */
	public void refreshList() {
		// Check if there is Network connection.
		if (Utils.checkConnection(this)) {
			// Shows a loading dialog for the user.
			String message = getString(R.string.activity_weather__loading_data) +
				" " + mCityName + "..."; 
			mDialog = DialogHelper.showProgressDialog(this, message);
			
			// Calls the API.
			setRetrofitService();
			ContentManager.getInstance().getWeatherResponse(this, mService, mCityName);
		} else {
			showNoConnectionDialog();
		}
	}
	
	/**
	 * Shows a error message for the user if we don't have Network connection.
	 */
	public void showNoConnectionDialog() {
		DialogHelper.showSimpleAlert(this, R.string.network_error_dialog_title,
			R.string.network_error_dialog_message, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			}
		);
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