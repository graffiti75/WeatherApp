package br.android.weather_app.utils;

import java.util.Calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings;
import br.android.weather_app.CustomApplication;

/**
 * A group of utility methods.
 * 
 * @author Rodrigo Cericatto
 * @since 14/10/2014
 */
public class Utils {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private static Float mDensity;

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------	
	
	/**
	 * Checks if the user is connected. If not, a message will show up to the user.
	 * 
	 * @param context
	 * @return
	 */
	/*
	public static boolean checkConnection(Context context) {
		// Checking for internet connection.
		if (!HttpManager.getInstance().isOnline()) {
			// Alert the user that a connection is needed.
			DialogHelper.showSimpleAlert(context, R.string.error_title_no_internet, R.string.error_msg_no_internet);
			return false;
		}
		return true;
	}
	*/
	
	/**
	 * Returns the class name without package, if any.
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getClassName(Class<?> clazz) {
		String className = getFullClassName(clazz);
		int firstChar = className.lastIndexOf('.') + 1;
		if (firstChar > 0) {
			className = className.substring(firstChar);
		}
		return className;
	}
	
	/**
	 * Returns the full class name.
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getFullClassName(Class<?> clazz) {
		return clazz.getName();
	}

	/**
	 * Returns the version code of the application.
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionCode(Context context) {
		if (context == null) return null;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(CustomApplication.ROOT_PACKAGE_NAME, 0);
			return String.valueOf(info.versionCode);
		} catch (NameNotFoundException e) {}
		return null;
	}
	
	/**
	 * Returns the version name of the application.
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		if (context == null) return null;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(CustomApplication.ROOT_PACKAGE_NAME, 0);
			return info.versionName;
		} catch (NameNotFoundException e) {}
		return null;
	}
	
	/**
	 * Returns the package name of the given class.
	 * 
	 * @param clazz
	 * @return The package name.
	 */
	public static String getPackageName(Class<?> clazz) {
		return clazz.getPackage().getName();
	}
	
	/**
	 * Returns if the Wi-Fi or GPS location service is enabled.
	 * 
	 * @return
	 */
	public static boolean isLocationServiceEnabled(Context context) {
		String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		return !StringUtils.isEmpty(provider);
	}

	/**
     * Sleep current thread.
     * 
     * @param milliseconds
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // Do nothing.
        }
    }

	/**
	 * Saves density of the screen.
	 * 
	 * @param density Screen density
	 */
	public static void setScreenDensity(Float density) {
		mDensity = density;
	}
	
	/**
	 * Converts density pixels to pixels.
	 * 
	 * @param dp Density pixels value to be converted to pixels.
	 * @return The conversion.
	 */
	public static Integer dpToPixel(int dp) {
		return (int)(dp * mDensity);
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
	
	/**
	 * Gets the current time in mili seconds.
	 * 
	 * @return The current time.
	 */
	public static Long getCurrentInMillis() {
		return Calendar.getInstance().getTimeInMillis();
	}
}