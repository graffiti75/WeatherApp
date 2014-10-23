package br.android.weather_app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * ActivityUtils class.
 * 
 * @author Rodrigo Cericatto
 * @since 14/10/2014
 */
public class ActivityUtils {
	
	/**
	 * Opens the specified activity.
	 * 
	 * @param context The context that will start the activity.
	 * @param activityClass The activity class to be started.
	 */
	public static void openActivity(Context context, final Class<? extends Activity> activityClass) {
		openActivity(context, activityClass, null);
	}
	
	/**
	 * Opens the specified activity clearing the activity stack.
	 * 
	 * @param context The context that will start the activity.
	 * @param activityClass The activity class to be started.
	 */
	public static void openActivityClearingStack(Context context, final Class<? extends Activity> activityClass) {
		openActivityClearingStack(context, activityClass, null);
	}
	
	/**
	 * Opens the specified activity.
	 * 
	 * @param context The context that will start the activity.
	 * @param activityClass The activity class to be started.
	 */
	public static void openActivityForResult(Context context, final Class<? extends Activity> activityClass) {
		Intent intent = new Intent(context, activityClass);
		((Activity)context).startActivityForResult(intent, 1);
	}
	
	/**
	 * Opens the specified activity.
	 * 
	 * @param context The context that will start the activity.
	 * @param activityClass The activity class to be started.
	 * @param extras A bundle containing any extras to be sent to the activity.
	 */
	public static void openActivity(Context context, final Class<? extends Activity> activityClass, final Bundle extras) {
		Intent intent = new Intent(context, activityClass);
		if (extras != null) intent.putExtras(extras);
		context.startActivity(intent);
	}
	
	/**
	 * Opens the specified activity.
	 * 
	 * @param context The context that will start the activity.
	 * @param activityClass The activity class to be started.
	 * @param extras A bundle containing any extras to be sent to the activity.
	 */
	public static void openActivityClearingStack(Context context, final Class<? extends Activity> activityClass, final Bundle extras) {
		Intent intent = new Intent(context, activityClass);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		if (extras != null) intent.putExtras(extras);
		context.startActivity(intent);
	}
	
    /**
     * Return true if screen is in landscape mode.
     * 
     * @param context
     * @return
     */
    public static boolean isLandscapeOrientation(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * Return true if screen is in portrait mode.
     * 
     * @param context
     * @return
     */
    public static boolean isPortraitOrientation(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * Return the screen size as defined in the screen layout configuration.
     * 
     * @param context
     * @return
     */
    public static int getScreenSize(Context context) {
        return context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    }

    /**
     * Return true if the screen is large size or bigger.
     * 
     * @param context
     * @return
     */
    public static boolean isLargeScreenOrLarger(Context context) {
        return getScreenSize(context) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * Return true is the screen is extra large size or bigger.
     * 
     * @param context
     * @return
     */
    public static boolean isXLargeScreenOrLarger(Context context) {
        return getScreenSize(context) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Force the screen to a orientation depending on size.
     * 
     * @param activity
     */
    public static void setActivityOrientation(Activity activity) {
        // Disable landscape for screens smaller than 'large'
        if(!isLargeScreenOrLarger(activity)) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
        // Disable portrait for screens larger than 'large'
        else if(ActivityUtils.isXLargeScreenOrLarger(activity)) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
    }
}