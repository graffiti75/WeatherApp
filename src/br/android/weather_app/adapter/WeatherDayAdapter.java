package br.android.weather_app.adapter;

import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.android.weather_app.R;
import br.android.weather_app.api.model.Weather;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * WeatherDayAdapter.java class.
 * 
 * @author Rodrigo Cericatto
 * @since 17/10/2014
 */
public class WeatherDayAdapter extends BaseAdapter {

	//--------------------------------------------------
	// Attributes
	//--------------------------------------------------
	
	private Context mContext;
	private List<Weather> mWeatherList;

	//--------------------------------------------------
	// View Holder
	//--------------------------------------------------
	
	public class ViewHolder {
		private LinearLayout mBackgroundLinearLayout;
		private TextView mCurrentDayTextView;
		private TextView mCurrentDayDescTextView;
		private ImageView mCurrentWeatherImageView;
		private TextView mMinTemperatureTextView;
		private TextView mMaxTemperatureTextView;
		private TextView mPrecipTextView;
		private TextView mWindDirTextView;
		private TextView mWindSpeedTextView;
		
		ViewHolder(View view) {
			setCurrentDayTextView((TextView)view.findViewById(R.id.id_weather_day_adapter__current_day_text_view));
			setCurrentWeatherImageView((ImageView)view.findViewById(R.id.id_weather_day_adapter__current_weather_image_view));
			setMinTemperatureTextView((TextView)view.findViewById(R.id.id_weather_day_adapter__min_temperature_text_view));
			setMaxTemperatureTextView((TextView)view.findViewById(R.id.id_weather_day_adapter__max_temperature_text_view));
			setPrecipTextView((TextView)view.findViewById(R.id.id_weather_day_adapter__precip_text_view));
			setWindDirTextView((TextView)view.findViewById(R.id.id_weather_day_adapter__wind_dir_text_view));
			setWindSpeedTextView((TextView)view.findViewById(R.id.id_weather_day_adapter__wind_speed_text_view));
			setCurrentDayDescTextView((TextView)view.findViewById(R.id.id_weather_day_adapter__current_day_desc_text_view));
			setBackgroundLinearLayout((LinearLayout)view.findViewById(R.id.id_weather_day_adapter__linear_layout));
		}
		
		public LinearLayout getBackgroundLinearLayout() {
			return mBackgroundLinearLayout;
		}
		public void setBackgroundLinearLayout(LinearLayout backgroundLinearLayout) {
			mBackgroundLinearLayout = backgroundLinearLayout;
		}
		
		public TextView getCurrentDayTextView() {
			return mCurrentDayTextView;
		}
		public void setCurrentDayTextView(TextView currentDayTextView) {
			mCurrentDayTextView = currentDayTextView;
		}
		
		public TextView getCurrentDayDescTextView() {
			return mCurrentDayDescTextView;
		}
		public void setCurrentDayDescTextView(TextView currentDayDescTextView) {
			mCurrentDayDescTextView = currentDayDescTextView;
		}

		public ImageView getCurrentWeatherImageView() {
			return mCurrentWeatherImageView;
		}
		public void setCurrentWeatherImageView(ImageView currentWeatherImageView) {
			mCurrentWeatherImageView = currentWeatherImageView;
		}

		public TextView getMinTemperatureTextView() {
			return mMinTemperatureTextView;
		}
		public void setMinTemperatureTextView(TextView minTemperatureTextView) {
			mMinTemperatureTextView = minTemperatureTextView;
		}

		public TextView getMaxTemperatureTextView() {
			return mMaxTemperatureTextView;
		}
		public void setMaxTemperatureTextView(TextView maxTemperatureTextView) {
			mMaxTemperatureTextView = maxTemperatureTextView;
		}

		public TextView getPrecipTextView() {
			return mPrecipTextView;
		}
		public void setPrecipTextView(TextView precipTextView) {
			this.mPrecipTextView = precipTextView;
		}

		public TextView getWindDirTextView() {
			return mWindDirTextView;
		}
		public void setWindDirTextView(TextView windDirTextView) {
			this.mWindDirTextView = windDirTextView;
		}

		public TextView getWindSpeedTextView() {
			return mWindSpeedTextView;
		}
		public void setWindSpeedTextView(TextView windSpeedTextView) {
			this.mWindSpeedTextView = windSpeedTextView;
		}
	}
	
	//--------------------------------------------------
	// Adapter
	//--------------------------------------------------
	
	public WeatherDayAdapter(Context context, List<Weather> weatherList) {
		this.mContext = context;
		this.mWeatherList = weatherList;
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
		// Creates a new convert view if needed.
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.weather_day_adapter, parent, false);
		}
		
		// Sets the view holder.
		ViewHolder holder = (ViewHolder)convertView.getTag();
		if (holder == null) {
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		
		getData(position, holder);
		
		return convertView;
	}
	
	//--------------------------------------------------
	// Methods
	//--------------------------------------------------
	
	/**
	 * Get the data of the {@link Weather}.
	 * 
	 * @param position
	 * @param holder
	 */
	@SuppressLint("NewApi")
	public void getData(Integer position, ViewHolder holder) {
		// Fills the list item view with the appropriate data.
		Weather instance = (Weather)getItem(position);
		
		// Background linear layout.
		Drawable color = getTemperatureColor(instance);
		holder.getBackgroundLinearLayout().setBackground(color);
		
		// Current day.
		String parts[] = instance.getDate().split("-");
		String dayOfMonth = parts[2];
		String currentDate = getCurrentWeekday(Integer.valueOf(dayOfMonth));
		String date = instance.getDate().replace("-", "/");
		currentDate = currentDate + ", " + date;
		holder.getCurrentDayTextView().setText(currentDate);

		// Current day description.
		String currentDayDescription = instance.getWeatherDesc().get(0).getValue();
		holder.getCurrentDayDescTextView().setText(currentDayDescription);
		
		// Weather image.
		setUniversalImage(instance.getWeatherIconUrl().get(0).getValue(), holder.getCurrentWeatherImageView());
		
		// Temperatures.
		String minTemperature = instance.getTempMinC().toString() + "ºC";
		holder.getMinTemperatureTextView().setText(minTemperature);
		String maxTemperature = instance.getTempMaxC().toString() + "ºC";
		holder.getMaxTemperatureTextView().setText(maxTemperature);
		
		// Precipitation.
		String precipitation = instance.getPrecipMM().toString() + " mm";
		holder.getPrecipTextView().setText(precipitation);
		
		// Wind Direction and Wind Speed.
		String windDirection = instance.getWinddirection();
		holder.getWindDirTextView().setText(windDirection);
		
		String windSpeed = instance.getWindspeedKmph().toString() + " km/h";
		holder.getWindSpeedTextView().setText(windSpeed);
	}
	
	/**
	 * Sets the image from each {@link ImageView}.<br>If it exists, get from cache.<br>If isn't, download it.
	 *  
	 * @param url The url of the image.
	 * @param imageView The {@link ImageView} which will receive the image.
	 */
	public void setUniversalImage(String url, ImageView imageView) {
		DisplayImageOptions cache = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		imageLoader.displayImage(url, imageView, cache);
	}
	
	/**
	 * Gets the current date in weekdays.
	 * 
	 * @param adapterDayOfMonth
	 * 
	 * @return
	 */
	public String getCurrentWeekday(Integer adapterDayOfMonth) {
		// Gets the current date.
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		// Compares the current date with the date from the adapter.
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		if (adapterDayOfMonth != dayOfMonth) {
			int difference = adapterDayOfMonth - dayOfMonth;
			dayOfWeek += difference;
			dayOfWeek = dayOfWeek % 7;	
		}
		
		// Gets the proper day of week string.
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
	
	/**
	 * Gets the {@link Drawable} according to the current temperature. 
	 * 
	 * @param instance
	 * @return
	 */
	public Drawable getTemperatureColor(Weather instance) {
		Integer minTemperature = instance.getTempMinC();
		Integer maxTemperature = instance.getTempMaxC();
		Double quotient = (double) ((minTemperature + maxTemperature) / 2);
		Integer medium = quotient.intValue();
		Drawable drawable = null;
		
		if (medium <= 0) {
			drawable = mContext.getResources().getDrawable(R.drawable.background_gray);
		} else if (medium > 0 && medium <= 15) {
			drawable = mContext.getResources().getDrawable(R.drawable.background_blue);
		} else if (medium > 15 && medium <= 30) {
			drawable = mContext.getResources().getDrawable(R.drawable.background_yellow);
		} else {
			drawable = mContext.getResources().getDrawable(R.drawable.background_red);
		}
		
		return drawable;
	}
}