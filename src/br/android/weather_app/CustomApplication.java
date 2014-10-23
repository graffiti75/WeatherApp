package br.android.weather_app;

import android.app.Application;
import br.android.weather_app.helper.DatabaseHelper;
import br.android.weather_app.manager.ContentManager;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * CustomApplication.java class.
 * A custom application class to manage the global application state.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class CustomApplication extends Application {
	
	//--------------------------------------------------
	// Application Life Cycle Methods
	//--------------------------------------------------
	
	@Override
	public void onCreate() {
		super.onCreate();
		// Setting the database helper class.
		OpenHelperManager.setOpenHelperClass(DatabaseHelper.class);
	}
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
        // Cleaning cached content.
      	ContentManager.getInstance().clean();
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		// Releasing the helper.
		OpenHelperManager.releaseHelper();
        // Cleaning cached content.
      	ContentManager.getInstance().clean();
	}
}