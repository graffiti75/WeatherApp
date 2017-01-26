package com.example.rodrigo.weatherapp.view.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.databinding.CityAdapterBinding;
import com.example.rodrigo.weatherapp.model.City;

import java.util.List;

/**
 * CityAdapter.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 25, 2017
 */
public class CityAdapter extends BaseAdapter {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Activity mActivity;
	private List<City> mAdapterCityList;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------
	
	public CityAdapter(Activity activity, List<City> data) {
		mActivity = activity;
		mAdapterCityList = data;
	}

	//--------------------------------------------------
	// Adapter Methods
	//--------------------------------------------------
	
	@Override
	public int getCount() {
		return mAdapterCityList.size();
	}

	@Override
	public City getItem(int position) {
		return mAdapterCityList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		City city = getItem(position);
		CityAdapterBinding binding;
		if (convertView == null) {
			binding = DataBindingUtil.inflate(LayoutInflater.from(mActivity), R.layout.city_adapter, parent, false);
		} else {
			binding = DataBindingUtil.bind(convertView);
		}
		binding.setCity(city);
		return binding.getRoot();
	}
}