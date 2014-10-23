package br.android.weather_app.helper;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;
import br.android.weather_app.AppConfiguration;
import br.android.weather_app.model.AppInfo;
import br.android.weather_app.model.Bean;

import com.j256.ormlite.dao.Dao;

/**
 * QueryHelper class.
 * 
 * @author Rodrigo Cericatto
 * @since 14/10/2014
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
	// Bean
	//--------------------------------------------------
	
	/**
	 * Gets the Bean list from the database.
	 * 
	 * @param context The context of the application.
	 * 
	 * @return The list of Bean.
	 */
	public static List<Bean> getBeanList(Context context) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		List<Bean> list = null;
		
		try {
    		// Getting the Bean.
    		Dao<Bean, Integer> beanDao = databaseHelper.getDao(Bean.class);
    		// Return all Bean.
    		list = beanDao.queryForAll();
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} finally {
			DatabaseHelper.releaseHelper();
		}
		
		return list;
	}
	
	/**
	 * Persists the Bean list into the database.
	 * 
	 * @param context The context of the application.
	 * @param list The Bean list.
	 */
	public static void persistBean(Context context, final List<Bean> list) {
		// Getting the database helper.
		DatabaseHelper databaseHelper = DatabaseHelper.getHelper(context);
		try {
			// Persisting.
			for (Bean bean : list) {
				// Persisting.
				databaseHelper.createOrUpdate(bean);
			}
		} catch (IllegalAccessException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Error while trying to create or update Bean.", e);
		} catch (SQLException e) {
			Log.e(AppConfiguration.COMMON_LOGGING_TAG, "Database exception.", e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			// Releasing the database helper.
			DatabaseHelper.releaseHelper();
		}
	}
}