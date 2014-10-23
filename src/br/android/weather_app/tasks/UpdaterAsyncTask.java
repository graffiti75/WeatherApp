package br.android.weather_app.tasks;

import android.content.Context;
import android.os.AsyncTask;
import br.android.weather_app.helper.QueryHelper;
import br.android.weather_app.manager.ContentManager;
import br.android.weather_app.model.AppInfo;
import br.android.weather_app.utils.Utils;

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