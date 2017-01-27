package com.example.rodrigo.weatherapp.model.api;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.controller.utils.Utils;
import com.example.rodrigo.weatherapp.model.WeatherResponse;
import com.example.rodrigo.weatherapp.view.activity.MainActivity;
import com.example.rodrigo.weatherapp.view.activity.WeatherActivity;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * RetrofitUtils.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 24, 2017
 */
public class RetrofitUtils {

    public static void getWeather(AppCompatActivity activity, String cityName, Dialog dialog) {
        // Retrofit.
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(AppConfiguration.BASE_URL)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        WeatherService service = retrofit.create(WeatherService.class);

        // Rx.
        Observable<WeatherResponse> observable = service.getWeather(cityName, AppConfiguration.FORMAT,
            AppConfiguration.NUMBER_OF_DAYS, AppConfiguration.KEY);
        observable
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                (response) -> callAction(activity, response, cityName),
                (error) -> {
                        String message = activity.getString(R.string.fetch_city_error, cityName);
                        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    },
                    () -> dialog.dismiss()
                );
    }

    private static void callAction(AppCompatActivity activity, WeatherResponse response,
        String cityName) {
        if (activity instanceof MainActivity) {
            String cityFromApi = response.getData().getRequest().get(0).getQuery();
            if (!Utils.isEmpty(cityFromApi)) {
                MainActivity mainActivity = (MainActivity) activity;
                mainActivity.citySearchedExists(cityName);
            }
        } else if (activity instanceof WeatherActivity) {
            WeatherActivity weatherActivity = (WeatherActivity)activity;
            weatherActivity.setAdapter(response);
        }
    }
}