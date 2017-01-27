package com.example.rodrigo.weatherapp.presenter.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Calendar;

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

	public static String getCurrentWeekday(Integer adapterDayOfMonth) {
		// Gets the current date.
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		// Compares the current date with the date from the adapter.
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		if (adapterDayOfMonth != dayOfMonth) {
			int difference = adapterDayOfMonth - dayOfMonth;
			dayOfWeek += difference;
			if (dayOfWeek != 7) {
				dayOfWeek = dayOfWeek % 7;
			}
		}

		// Gets the proper day of week string.
		String dayInString = getWeekdayString(dayOfWeek);
		return dayInString;
	}

	private static String getWeekdayString(Integer dayOfWeek) {
		String dayInString = "";
		switch (dayOfWeek) {
			case 2:
				dayInString = "Monday";
				break;
			case 3:
				dayInString = "Tuesday";
				break;
			case 4:
				dayInString = "Wednesday";
				break;
			case 5:
				dayInString = "Thursday";
				break;
			case 6:
				dayInString = "Friday";
				break;
			case 7:
				dayInString = "Saturday";
				break;
			case 1:
				dayInString = "Sunday";
				break;
		}
		return dayInString;
	}
}