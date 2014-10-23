package br.android.weather_app.helper;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;
import br.android.weather_app.AppConfiguration;
import br.android.weather_app.model.AppInfo;
import br.android.weather_app.model.City;

import com.j256.ormlite.dao.Dao;

/**
 * QueryHelper class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class QueryHelper {
	
	//--------------------------------------------------
	// AppInfo
	//--------------------------------------------------
	
	/**
	 * Gets the app info object from the database.
	 * 
	 * @param context The context of the application.
	 * 
	 * @return The AppInfo.
	 */
	public static AppInfo getAppInfo(Context context) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		
		try {
			// Creating the dao object.
			Dao<AppInfo, Integer> infoDao = databaseHelper.getDao(AppInfo.class);

			// Getting the info item from the database.
			List<AppInfo> list = infoDao.queryForAll();
			AppInfo info = null;
			if (list.size() != 0 && list != null) {
				info = list.get(list.size() - 1);
			}
			
			// Returns the existent info.
			if (info != null) {
				return info;
			}
			
			// No info found, creating a new one.
			info = new AppInfo();
			info.id = AppInfo.ID;
			info.lastUpdate = 0;
			
			// Updating the info.
			databaseHelper.createOrUpdate(info);
			return info;
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} catch (IllegalArgumentException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Couldn't create the AppInfo.", e);
		} catch (IllegalAccessException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Couldn't create the AppInfo.", e);
		} finally {
			DatabaseHelper.releaseHelper();
		}
		return null;
	}

	/**
	 * Updates the app info last update time.
	 * 
	 * @param context The context of the application.
	 * @param id The id of the database.
	 * @param currentMillis The current time in milliseconds.
	 * 
	 * @return The status about the update (if needs or not).
	 */
	public static boolean updateAppInfoUpdateTime(Context context, Integer id, long currentMillis) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		
		// Getting the existent app info.
		AppInfo info = new AppInfo();
		info.lastUpdate = currentMillis;
		
		// Check if the database needs update.
		Boolean needsUpdate = false;
		if (AppConfiguration.DATABASE_VERSION > id) {
			info.id = AppConfiguration.DATABASE_VERSION;
			needsUpdate = true;
		}
		
		try {
			// Updating.
			databaseHelper.createOrUpdate(info);
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} catch (IllegalArgumentException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Couldn't create the AppInfo.", e);
		} catch (IllegalAccessException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Couldn't create the AppInfo.", e);
		} finally {
			DatabaseHelper.releaseHelper();
		}
		return needsUpdate;
	}
	
	//--------------------------------------------------
	// City
	//--------------------------------------------------
	
	/**
	 * Gets the {@link City} list from the database.
	 * 
	 * @param context The context of the application.
	 * 
	 * @return The list of {@link City}.
	 */
	public static List<City> getCityList(Context context) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		List<City> list = null;
		
		try {
    		Dao<City, Integer> cityDao = databaseHelper.getDao(City.class);
    		list = cityDao.queryForAll();
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} finally {
			DatabaseHelper.releaseHelper();
		}
		
		return list;
	}
	
	/**
	 * Persists the {@link City} list into the database.
	 * 
	 * @param context The context of the application.
	 * @param list The {@link City} list.
	 * 
	 * @return
	 */
	public static Boolean persistCity(Context context, final List<City> list) {
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		Boolean success = true;
		
		try {
			// Persisting.
			for (City city : list) {
				// Persisting.
				databaseHelper.createOrUpdate(city);
			}
		} catch (IllegalAccessException e) {
			success = false;
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error while trying to create or update City.", e);
		} catch (SQLException e) {
			success = false;
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} catch (IllegalArgumentException e) {
			success = false;
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} finally {
			// Releasing the database helper.
			DatabaseHelper.releaseHelper();
		}
		return success;
	}
	
	/**
	 * Removes the {@link City} from the database.
	 * 
	 * @param context The context of the application.
	 * @param city The {@link City} to be removed.
	 * 
	 * @return
	 */
	public static Boolean removeCity(Context context, City city) {
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		Boolean success = true;
		
		try {
			databaseHelper.delete(city);
		} catch (IllegalArgumentException e) {
			success = false;
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} finally {
			// Releasing the database helper.
			DatabaseHelper.releaseHelper();
		}
		return success;
	}
}