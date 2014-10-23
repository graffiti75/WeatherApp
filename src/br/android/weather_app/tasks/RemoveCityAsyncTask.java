package br.android.weather_app.tasks;

import android.content.Context;
import android.os.AsyncTask;
import br.android.weather_app.helper.QueryHelper;
import br.android.weather_app.manager.ContentManager;
import br.android.weather_app.model.City;

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
		Boolean success = QueryHelper.removeCity(mContext, mCity);
		return success;
	}
}