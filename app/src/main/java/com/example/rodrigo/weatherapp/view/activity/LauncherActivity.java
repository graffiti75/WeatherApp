package com.example.rodrigo.weatherapp.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.model.City;
import com.example.rodrigo.weatherapp.presenter.utils.ActivityUtils;
import com.example.rodrigo.weatherapp.presenter.utils.ReactiveUtils;
import com.example.rodrigo.weatherapp.presenter.utils.dialog.DialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * LauncherActivity.java class.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 25, 2017
 */
public class LauncherActivity extends BaseActivity {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	// Context.
	private LauncherActivity mActivity = LauncherActivity.this;

	//--------------------------------------------------
	// Base Activity
	//--------------------------------------------------

	@Override
	protected int getContentView() {
		return R.layout.activity_launcher;
	}

	@Override
	protected void onViewReady(Bundle savedInstanceState, Intent intent) {
		super.onViewReady(savedInstanceState, intent);
		setContentView(getContentView());

		showBackArrow(mActivity, false, "");
		getCityListFromDatabase();
	}

	@Override
	protected void resolveDaggerDependency() {
	}

	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------

	private void getCityListFromDatabase() {
		List<City> list = new ArrayList<>();
		for (int i = 0; i < AppConfiguration.DEFAULT_DATA.length; i++) {
			list.add(new City(i + 1, AppConfiguration.DEFAULT_DATA[i]));
		}
		String message = getString(R.string.inserting_in_database);
		ProgressDialog dialog = DialogUtils.showProgressDialog(mActivity, message);
		ReactiveUtils.insertCityList(mActivity, list, dialog);
    }

	//--------------------------------------------------
	// Callbacks
	//--------------------------------------------------

	public void callMainActivity(Boolean success) {
		if (success) {
			ActivityUtils.startActivity(mActivity, MainActivity.class);
		} else {
			Toast.makeText(mActivity, R.string.database_error, Toast.LENGTH_LONG);
		}
	}
}