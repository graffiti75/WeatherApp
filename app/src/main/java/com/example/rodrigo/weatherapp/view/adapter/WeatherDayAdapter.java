package com.example.rodrigo.weatherapp.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.controller.utils.LayoutUtils;
import com.example.rodrigo.weatherapp.model.Weather;

import java.util.Calendar;
import java.util.List;

/**
 * WeatherDayAdapter.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 25, 2017
 */
public class WeatherDayAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Activity mActivity;
	private List<Weather> mWeatherList;

	//--------------------------------------------------
	// View Holder
	//--------------------------------------------------
	
	public class ViewHolder {
		private LinearLayout backgroundLinearLayout;
		private TextView currentDayTextView;
		private TextView currentDayDescTextView;
		private ImageView currentWeatherImageView;
		private TextView minTemperatureTextView;
		private TextView maxTemperatureTextView;
		private TextView precipTextView;
		private TextView windDirTextView;
		private TextView windSpeedTextView;
	}
	
	//--------------------------------------------------
	// Adapter
	//--------------------------------------------------
	
	public WeatherDayAdapter(Activity activity, List<Weather> weatherList) {
		mActivity = activity;
		mWeatherList = weatherList;
	}
	
	public int getCount() {
		return mWeatherList.size();
	}
	
	public Weather getItem(int position) {
		return mWeatherList.get(position);
	}
	
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Weather weather = getItem(position);
		ViewHolder holder = new ViewHolder();
		
		if (convertView == null) {
			// Inflates the layout of the adapter.
			LayoutInflater inflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.weather_day_adapter, parent, false);
			
			// Initializes the ViewHolder.
			holder = setViewHolder(holder, convertView);
			
			// Sets the View tag.
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		getData(holder, weather);
		
		return convertView;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	public ViewHolder setViewHolder(ViewHolder holder, View convertView) {
		holder.backgroundLinearLayout = (LinearLayout)convertView.findViewById(R.id.id_weather_day_adapter__linear_layout);
		holder.currentDayTextView = (TextView)convertView.findViewById(R.id.id_weather_day_adapter__current_day_text_view);
		holder.currentDayDescTextView = (TextView)convertView.findViewById(R.id.id_weather_day_adapter__current_day_desc_text_view);
		
		holder.currentWeatherImageView = (ImageView)convertView.findViewById(R.id.id_weather_day_adapter__current_weather_image_view);
		holder.minTemperatureTextView = (TextView)convertView.findViewById(R.id.id_weather_day_adapter__min_temperature_text_view);
		holder.maxTemperatureTextView = (TextView)convertView.findViewById(R.id.id_weather_day_adapter__max_temperature_text_view);
		
		holder.precipTextView = (TextView)convertView.findViewById(R.id.id_weather_day_adapter__precip_text_view);
		holder.windDirTextView = (TextView)convertView.findViewById(R.id.id_weather_day_adapter__wind_dir_text_view);
		holder.windSpeedTextView = (TextView)convertView.findViewById(R.id.id_weather_day_adapter__wind_speed_text_view);
		
		return holder;
	}
	
	public void getData(ViewHolder holder, Weather instance) {
		// Background linear layout.
		Drawable color = LayoutUtils.getTemperatureColor(mActivity, instance);
		holder.backgroundLinearLayout.setBackground(color);
		
		// Current day.
		String parts[] = instance.getDate().split("-");
		String dayOfMonth = parts[2];
		String currentDate = getCurrentWeekday(Integer.valueOf(dayOfMonth));
		
		String date = instance.getDate().replace("-", "/");
		currentDate = currentDate + ", " + date;
		holder.currentDayTextView.setText(currentDate);

		// Current day description.
		String currentDayDescription = instance.getWeatherDesc().get(0).getValue();
		holder.currentDayDescTextView.setText(currentDayDescription);
		
		// Weather image.
		String weatherIconUrl = instance.getWeatherIconUrl().get(0).getValue();
		LayoutUtils.setUniversalImage(mActivity, weatherIconUrl, holder.currentWeatherImageView);

		// Temperatures.
		String minTemperature = instance.getTempMinC().toString() + "ºC";
		holder.minTemperatureTextView.setText(minTemperature);
		String maxTemperature = instance.getTempMaxC().toString() + "ºC";
		holder.maxTemperatureTextView.setText(maxTemperature);
		
		// Precipitation.
		String precipitation = instance.getPrecipMM().toString() + " mm";
		holder.precipTextView.setText(precipitation);
		
		// Wind Direction.
		String windDirection = instance.getWinddirection();
		holder.windDirTextView.setText(windDirection);

		// Wind Speed.		
		String windSpeed = instance.getWindspeedKmph().toString() + " km/h";
		holder.windSpeedTextView.setText(windSpeed);
	}
	
	public String getCurrentWeekday(Integer adapterDayOfMonth) {
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
	
	public String getWeekdayString(Integer dayOfWeek) {
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