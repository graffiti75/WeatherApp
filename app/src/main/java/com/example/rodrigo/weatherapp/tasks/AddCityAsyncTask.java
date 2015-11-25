package com.example.rodrigo.weatherapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.helper.QueryHelper;
import com.example.rodrigo.weatherapp.manager.ContentManager;
import com.example.rodrigo.weatherapp.model.City;

import java.util.List;

/**
 * AddCityAsyncTask.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 21/10/2014
 */
public class AddCityAsyncTask extends AsyncTask<Void, Integer, Boolean> {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	// Application context reference.
	private static Context mContext;
	
	// The list to be persisted.
	private static List<City> mCityList;
	
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
		// Updating City list.
		try {
			return insert();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		ContentManager.getInstance().taskFinished(this, result);
	}

	//----------------------------------------------
	// Static Methods
	//----------------------------------------------
	
	/**
	 * Inserts the {@link City} into the database.
	 * 
	 * @return
	 */
	public static Boolean insert() {
		Log.i(AppConfiguration.COMMON_LOGGING_TAG,
			"[AddCityAsyncTask].insert() -> Adding city: " + mCityList.get(0).toString());
		Boolean success = QueryHelper.persistCity(mContext, mCityList);
		return success;
	}
}