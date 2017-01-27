package com.example.rodrigo.weatherapp.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.presenter.di.components.DaggerWeatherComponent;
import com.example.rodrigo.weatherapp.presenter.di.module.WeatherModule;
import com.example.rodrigo.weatherapp.presenter.utils.NavigationUtils;
import com.example.rodrigo.weatherapp.databinding.ActivityWeatherBinding;
import com.example.rodrigo.weatherapp.model.Weather;
import com.example.rodrigo.weatherapp.model.WeatherResponse;
import com.example.rodrigo.weatherapp.presenter.WeatherPresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * WeatherActivity.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class WeatherActivity extends BaseActivity {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	// Context.
	private WeatherActivity mActivity = WeatherActivity.this;

	// Rest.
	private String mCityName;

	// Adapter.
	private List<Weather> mWeatherList;
	private ActivityWeatherBinding mBinding;

	@Inject
	protected WeatherPresenter mPresenter;

	//--------------------------------------------------
	// Base Activity
	//--------------------------------------------------

	@Override
	protected int getContentView() {
		return R.layout.activity_weather;
	}

	@Override
	protected void onViewReady(Bundle savedInstanceState, Intent intent) {
		super.onViewReady(savedInstanceState, intent);
		mBinding = DataBindingUtil.setContentView(mActivity, getContentView());

		mPresenter.getExtras();
		mPresenter.refreshList();
	}

	@Override
	protected void resolveDaggerDependency() {
		DaggerWeatherComponent.builder()
			.applicationComponent(getApplicationComponent())
			.weatherModule(new WeatherModule(this))
			.build().inject(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		NavigationUtils.animate(mActivity, NavigationUtils.Animation.BACK);
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
				mPresenter.refreshList();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//--------------------------------------------------
	// Callbacks
	//--------------------------------------------------

	public void setAdapter(WeatherResponse response) {
		mPresenter.setAdapter(response, mBinding);
	}

	public String getCityName() {
		return mCityName;
	}

	public void setCityName(String cityName) {
		mCityName = cityName;
	}

	public List<Weather> getWeatherList() {
		return mWeatherList;
	}

	public void setWeatherList(List<Weather> list) {
		mWeatherList = list;
	}
}