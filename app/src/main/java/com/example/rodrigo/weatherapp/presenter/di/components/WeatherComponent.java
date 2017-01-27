package com.example.rodrigo.weatherapp.presenter.di.components;

import com.example.rodrigo.weatherapp.presenter.di.module.WeatherModule;
import com.example.rodrigo.weatherapp.presenter.di.scope.PerActivity;
import com.example.rodrigo.weatherapp.view.activity.WeatherActivity;

import dagger.Component;

/**
 * WeatherComponent.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
@PerActivity
@Component(modules = WeatherModule.class, dependencies = ApplicationComponent.class)
public interface WeatherComponent {
    void inject(WeatherActivity activity);
}