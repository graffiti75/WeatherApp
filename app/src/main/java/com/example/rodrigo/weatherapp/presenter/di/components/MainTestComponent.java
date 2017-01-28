package com.example.rodrigo.weatherapp.presenter.di.components;


import com.example.rodrigo.weatherapp.presenter.di.module.MainTestModule;
import com.example.rodrigo.weatherapp.presenter.di.scope.PerActivity;
import com.example.rodrigo.weatherapp.view.fragment.LoadingDialogApiFragment;

import dagger.Component;

/**
 * MainComponent.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
@PerActivity
@Component(modules = MainTestModule.class, dependencies = ApplicationComponent.class)
public interface MainTestComponent {
    void inject(LoadingDialogApiFragment fragment);
}