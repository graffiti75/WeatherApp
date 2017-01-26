package com.example.rodrigo.weatherapp.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rodrigo.weatherapp.R;
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
	// View Holder
	//--------------------------------------------------

	public class ViewHolder {
		private TextView cityTextView;
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
		ViewHolder holder = new ViewHolder();
		
		if (convertView == null) {
			// Inflates the layout of the adapter.
			LayoutInflater inflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.city_adapter, parent, false);

			// Initializes the ViewHolder.
			holder.cityTextView = (TextView) convertView.findViewById(R.id.id_city_adapter__city_name_text_view);
			
			// Sets the View tag.
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		setData(holder, city);

		return convertView;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	public void setData(ViewHolder holder, City city) {
		holder.cityTextView.setText(city.getCity());
	}
}