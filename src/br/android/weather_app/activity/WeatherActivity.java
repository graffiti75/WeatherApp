package br.android.weather_app.activity;

import java.util.List;

import android.os.Bundle;
import android.widget.ListView;
import br.android.weather_app.R;
import br.android.weather_app.adapter.WeatherDayAdapter;
import br.android.weather_app.api.model.Weather;
import br.android.weather_app.manager.ContentManager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

/**
 * WeatherActivity.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 14/10/2014
 */
public class WeatherActivity extends SherlockActivity {
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	// City name.
	private String mCityName;
	
	// Adapter.
	private ListView mListView;
	private WeatherDayAdapter mAdapter;
	private List<Weather> mWeatherList;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		
		getExtras();
		setActionBar();
		setAdapter();
	}
	
	//--------------------------------------------------
	// Menu
	//--------------------------------------------------
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.menu_application, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Gets the extras.
	 */
	public void getExtras() {
		Bundle extras = getIntent().getExtras(); 
		if (extras != null) {
			mCityName = extras.getString(MainActivity.CITY_NAME_EXTRA);
		}
	}
	
	/**
	 * Sets the {#link ActionBar}.
	 */
	public void setActionBar() {
		ActionBar action = getSupportActionBar();
		action.setTitle(mCityName);
		action.setDisplayHomeAsUpEnabled(true);
	}
	
	/**
	 * Adds values of the list, customizes adapter, and set's list view adapter and it's listener.
	 */
	public void setAdapter() {
		// Gets the Weather list.
		mWeatherList = ContentManager.getInstance().getWeatherList();
		
		// Sets the adapter.
	    mAdapter = new WeatherDayAdapter(this, mWeatherList);
		mListView = (ListView)findViewById(R.id.id_activity_weather__listview);
		mListView.setAdapter(mAdapter);
	}
}