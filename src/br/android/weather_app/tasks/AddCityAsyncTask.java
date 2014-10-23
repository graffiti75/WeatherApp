package br.android.weather_app.tasks;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import br.android.weather_app.helper.QueryHelper;
import br.android.weather_app.manager.ContentManager;
import br.android.weather_app.model.City;

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
		Boolean success = QueryHelper.persistCity(mContext, mCityList);
		return success;
	}
}