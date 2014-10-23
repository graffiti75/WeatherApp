package br.android.weather_app.tasks;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import br.android.weather_app.data.ListItems;
import br.android.weather_app.helper.QueryHelper;
import br.android.weather_app.manager.ContentManager;
import br.android.weather_app.model.City;

/**
 * GetCityListAsyncTask.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 21/10/2014
 */
public class GetCityListAsyncTask extends AsyncTask<Void, Integer, Object> {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	// Application context reference.
	private final Context mContext;
	
	// Flag for Alderman retrieval.
	private boolean mNeedsUpdate;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public GetCityListAsyncTask(Context context, boolean needsUpdate) {
		mContext = context;
		mNeedsUpdate = needsUpdate;
	}

	//----------------------------------------------
	// Async Task
	//----------------------------------------------
	
	@Override
	protected Object doInBackground(Void... params) {
		// Updating City list.
		try {
			return update(mContext, mNeedsUpdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Object result) {
		ContentManager.getInstance().taskFinished(this, result);
	}

	//----------------------------------------------
	// Static Methods
	//----------------------------------------------
	
	/**
	 * Updates the {@link City} if needed or fetch it from the database.
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static Object update(Context context, boolean needsUpdate) throws Exception {
		List<City> cachedList = ListItems.getCityList();
		List<City> returnList = new ArrayList<City>();
		
		// If we need to grab the default values.
		if (needsUpdate) {
			returnList = cachedList;
			QueryHelper.persistCity(context, cachedList);
		} else {
			// We just need to grab the data from the database.
			returnList = QueryHelper.getCityList(context);
		}
		return returnList;
	}
}