package br.android.weather_app.activity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit.RestAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import br.android.weather_app.R;
import br.android.weather_app.api.WeatherService;
import br.android.weather_app.api.model.CurrentCondition;
import br.android.weather_app.api.model.Request;
import br.android.weather_app.api.model.WeatherResponse;
import br.android.weather_app.manager.ContentManager;
import br.android.weather_app.model.City;
import br.android.weather_app.tasks.Notifiable;
import br.android.weather_app.utils.ActivityUtils;

/**
 * MainActivity.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 19/10/2014
 */
public class LauncherActivity extends Activity implements Notifiable, LocationListener {

	//--------------------------------------------------
	// Constants
	//--------------------------------------------------
	
	public static final String USER_ALLOW_EXTRA = "user_allow_extra";
	public static Integer DELAY = 3000;
	
	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------

	// Rest.
	private WeatherService mService;
	
	// Handler.
	private Handler mMainActivityHandler = new Handler();
	private Handler mUserCityHandler = new Handler();
	private Dialog mDialog;
	
	// Coordenates.
	private Double mLatitude;
	private Double mLongitude;
	
	// Flow.
	private Boolean mActivityAlreadyAcessed = false;
	private Boolean mUserAllowGps = false;
	
	//--------------------------------------------------
	// Activity Life Cycle
	//--------------------------------------------------
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// Avoid this Activity to be shown every time.
		if (mActivityAlreadyAcessed) {
			finish();
		}
		
		mUserCityHandler.postDelayed(mUserCityHandlerChecker, DELAY);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		// Turns off the GPS.
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		locationManager.removeUpdates(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMainActivityHandler.removeCallbacks(mMainActivityHandlerChecker);
		mUserCityHandler.removeCallbacks(mUserCityHandlerChecker);
	}

	//--------------------------------------------------
	// Methods
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
	 * Checks if the GPS is on or not.
	 */
	public void checkGps() {
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			// GPS is on.
			mUserAllowGps = true;
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 100, this);
		} else {
			// Asks the user to turn on the GPS.
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("The GPS is off. Do you want to turn it now?").setCancelable(false);
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Intent to go to the Settings window.
						Intent callGpsSettings = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(callGpsSettings);
					}
				}
			);
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					mMainActivityHandler.postDelayed(mMainActivityHandlerChecker, DELAY);
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
	}
	
	/**
	 * Gets the city name from the {@link Geocoder}.
	 * 
	 * @return
	 */
	public String getCityFromGeocode() {
		// Gets the city.
		Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
		String city = "";
		try {
			List<Address> addresses = geoCoder.getFromLocation(mLatitude, mLongitude, 1);
			if (addresses.size() > 0)  {
				city = addresses.get(0).getSubAdminArea();
			}
			Toast.makeText(this, "City is " + city + ".", Toast.LENGTH_LONG).show();
		} catch (IOException e1) {                
			e1.printStackTrace();
		}
		
		// Calls the API.
		setRetrofitService();
		ContentManager.getInstance().getWeatherResponse(this, mService, city);
		
		return city;
	}
	
	/**
	 * Sets the {@link CurrentCondition} list from the REST API.
	 * 
	 * @param response
	 */
	public void setCurrentConditionInCache(WeatherResponse response) {
		ContentManager.getInstance().setCurrentCondition(response);
	}
	
	/**
	 * Sets the {@link Request} list from the REST API.
	 * 
	 * @param response
	 */
	public void setRequestListInCache(WeatherResponse response) {
		ContentManager.getInstance().setRequestList(response);
		mMainActivityHandler.postDelayed(mMainActivityHandlerChecker, DELAY);
	}
	
	/**
	 * Opens the {@link MainActivity}.
	 */
	public void openMainActivity() {
		// Closes the dialog and sets the flag of this activity.
		mActivityAlreadyAcessed = true;
		if (mDialog != null) {
			mDialog.cancel();
		}
		
		// Extras.
		Bundle extras = new Bundle();
		extras.putBoolean(USER_ALLOW_EXTRA, mUserAllowGps);
		ActivityUtils.openActivity(this, MainActivity.class, extras);
	}
	
	//--------------------------------------------------
	// Notifiable
	//--------------------------------------------------

	@Override
	public void taskFinished(int type, Object result) {
		if (type == ContentManager.FETCH_TASK.WEATHER) {
			WeatherResponse response = (WeatherResponse) result;
			setCurrentConditionInCache(response);
			setRequestListInCache(response);
		}
	}
	
	//--------------------------------------------------
	// Handlers
	//--------------------------------------------------
	
	/**
	 * The {@link Handler} checker before this {@link Activity} go to the {@link MainActivity}. 
	 */
	private Runnable mMainActivityHandlerChecker = new Runnable() {
	    public void run() {
	    	mMainActivityHandler.removeCallbacks(mMainActivityHandlerChecker);
	    	openMainActivity();
	    }
	};
	
	/**
	 * The {@link Handler} checker before the user {@link City} be discovered.
	 */
	private Runnable mUserCityHandlerChecker = new Runnable() {
	    public void run() {
	    	mUserCityHandler.removeCallbacks(mUserCityHandlerChecker);
	    	checkGps();
	    }
	};
	
	//--------------------------------------------------
	// Location Listener
	//--------------------------------------------------
	
	@Override
	public void onLocationChanged(Location location) {
		mLatitude = location.getLatitude();
		mLongitude = location.getLongitude();
		getCityFromGeocode();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}

	@Override
	public void onProviderEnabled(String provider) {}

	@Override
	public void onProviderDisabled(String provider) {}
}