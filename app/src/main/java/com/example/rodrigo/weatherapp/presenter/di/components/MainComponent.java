package com.example.rodrigo.weatherapp.presenter.di.components;


import com.example.rodrigo.weatherapp.presenter.di.module.MainModule;
import com.example.rodrigo.weatherapp.presenter.di.scope.PerActivity;
import com.example.rodrigo.weatherapp.view.activity.MainActivity;

import dagger.Component;

/**
 * MainComponent.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
@PerActivity
@Component(modules = MainModule.class, dependencies = ApplicationComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}