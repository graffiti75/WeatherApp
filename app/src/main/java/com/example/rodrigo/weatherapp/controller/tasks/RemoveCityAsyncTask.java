package com.example.rodrigo.weatherapp.controller.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.model.City;
import com.example.rodrigo.weatherapp.model.database.DatabaseUtils;

/**
 * RemoveCityAsyncTask.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 25, 2017
 */
public class RemoveCityAsyncTask extends AsyncTask<Void, Integer, Boolean> {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	// Application context reference.
	private Context mContext;
	
	// The city to be deleted.
	private City mCity;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public RemoveCityAsyncTask(Context context, City city) {
		mContext = context;
		mCity = city;
	}

	//----------------------------------------------
	// Async Task
	//----------------------------------------------
	
	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			return remove();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//----------------------------------------------
	// Methods
	//----------------------------------------------

	private Boolean remove() {
		Log.i(AppConfiguration.TAG,
			"[RemoveCityAsyncTask].remove() -> Removing city: " + mCity.toString());
		Boolean success = DatabaseUtils.deleteCity(mContext, mCity.getId());
		return success;
	}
}