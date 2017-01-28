package com.example.rodrigo.weatherapp.presenter.di.module;

import com.example.rodrigo.weatherapp.model.api.WeatherService;
import com.example.rodrigo.weatherapp.presenter.di.scope.PerActivity;
import com.example.rodrigo.weatherapp.view.fragment.LoadingDialogApiFragment;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * MainTestModule.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
@Module
public class MainTestModule {
    private LoadingDialogApiFragment mDialogFragment;

    public MainTestModule(LoadingDialogApiFragment dialogFragment) {
        mDialogFragment = dialogFragment;
    }

    @PerActivity
    @Provides
    WeatherService provideApiService(Retrofit retrofit) {
        return retrofit.create(WeatherService.class);
    }

    @PerActivity
    @Provides
    LoadingDialogApiFragment provideDialogFragment() {
        return mDialogFragment;
    }
}


