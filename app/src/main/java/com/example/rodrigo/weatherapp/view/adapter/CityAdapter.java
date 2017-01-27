package com.example.rodrigo.weatherapp.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.databinding.AdapterCityBinding;
import com.example.rodrigo.weatherapp.model.City;
import com.example.rodrigo.weatherapp.view.activity.MainActivity;

import java.util.List;

/**
 * CityAdapter.java.
 * 
 * @author Rodrigo Cericatto
 * @since Jan 25, 2017
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityAdapterViewHolder> {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	private MainActivity mActivity;
	private List<City> mItems;

	//--------------------------------------------------
	// Constructor
	//--------------------------------------------------

	public CityAdapter(MainActivity context, List<City> items) {
		mActivity = context;
		mItems = items;
	}

	//--------------------------------------------------
	// Adapter Methods
	//--------------------------------------------------

	@Override
	public CityAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		AdapterCityBinding binding = DataBindingUtil.inflate(
			LayoutInflater.from(parent.getContext()), R.layout.adapter_city, parent, false);
		return new CityAdapterViewHolder(binding.getRoot());
	}

	@Override
	public void onBindViewHolder(CityAdapterViewHolder holder, int position) {
		City item = mItems.get(position);
		holder.binding.setCity(item);
		holder.binding.idCityAdapterCityNameTextView.setOnClickListener(
			view -> mActivity.onItemClick(position));
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

	public class CityAdapterViewHolder extends RecyclerView.ViewHolder {
		AdapterCityBinding binding;

		public CityAdapterViewHolder(View rootView) {
			super(rootView);
			binding = DataBindingUtil.bind(rootView);
		}
	}
}