package com.example.rodrigo.weatherapp.controller.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.rodrigo.weatherapp.R;

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

	public static void initToolbar(AppCompatActivity activity, Boolean homeEnabled, int stringId) {
		Toolbar toolbar = (Toolbar)activity.findViewById(R.id.id_toolbar);
		if (toolbar != null) {
			activity.setSupportActionBar(toolbar);
			activity.getSupportActionBar().setDisplayHomeAsUpEnabled(homeEnabled);
			activity.getSupportActionBar().setTitle(stringId);
		}
	}

	public static void initToolbar(AppCompatActivity activity, Boolean homeEnabled, String string) {
		Toolbar toolbar = (Toolbar)activity.findViewById(R.id.id_toolbar);
		if (toolbar != null) {
			activity.setSupportActionBar(toolbar);
			activity.getSupportActionBar().setDisplayHomeAsUpEnabled(homeEnabled);
			activity.getSupportActionBar().setTitle(string);
		}
	}
}