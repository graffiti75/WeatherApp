package com.example.rodrigo.weatherapp.view.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.controller.utils.ActivityUtils;
import com.example.rodrigo.weatherapp.controller.utils.ReactiveUtils;
import com.example.rodrigo.weatherapp.controller.utils.Utils;
import com.example.rodrigo.weatherapp.controller.utils.dialog.DialogUtils;
import com.example.rodrigo.weatherapp.databinding.ActivityMainBinding;
import com.example.rodrigo.weatherapp.model.City;
import com.example.rodrigo.weatherapp.view.adapter.CityAdapter;
import com.example.rodrigo.weatherapp.view.listeners.RecyclerTouchListener;
import com.example.rodrigo.weatherapp.view.listeners.RecyclerViewListeners;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class MainActivity extends AppCompatActivity {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	/**
	 * Context.
	 */

	private MainActivity mActivity = MainActivity.this;

	/**
	 * Adapter.
	 */

	private List<City> mCityList = new ArrayList<>();
	private CityAdapter mAdapter;
	private ActivityMainBinding mBinding;

	/**
	 * Rest.
	 */

	private String mCityName;

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(mActivity, R.layout.activity_main);

		Utils.initToolbar(mActivity, false, R.string.activity_main__title);
		setAdapter();
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
		switch (item.getItemId()) {
			case R.id.id_menu_add:
				getCityDialog();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------

	private void setAdapter() {
		mBinding.idActivityMainRecyclerView.addOnItemTouchListener(setRecyclerViewListener());

		String message = getString(R.string.reading_from_database);
		ProgressDialog dialog = DialogUtils.showProgressDialog(mActivity, message);
		ReactiveUtils.getCityList(mActivity, false, dialog);
	}
	
	private Boolean cityAlreadyInAdapter(String cityName) {
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

	private void getCityInfoFromApi(String cityName) {
		mCityName = cityName;
		String message = getString(R.string.activity_main__loading_data, mCityName);
		Dialog dialog = DialogUtils.showProgressDialog(mActivity, message);
		
		ReactiveUtils.getWeather(mActivity, cityName, dialog);
	}
	
	private void addCityInDatabase(String cityName) {
		City lastElement = mCityList.get(mCityList.size() - 1);
		Integer index = lastElement.getId() + 1;
		City newCity = new City(index, cityName);
		List<City> list = new ArrayList<>();
		list.add(newCity);

		String message = getString(R.string.inserting_in_database);
		ProgressDialog dialog = DialogUtils.showProgressDialog(mActivity, message);
		ReactiveUtils.insertCityList(mActivity, list, newCity, dialog);
	}

	private void removeCityFromDatabase(Integer position) {
		DialogUtils.showConfirmDialog(mActivity, 0, R.string.activity_main__remove_city_dialog_title,
			confirmListener(position), cancelListener());
	}

	private DialogInterface.OnClickListener confirmListener(final int position) {
		return (context, which) -> {
            City city = mCityList.get(position);

            String message = MainActivity.this.getString(R.string.removing_from_database);
            ProgressDialog dialog = DialogUtils.showProgressDialog(mActivity, message);
            ReactiveUtils.removeCity(mActivity, city, dialog);
        };
	}

	private DialogInterface.OnClickListener cancelListener() {
		return (context, which) -> context.dismiss();
	}
	
	private void getCityDialog() {
		if (Utils.checkConnection(mActivity)) {
			// Gets the city from the dialog.
			DialogUtils.showCustomDialog(mActivity, R.layout.custom_dialog, R.string.activity_main__add_city_dialog_title,
				(context, city) -> getCityInfoFromApi(city)
			);
		} else {
			DialogUtils.showNoConnectionDialog(mActivity);
		}
	}
	
	private RecyclerTouchListener setRecyclerViewListener() {
		return new RecyclerTouchListener(mActivity, mBinding.idActivityMainRecyclerView,
			new RecyclerViewListeners() {
			@Override
			public void onClick(View view, final int position) {
				if (Utils.checkConnection(mActivity)) {
					mCityName = mCityList.get(position).getCity();
					ActivityUtils.startActivityExtras(mActivity, WeatherActivity.class,
						AppConfiguration.CITY_NAME_EXTRA, mCityName);
				} else {
					DialogUtils.showNoConnectionDialog(mActivity);
				}
			}

			@Override
			public void onLongClick(View view, int position) {
				removeCityFromDatabase(position);
			}
		});
	}

	//--------------------------------------------------
	// Callbacks
	//--------------------------------------------------

	public void citySearchedExists(String cityName) {
		Boolean cityRepeated = cityAlreadyInAdapter(cityName);
		if (cityRepeated) {
			DialogUtils.showSimpleAlert(mActivity, R.string.activity_main__repeated_city_dialog_title,
				R.string.activity_main__repeated_city_dialog_message);
		} else {
			addCityInDatabase(cityName);
		}
	}

	public void changeAdapter(List<City> list, Boolean updateAdapter) {
		if (updateAdapter) {
			if (list == null || list.size() <= 0) {
				Toast.makeText(mActivity, R.string.database_error, Toast.LENGTH_LONG);
			} else {
				mCityList.addAll(list);
				mAdapter.notifyDataSetChanged();
			}
		} else {
			mAdapter = new CityAdapter(mActivity, list);
			LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
			layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
			mBinding.idActivityMainRecyclerView.setLayoutManager(layoutManager);
			mBinding.idActivityMainRecyclerView.setItemAnimator(new DefaultItemAnimator());
			mBinding.idActivityMainRecyclerView.setAdapter(mAdapter);
		}
		mCityList = list;
	}

	public void addCityIntoAdapter(City newCity, Boolean result) {
		if (result) {
			mCityList.add(newCity);
			mAdapter.notifyDataSetChanged();
		} else {
			Toast.makeText(mActivity, R.string.database_error, Toast.LENGTH_LONG);
		}
	}

	public void removeCityFromAdapter(Boolean result, City city) {
		List<City> list = new ArrayList<>();
		for (City item: mCityList) {
			if (city.getId() != item.getId()) {
				list.add(item);
			}
		}

		if (result) {
			mCityList.clear();
			mCityList.addAll(list);
			mAdapter.notifyDataSetChanged();
		} else {
			Toast.makeText(mActivity, R.string.database_error, Toast.LENGTH_LONG);
		}
	}
}