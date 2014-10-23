package br.android.weather_app.utils;

import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * A group of utility methods.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class Utils {

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------	
	
	/**
	 * Checks if the user is connected. If not, a message will show up to the user.
	 * 
	 * @param context
	 * @return
	 */
	public static Boolean checkConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false; 
	}
	
	/**
	 * Gets the current time in mili seconds.
	 * 
	 * @return The current time.
	 */
	public static Long getCurrentInMillis() {
		return Calendar.getInstance().getTimeInMillis();
	}

    /**
     * Sets the preference of the application.
     * 
     * @param context The current context.
     * @param key The key of the preference.
     * @param status If the data were loaded.
     */
	public static void setPreference(Context context, String key, Boolean status) {
		// Gets the preference.
		SharedPreferences pref = context.getSharedPreferences("my_pref", Context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = pref.edit();
	    
		// Saves the preference.
	    editor.putBoolean(key, status);
	    editor.commit();
	}
	
    /**
     * Gets the preference of the application.
     * 
     * @param context The current context.
     * @páram key The key to be caught.
     * 
     * @return The preference value.
     */
	public static Boolean getPreference(Context context, String key) {
		SharedPreferences pref = context.getSharedPreferences("my_pref", Context.MODE_PRIVATE);
	    Boolean status = pref.getBoolean(key, false);
	    return status;
	}
}