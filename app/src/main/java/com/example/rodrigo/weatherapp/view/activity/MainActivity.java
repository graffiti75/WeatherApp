package com.example.rodrigo.weatherapp.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.databinding.ActivityMainBinding;
import com.example.rodrigo.weatherapp.model.City;
import com.example.rodrigo.weatherapp.presenter.MainPresenter;
import com.example.rodrigo.weatherapp.presenter.di.components.DaggerMainComponent;
import com.example.rodrigo.weatherapp.presenter.di.module.MainModule;
import com.example.rodrigo.weatherapp.view.adapter.CityAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * MainActivity.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class MainActivity extends BaseActivity {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	// Context.
	private MainActivity mActivity = MainActivity.this;

	// Adapter.
	private List<City> mCityList = new ArrayList<>();
	private CityAdapter mAdapter;
	private ActivityMainBinding mBinding;

	// Rest.
	private String mCityName;

	@Inject
	protected MainPresenter mPresenter;

	//--------------------------------------------------
	// Base Activity
	//--------------------------------------------------

	@Override
	protected int getContentView() {
		return R.layout.activity_main;
	}

	@Override
	protected void onViewReady(Bundle savedInstanceState, Intent intent) {
		super.onViewReady(savedInstanceState, intent);
		mBinding = DataBindingUtil.setContentView(mActivity, getContentView());

		showBackArrow(mActivity, false, getString(R.string.activity_main__title));
		mPresenter.setAdapter(mBinding);
	}

	@Override
	protected void resolveDaggerDependency() {
		DaggerMainComponent.builder()
			.applicationComponent(getApplicationComponent())
			.mainModule(new MainModule(this))
			.build().inject(this);
	}

	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}

	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.id_menu_add) {
			mPresenter.getCityDialog();
		}
		return super.onOptionsItemSelected(item);
	}

	//--------------------------------------------------
	// Callbacks
	//--------------------------------------------------

	public void citySearchedExists(String cityName) {
		mPresenter.citySearchedExists(cityName);
	}

	public void changeAdapter(List<City> list, Boolean updateAdapter) {
		mPresenter.changeAdapter(list, mBinding, updateAdapter);
	}

	public void addCityIntoAdapter(City newCity, Boolean result) {
		mPresenter.addCityIntoAdapter(newCity, mBinding, result);
	}

	public void removeCityFromAdapter(Boolean result, City city) {
		mPresenter.removeCityFromAdapter(result, city);
	}

	public String getCityName() {
		return mCityName;
	}

	public void setCityName(String cityName) {
		mCityName = cityName;
	}

	public List<City> getCityList() {
		return mCityList;
	}

	public void setCityList(List<City> list) {
		mCityList = list;
	}

	public CityAdapter getAdapter() {
		return mAdapter;
	}

	public void setAdapter(CityAdapter adapter) {
		mAdapter = adapter;
	}
}