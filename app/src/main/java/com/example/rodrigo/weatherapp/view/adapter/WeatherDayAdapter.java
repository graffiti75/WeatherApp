package com.example.rodrigo.weatherapp.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.databinding.WeatherDayAdapterBinding;
import com.example.rodrigo.weatherapp.model.Weather;

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
		WeatherDayAdapterBinding binding;
		if (convertView == null) {
			binding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.weather_day_adapter, parent, false);
		} else {
			binding = DataBindingUtil.bind(convertView);
		}
		binding.setWeather(weather);
		return binding.getRoot();
	}
}