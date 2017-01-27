package com.example.rodrigo.weatherapp.presenter;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.presenter.utils.ReactiveUtils;
import com.example.rodrigo.weatherapp.presenter.utils.Utils;
import com.example.rodrigo.weatherapp.presenter.utils.dialog.DialogUtils;
import com.example.rodrigo.weatherapp.databinding.ActivityWeatherBinding;
import com.example.rodrigo.weatherapp.model.WeatherResponse;
import com.example.rodrigo.weatherapp.view.activity.WeatherActivity;
import com.example.rodrigo.weatherapp.view.adapter.WeatherDayAdapter;

import javax.inject.Inject;

/**
 * WeatherPresenter.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
public class WeatherPresenter {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private WeatherActivity mActivity;

    //--------------------------------------------------
    // Constructor
    //--------------------------------------------------

    @Inject
    public WeatherPresenter(WeatherActivity activity) {
        mActivity = activity;
    }

    //--------------------------------------------------
    // Public Methods
    //--------------------------------------------------

    public void getExtras() {
        Bundle extras = mActivity.getIntent().getExtras();
        if (extras != null) {
            mActivity.setCityName(extras.getString(AppConfiguration.CITY_NAME_EXTRA));
            mActivity.showBackArrow(mActivity, true, mActivity.getCityName());
        }
    }

    public void refreshList() {
        // Check if there is Network connection.
        if (Utils.checkConnection(mActivity)) {
            // Shows a loading dialog for the user.
            String message = mActivity.getString(R.string.activity_weather__loading_data, mActivity.getCityName());
            ProgressDialog dialog = DialogUtils.showProgressDialog(mActivity, message);

            // Calls the API.
            ReactiveUtils.getWeather(mActivity, mActivity.getCityName(), dialog);
        } else {
            DialogUtils.showNoConnectionDialog(mActivity);
        }
    }

    public void setAdapter(WeatherResponse response, ActivityWeatherBinding binding) {
        mActivity.setWeatherList(response.getData().getWeather());
        WeatherDayAdapter adapter = new WeatherDayAdapter(mActivity.getWeatherList());

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.idActivityWeatherRecyclerView.setLayoutManager(layoutManager);
        binding.idActivityWeatherRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.idActivityWeatherRecyclerView.setAdapter(adapter);
    }
}