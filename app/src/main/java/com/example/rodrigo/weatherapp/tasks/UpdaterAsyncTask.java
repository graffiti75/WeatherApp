package com.example.rodrigo.weatherapp.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.rodrigo.weatherapp.helper.QueryHelper;
import com.example.rodrigo.weatherapp.manager.ContentManager;
import com.example.rodrigo.weatherapp.model.AppInfo;
import com.example.rodrigo.weatherapp.utils.Utils;

/**
 * UpdaterAsyncTask.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 21/10/2014
 */
public class UpdaterAsyncTask extends AsyncTask<Void, Integer, Boolean> {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	final private Context mContext;
	
	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public UpdaterAsyncTask(Context activity) {
		mContext = activity;
	}
	
	//--------------------------------------------------
	// Async Task
	//--------------------------------------------------

	@Override
	protected Boolean doInBackground(Void... params) {
		// Getting the AppInfo and updating the AppInfo update time.
		final AppInfo info = QueryHelper.getAppInfo(mContext);
		Boolean databaseNeeedsUpdate = QueryHelper.updateAppInfoUpdateTime(mContext, info.getId(), Utils.getCurrentInMillis());
		
		return databaseNeeedsUpdate;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		ContentManager.getInstance().taskFinished(this, result);
	}
}