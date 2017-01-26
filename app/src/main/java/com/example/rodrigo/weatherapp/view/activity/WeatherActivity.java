package com.example.rodrigo.weatherapp.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.controller.helper.DialogHelper;
import com.example.rodrigo.weatherapp.controller.utils.Utils;
import com.example.rodrigo.weatherapp.model.Weather;
import com.example.rodrigo.weatherapp.model.WeatherResponse;
import com.example.rodrigo.weatherapp.model.api.RetrofitUtils;
import com.example.rodrigo.weatherapp.view.adapter.WeatherDayAdapter;

import java.util.List;

/**
 * WeatherActivity.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class WeatherActivity extends AppCompatActivity {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	/**
	 * Context.
	 */

	private WeatherActivity mActivity = WeatherActivity.this;

	/**
	 * Rest.
	 */

	private String mCityName;

	/**
	 * Adapter.
	 */

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
		refreshList();
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

	private void getExtras() {
		Bundle extras = getIntent().getExtras(); 
		if (extras != null) {
			mCityName = extras.getString(AppConfiguration.CITY_NAME_EXTRA);
		}
	}

	private void setActionBar() {
		ActionBar action = getSupportActionBar();
		action.setTitle(mCityName);
		action.setDisplayHomeAsUpEnabled(true);
	}

	private void refreshList() {
		// Check if there is Network connection.
		if (Utils.checkConnection(this)) {
			// Shows a loading dialog for the user.
			String message = getString(R.string.activity_weather__loading_data, mCityName);
			ProgressDialog dialog = DialogHelper.showProgressDialog(this, message);

			// Calls the API.
			RetrofitUtils.getWeather(mActivity, mCityName, dialog);
		} else {
			showNoConnectionDialog();
		}
	}

	private void showNoConnectionDialog() {
		DialogHelper.showSimpleAlert(this, R.string.network_error_dialog_title,
				R.string.network_error_dialog_message, (dialog, which) -> dialog.cancel()
		);
	}
	
	//--------------------------------------------------
	// Callbacks
	//--------------------------------------------------

	public void setAdapter(WeatherResponse response) {
		mWeatherList = response.getData().getWeather();
		mAdapter = new WeatherDayAdapter(this, mWeatherList);
		mListView = (ListView)findViewById(R.id.id_activity_weather__listview);
		mListView.setAdapter(mAdapter);
	}
}