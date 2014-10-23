package br.android.weather_app;

import android.app.Application;
import br.android.weather_app.helper.DatabaseHelper;
import br.android.weather_app.manager.ContentManager;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * A custom application class to manage the global application state.
 * 
 * @author Rodrigo Cericatto
 * @since 14/10/2014
 */
public class CustomApplication extends Application {
	
	//--------------------------------------------------
	// Statics
	//--------------------------------------------------
	
	// The root package name.
	public static final String ROOT_PACKAGE_NAME;
	static {
		Class<?> clazz = CustomApplication.class;
		ROOT_PACKAGE_NAME = clazz.getPackage().getName();
	}
	
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