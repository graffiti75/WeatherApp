package com.example.rodrigo.weatherapp.presenter.di.components;

import android.content.Context;

import com.example.rodrigo.weatherapp.presenter.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * ApplicationComponent.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    Retrofit exposeRetrofit();
    Context exposeContext();
}