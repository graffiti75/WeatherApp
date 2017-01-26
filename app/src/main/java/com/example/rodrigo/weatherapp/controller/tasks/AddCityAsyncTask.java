package com.example.rodrigo.weatherapp.controller.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.model.City;
import com.example.rodrigo.weatherapp.model.database.DatabaseUtils;

import java.util.List;

/**
 * AddCityAsyncTask.java class.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 25, 2017
 */
public class AddCityAsyncTask extends AsyncTask<Void, Integer, Boolean> {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	// Application context reference.
	private Context mContext;
	
	// The list to be persisted.
	private List<City> mCityList;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public AddCityAsyncTask(Context context, List<City> cityList) {
		mContext = context;
		mCityList = cityList;
	}

	//----------------------------------------------
	// Async Task
	//----------------------------------------------
	
	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			return insert();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//----------------------------------------------
	// Methods
	//----------------------------------------------
	
	public Boolean insert() {
		Log.i(AppConfiguration.TAG,
			"[AddCityAsyncTask].insert() -> Adding city: " + mCityList.get(0).toString());
		Boolean success = DatabaseUtils.insertCityList(mContext, mCityList);
		return success;
	}
}