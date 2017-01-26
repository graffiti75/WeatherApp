package com.example.rodrigo.weatherapp.view.activity;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.controller.helper.DialogHelper;
import com.example.rodrigo.weatherapp.controller.tasks.AddCityAsyncTask;
import com.example.rodrigo.weatherapp.controller.tasks.RemoveCityAsyncTask;
import com.example.rodrigo.weatherapp.controller.utils.ActivityUtils;
import com.example.rodrigo.weatherapp.controller.utils.Utils;
import com.example.rodrigo.weatherapp.databinding.ActivityMainBinding;
import com.example.rodrigo.weatherapp.model.City;
import com.example.rodrigo.weatherapp.model.api.RetrofitUtils;
import com.example.rodrigo.weatherapp.model.database.CityProvider;
import com.example.rodrigo.weatherapp.model.database.DatabaseUtils;
import com.example.rodrigo.weatherapp.view.adapter.CityAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class MainActivity extends AppCompatActivity implements OnItemClickListener {

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
		mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

		setAdapter();
		registerForContextMenu(mBinding.idActivityMainListView);
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
                return super.onContextItemSelected(item);
        }
    }
	    
	//--------------------------------------------------
	// Adapter Methods
	//--------------------------------------------------

	public void setAdapter() {
		mCityList = DatabaseUtils.getCityList(mActivity);
		mAdapter = new CityAdapter(this, mCityList);
		mBinding.idActivityMainListView.setAdapter(mAdapter);
		mBinding.idActivityMainListView.setOnItemClickListener(this);
	}
	
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

	//--------------------------------------------------
	// API Methods
	//--------------------------------------------------
	
	public void getCityInfoFromApi(String cityName) {
		mCityName = cityName;
		String message = getString(R.string.activity_main__loading_data, mCityName);
		Dialog dialog = DialogHelper.showProgressDialog(MainActivity.this, message);
		
		RetrofitUtils.getWeather(mActivity, cityName, dialog);
	}
	
	public void showNoConnectionDialog() {
		DialogHelper.showSimpleAlert(this, R.string.network_error_dialog_title,
			R.string.network_error_dialog_message, (dialog, which) -> dialog.cancel());
	}

	//--------------------------------------------------
	// Insert or Remove Methods
	//--------------------------------------------------
	
	private void addCityInDatabase(String cityName) {
		City lastElement = mCityList.get(mCityList.size() - 1);
		Integer index = lastElement.getId() + 1;
		City newCity = new City(index, cityName);
		List<City> list = new ArrayList<>();
		list.add(newCity);

		AddCityAsyncTask task = new AddCityAsyncTask(mActivity, list) {
			@Override
			protected void onPostExecute(Boolean result) {
				addCityIntoAdapter(newCity, result);
			}
		};
		task.execute();
	}
	
	private void addCityIntoAdapter(City newCity, Boolean result) {
		if (result) {
			mCityList.add(newCity);
			mAdapter.notifyDataSetChanged();
		}
	}

	private void removeCityFromDatabase(Integer position) {
		City city = mCityList.get(position);
		RemoveCityAsyncTask task = new RemoveCityAsyncTask(mActivity, city) {
			@Override
			protected void onPostExecute(Boolean result) {
				if (result) {
					updateCityAdapter();
				} else {
					Toast.makeText(mActivity, R.string.database_error, Toast.LENGTH_LONG);
				}
			}
		};
		task.execute();
	}
	
	private void updateCityAdapter() {
		mCityList.clear();
		CityProvider database = DatabaseUtils.openDatabase(mActivity);
		List<City> list = DatabaseUtils.getCityList(mActivity);
		if (list == null || list.size() <= 0) {
			Toast.makeText(mActivity, R.string.database_error, Toast.LENGTH_LONG);
		} else {
			mCityList.addAll(list);
			mAdapter.notifyDataSetChanged();
		}
		DatabaseUtils.closeDatabase(database);
	}
	
	//--------------------------------------------------
	// Other Methods
	//--------------------------------------------------
	
	private void getCityDialog() {
		if (Utils.checkConnection(this)) {
			// Gets the city from the dialog.
			DialogHelper.showCustomDialog(this, R.layout.custom_dialog, R.string.activity_main__city_dialog_title,
				(context, city) -> getCityInfoFromApi(city)
			);
		} else {
			showNoConnectionDialog();
		}
	}
	
	private void openWeatherActivity(String cityName) {
		Bundle extras = new Bundle();
		extras.putString(AppConfiguration.CITY_NAME_EXTRA, cityName);
		ActivityUtils.openActivity(this, WeatherActivity.class, extras);
		overridePendingTransition(R.anim.slide_up_from_outside, R.anim.slide_up_to_outside);
	}

	//--------------------------------------------------
	// Callback
	//--------------------------------------------------

	public void citySearchedExists(String cityName) {
		Boolean cityRepeated = cityAlreadyInAdapter(cityName);
		if (cityRepeated) {
			DialogHelper.showSimpleAlert(this, R.string.activity_main__repeated_city_dialog_title,
				R.string.activity_main__repeated_city_dialog_message);
		} else {
			addCityInDatabase(cityName);
		}
	}
	
	//--------------------------------------------------
	// Listener
	//--------------------------------------------------
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (Utils.checkConnection(this)) {
			mCityName = mCityList.get(position).getCity();
			openWeatherActivity(mCityName);
		} else {
			showNoConnectionDialog();
		}
	}
}