package br.android.weather_app.activity;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import br.android.weather_app.R;
import br.android.weather_app.api.WeatherService;
import br.android.weather_app.api.model.Request;
import br.android.weather_app.api.model.WeatherResponse;
import br.android.weather_app.data.ListItems;
import br.android.weather_app.helper.DialogHelper;
import br.android.weather_app.helper.OnClickListenerCustomDialog;
import br.android.weather_app.manager.ContentManager;
import br.android.weather_app.model.City;
import br.android.weather_app.tasks.Notifiable;
import br.android.weather_app.utils.ActivityUtils;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * MainActivity.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 19/10/2014
 */
public class MainActivity extends SherlockActivity implements Notifiable, OnClickListener {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------

	public static final String CITY_NAME_EXTRA = "city_name_extra";
	public static final Integer LIMIT = 75;

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	// Layout.
	private Button mAddButton;
	private Dialog mDialog = null;

	// Rest.
	private String mCity;
	private WeatherService mService;
	private Boolean mCheckingCity = false;

	// Adapter.
	private List<City> mCityList = new ArrayList<City>();
	private CityAdapter mAdapter;
	private ListView mListView;
	private CityTouchListener mOnTouchListener;

	// Swipe.
	private Integer mActionDownX = 0;
	private Integer mActionUpX = 0;
	private Integer mDifference = 0;

	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setAdapter();
		setLayout();
	}

	//--------------------------------------------------
	// Layout Methods
	//--------------------------------------------------

	/**
	 * Adds values of the list, customizes adapter, and set's list view adapter
	 * and it's listener.
	 */
	public void setAdapter() {
		mOnTouchListener = new CityTouchListener();
		mCityList = ListItems.getCityList();
		mListView = (ListView) findViewById(R.id.id_activity_main__listview);
		mAdapter = new CityAdapter(mCityList);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * Sets the layout of this {@link Activity}.
	 */
	public void setLayout() {
		mAddButton = (Button) findViewById(R.id.id_activity_main__add_city_button);
		mAddButton.setOnClickListener(this);
	}

	//--------------------------------------------------
	// API Methods
	//--------------------------------------------------
	
	/**
	 * Sets the {@link WeatherService} from the Retrofit.
	 */
	public void setRetrofitService() {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
			"http://api.worldweatheronline.com").build();

		mService = restAdapter.create(WeatherService.class);
	}
	
	/**
	 * Gets the {@link WeatherResponse} of a specific city.
	 */
	public void getWeatherInfo() {
		setRetrofitService();
		ContentManager.getInstance().getWeatherResponse(this, mService, mCity);
	}

	/**
	 * Shows the {@link WeatherResponse} info for the user.
	 * 
	 * @param response
	 */
	public void setWeatherListInCache(WeatherResponse response) {
		ContentManager.getInstance().setWeatherList(response);
		openWeatherActivity();
	}

	/**
	 * Verifies the {@link Request} list from the API.
	 * 
	 * @param response
	 */
	public void setRequestListInCache(WeatherResponse response) {
		Boolean loadedSuccessfully = ContentManager.getInstance().setRequestList(response);
		if (loadedSuccessfully) {
			// Adds the city into the adapter.
			String city = ContentManager.getInstance().getCityFromRequest();
			mCityList.add(new City(city, true));
			mAdapter.notifyDataSetChanged();
			
			// Closes the current dialog.
			if (mDialog != null) {
				mDialog.cancel();
			}
		} else {
			// Shows a error message for the user.
			DialogHelper.showSimpleAlert(this, R.string.activity_main__empty_city_dialog_title,
				R.string.activity_main__empty_city_dialog_message);
			if (mDialog != null) {
				mDialog.cancel();
			}
		}
		mCheckingCity = false;
	}

	//--------------------------------------------------
	// Other Methods
	//--------------------------------------------------
	
	/**
	 * Opens the {@link WeatherActivity}.
	 */
	public void openWeatherActivity() {
		// Extras.
		Bundle extras = new Bundle();
		String cityFromResquest = ContentManager.getInstance().getCityFromRequest();
		extras.putString(CITY_NAME_EXTRA, cityFromResquest);

		// Transition.
		if (mDialog != null) {
			mDialog.cancel();
		}
		ActivityUtils.openActivity(this, WeatherActivity.class, extras);
		overridePendingTransition(R.anim.slide_up_from_outside, R.anim.slide_up_to_outside);
	}

	/**
	 * Calculates the difference between the clicked position in X axis.
	 * 
	 * @param holder
	 * @param position
	 */
	public void calculateDifference(final ViewHolder holder, final int position) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (mDifference > LIMIT) {
					holder.removeButton.setVisibility(View.VISIBLE);
					mCityList.get(position).setVisible(true);
					mAdapter.changeData(mCityList);
				} else if (mDifference < -LIMIT) {
					holder.removeButton.setVisibility(View.GONE);
					mCityList.get(position).setVisible(false);
					mAdapter.changeData(mCityList);
				} else {
					mCity = mCityList.get(position).getCity();
					String message = getString(R.string.activity_main__loading_data) +
						" " + mCity + "..."; 
					mDialog = DialogHelper.showProgressDialog(MainActivity.this, message);
					getWeatherInfo();
				}
			}
		});
	}
	
	//--------------------------------------------------
	// Notifiable
	//--------------------------------------------------

	@Override
	public void taskFinished(int type, Object result) {
		if (type == ContentManager.FETCH_TASK.WEATHER) {
			WeatherResponse response = (WeatherResponse) result;
			if (mCheckingCity) {
				setRequestListInCache(response);
			} else {
				setWeatherListInCache(response);
			}
		}
	}

	//--------------------------------------------------
	// Listeners
	//--------------------------------------------------

	@Override
	public void onClick(View view) {
		// Gets the city from the dialog.
		DialogHelper.showCustomDialog(this, R.layout.custom_dialog,
			R.string.activity_main__city_dialog_title,
			new OnClickListenerCustomDialog() {
				@Override
				public void onClickCallback(Context context, String city) {
					// Shows a dialog for the user.
					mCity = city;
					String message = getString(R.string.activity_main__loading_data) +
						" " + mCity + "..."; 
					mDialog = DialogHelper.showProgressDialog(MainActivity.this, message);
					
					// Calls the API to check if the city exists.
					mCheckingCity = true;
					setRetrofitService();
					ContentManager.getInstance().getWeatherResponse(MainActivity.this, mService, city);
				}
			}
		);
	}
	
	/**
	 * CityTouchListener.java class.
	 * This class is used to link the Swipe action of the Adapter with
	 * the UiThread, that in this case is the MainActivity. 
	 */
	public class CityTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			ViewHolder holder = (ViewHolder) view.getTag(R.layout.city_adapter);
			int action = event.getAction();
			int position = (Integer) view.getTag();

			switch (action) {
			case MotionEvent.ACTION_DOWN:
				mActionDownX = (int) event.getX();
				break;
			case MotionEvent.ACTION_MOVE:
				mActionUpX = (int) event.getX();
				mDifference = mActionDownX - mActionUpX;
				break;
			case MotionEvent.ACTION_UP:
				calculateDifference(holder, position);
				mActionDownX = 0;
				mActionUpX = 0;
				mDifference = 0;
				break;
			}
			return true;
		}
	}

	//--------------------------------------------------
	// View Holder
	//--------------------------------------------------

	public class ViewHolder {
		private TextView cityTextView;
		private Button removeButton;
	}

	//--------------------------------------------------
	// City Adapter
	//--------------------------------------------------

	public class CityAdapter extends BaseAdapter implements OnClickListener {

		//------------ Attributes ------------
		
		private List<City> mAdapterCityList;

		//------------ Constructor ------------
		
		public CityAdapter(List<City> data) {
			mAdapterCityList = data;
		}

		//------------ Adapter ------------
		
		@Override
		public int getCount() {
			return mAdapterCityList.size();
		}

		@Override
		public Object getItem(int position) {
			return mAdapterCityList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.city_adapter, null);
				holder.cityTextView = (TextView) convertView.findViewById(R.id.id_city_adapter__city_name_text_view);
				holder.removeButton = (Button) convertView.findViewById(R.id.id_city_adapter__remove_button);
				holder.removeButton.setOnClickListener(this);
				convertView.setTag(R.layout.city_adapter, holder);
			} else {
				holder = (ViewHolder) convertView.getTag(R.layout.city_adapter);
			}
			setAdapter(holder, convertView, position);

			return convertView;
		}

		//------------ Methods ------------

		/**
		 * Sets the adapter.
		 * 
		 * @param holder
		 * @param convertView
		 * @param position
		 */
		public void setAdapter(ViewHolder holder, View convertView, Integer position) {
			convertView.setTag(position);
			convertView.setOnTouchListener(mOnTouchListener);
			holder.removeButton.setTag(position);
			holder.cityTextView.setText(mCityList.get(position).getCity());
		}

		/**
		 * Changes the data of the adapter.
		 * 
		 * @param data
		 */
		public void changeData(List<City> data) {
			mAdapterCityList = data;
			notifyDataSetChanged();
		}

		//------------ Listeners ------------
		
		@Override
		public void onClick(View v) {
			final int pos = (Integer) v.getTag();
			mCityList.get(pos).setVisible(false);
			mCityList.remove(pos);
			v.setVisibility(View.GONE);
			changeData(mCityList);
		}
	}
}