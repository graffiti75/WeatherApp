package com.example.rodrigo.weatherapp.presenter.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * PerActivity.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}