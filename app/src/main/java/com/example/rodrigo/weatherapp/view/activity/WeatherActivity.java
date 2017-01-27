package com.example.rodrigo.weatherapp.view.activity;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.controller.utils.ReactiveUtils;
import com.example.rodrigo.weatherapp.controller.utils.Utils;
import com.example.rodrigo.weatherapp.controller.utils.dialog.DialogUtils;
import com.example.rodrigo.weatherapp.databinding.ActivityWeatherBinding;
import com.example.rodrigo.weatherapp.model.Weather;
import com.example.rodrigo.weatherapp.model.WeatherResponse;
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

	private WeatherDayAdapter mAdapter;
	private List<Weather> mWeatherList;
	private ActivityWeatherBinding mBinding;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(mActivity, R.layout.activity_weather);
		
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
		if (Utils.checkConnection(mActivity)) {
			// Shows a loading dialog for the user.
			String message = getString(R.string.activity_weather__loading_data, mCityName);
			ProgressDialog dialog = DialogUtils.showProgressDialog(mActivity, message);

			// Calls the API.
			ReactiveUtils.getWeather(mActivity, mCityName, dialog);
		} else {
			DialogUtils.showNoConnectionDialog(mActivity);
		}
	}

	//--------------------------------------------------
	// Callbacks
	//--------------------------------------------------

	public void setAdapter(WeatherResponse response) {
		mWeatherList = response.getData().getWeather();
		mAdapter = new WeatherDayAdapter(mWeatherList);

		LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mBinding.idActivityWeatherRecyclerView.setLayoutManager(layoutManager);
		mBinding.idActivityWeatherRecyclerView.setItemAnimator(new DefaultItemAnimator());
		mBinding.idActivityWeatherRecyclerView.setAdapter(mAdapter);
	}
}