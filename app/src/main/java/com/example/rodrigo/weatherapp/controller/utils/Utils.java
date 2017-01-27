package com.example.rodrigo.weatherapp.controller.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Utils.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 24, 2017
 */
public class Utils {

	public static Boolean isEmpty(String text) {
		Boolean result = true;
		Boolean isNull = (text == null);
		if (!isNull) {
			Boolean isZeroLength = (text.length() <= 0);
			Boolean isEmpty = (text.equals(""));
			result = isNull || isZeroLength || isEmpty;
		}
		return result;
	}

	public static Boolean checkConnection(Context context) {
		ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false; 
	}
}