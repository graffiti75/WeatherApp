package com.example.rodrigo.weatherapp;

import android.app.Application;

import com.example.rodrigo.weatherapp.presenter.di.components.ApplicationComponent;
import com.example.rodrigo.weatherapp.presenter.di.components.DaggerApplicationComponent;
import com.example.rodrigo.weatherapp.presenter.di.module.ApplicationModule;

/**
 * WeatherApplication.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 26, 2017
 */
public class WeatherApplication extends Application {
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplicationComponent();
    }

    private void initializeApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(new ApplicationModule(this, AppConfiguration.BASE_URL))
            .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}