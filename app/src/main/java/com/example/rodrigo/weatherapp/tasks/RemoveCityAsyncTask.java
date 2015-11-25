package com.example.rodrigo.weatherapp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.helper.QueryHelper;
import com.example.rodrigo.weatherapp.manager.ContentManager;
import com.example.rodrigo.weatherapp.model.City;

/**
 * RemoveCityAsyncTask.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 21/10/2014
 */
public class RemoveCityAsyncTask extends AsyncTask<Void, Integer, Boolean> {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	// Application context reference.
	private static Context mContext;
	
	// The city to be deleted.
	private static City mCity;
	
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
		// Removing City.
		try {
			return remove();
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
	 * Removes the {@link City} from the database.
	 * 
	 * @return
	 */
	public static Boolean remove() {
		Log.i(AppConfiguration.COMMON_LOGGING_TAG,
			"[RemoveCityAsyncTask].remove() -> Removing city: " + mCity.toString());
		Boolean success = QueryHelper.removeCity(mContext, mCity);
		return success;
	}
}