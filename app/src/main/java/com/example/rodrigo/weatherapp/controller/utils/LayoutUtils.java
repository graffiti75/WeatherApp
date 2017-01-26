package com.example.rodrigo.weatherapp.controller.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.model.Weather;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * LayoutUtils.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 25, 2017
 */
public class LayoutUtils {

	//--------------------------------------------------
	// Methods
	//--------------------------------------------------	
	
	/**
	 * Gets the {@link Drawable} according to the current temperature. 
	 * 
	 * @param activity The current {@link Activity}.
	 * @param instance The {@link Weather} class instance.
	 * 
	 * @return
	 */
	public static Drawable getTemperatureColor(Activity activity, Weather instance) {
		Integer minTemperature = instance.getTempMinC();
		Integer maxTemperature = instance.getTempMaxC();
		Double quotient = (double) ((minTemperature + maxTemperature) / 2);
		Integer medium = quotient.intValue();
		
		Drawable drawable;
		Resources resources = activity.getResources();
		
		if (medium <= 0) {
			drawable = resources.getDrawable(R.drawable.background_gray);
		} else if (medium > 0 && medium <= 15) {
			drawable = resources.getDrawable(R.drawable.background_blue);
		} else if (medium > 15 && medium <= 30) {
			drawable = resources.getDrawable(R.drawable.background_yellow);
		} else {
			drawable = resources.getDrawable(R.drawable.background_red);
		}
		
		return drawable;
	}
	
	/**
	 * Sets the image from each {@link ImageView}.
	 * <br>If it exists, get from cache.<br>If isn't, download it.
	 *  
	 * @param context The context of the application.
	 * @param url The url of the image.
	 * @param imageView The {@link ImageView} which will receive the image.
	 */
	public static void setUniversalImage(Context context, String url, ImageView imageView) {
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		imageLoader.displayImage(url, imageView, cache);
	}
}