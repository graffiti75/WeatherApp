package com.example.rodrigo.weatherapp.presenter.di.module;

import com.example.rodrigo.weatherapp.presenter.di.scope.PerActivity;
import com.example.rodrigo.weatherapp.model.api.WeatherService;
import com.example.rodrigo.weatherapp.view.activity.WeatherActivity;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * WeatherModule.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
@Module
public class WeatherModule {
    private WeatherActivity mActivity;

    public WeatherModule(WeatherActivity activity) {
        mActivity = activity;
    }

    @PerActivity
    @Provides
    WeatherService provideApiService(Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }

    @PerActivity
    @Provides
    WeatherActivity provideActivity() {
        return mActivity;
    }
}