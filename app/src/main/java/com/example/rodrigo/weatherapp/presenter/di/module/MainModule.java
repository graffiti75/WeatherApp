package com.example.rodrigo.weatherapp.presenter.di.module;

import com.example.rodrigo.weatherapp.presenter.di.scope.PerActivity;
import com.example.rodrigo.weatherapp.model.api.WeatherService;
import com.example.rodrigo.weatherapp.view.activity.MainActivity;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * MainModule.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
@Module
public class MainModule {
    private MainActivity mActivity;

    public MainModule(MainActivity activity) {
        mActivity = activity;
    }

    @PerActivity
    @Provides
    WeatherService provideApiService(Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }

    @PerActivity
    @Provides
    MainActivity provideActivity() {
        return mActivity;
    }
}


