package com.example.rodrigo.weatherapp.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.databinding.AdapterWeatherDayBinding;
import com.example.rodrigo.weatherapp.model.Weather;

import java.util.List;

/**
 * WeatherDayAdapter.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 25, 2017
 */
public class WeatherDayAdapter extends RecyclerView.Adapter<WeatherDayAdapter.WeatherDayViewHolder> {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	private List<Weather> mItems;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------

	public WeatherDayAdapter(List<Weather> items) {
		mItems = items;
	}

	//--------------------------------------------------
	// Adapter Methods
	//--------------------------------------------------

	@Override
	public WeatherDayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		AdapterWeatherDayBinding binding = DataBindingUtil.inflate(
			LayoutInflater.from(parent.getContext()), R.layout.adapter_weather_day, parent, false);
		return new WeatherDayViewHolder(binding.getRoot());
	}

	@Override
	public void onBindViewHolder(WeatherDayViewHolder holder, int position) {
		Weather item = mItems.get(position);
		holder.binding.setWeather(item);
	}

	@Override
	public int getItemCount() {
		if (mItems != null && mItems.size() > 0) {
			return mItems.size();
		}
		return 0;
	}

	//--------------------------------------------------
	// View Holder
	//--------------------------------------------------

	public class WeatherDayViewHolder extends RecyclerView.ViewHolder {
		AdapterWeatherDayBinding binding;

		public WeatherDayViewHolder(View rootView) {
			super(rootView);
			binding = DataBindingUtil.bind(rootView);
		}
	}
}