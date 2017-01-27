package com.example.rodrigo.weatherapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.WeatherApplication;
import com.example.rodrigo.weatherapp.presenter.di.components.ApplicationComponent;

/**
 * BaseActivity.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
public abstract class BaseActivity extends AppCompatActivity {

    //--------------------------------------------------
    // Activity Life Cycle
    //--------------------------------------------------

    protected abstract int getContentView();

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        resolveDaggerDependency();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onViewReady(savedInstanceState, getIntent());
    }

    //--------------------------------------------------
    // Dependency Injection
    //--------------------------------------------------

    protected void resolveDaggerDependency() {}

    protected ApplicationComponent getApplicationComponent() {
        return ((WeatherApplication) getApplication()).getApplicationComponent();
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    public void showBackArrow(AppCompatActivity activity, Boolean homeEnabled, String string) {
        Toolbar toolbar = (Toolbar)activity.findViewById(R.id.id_toolbar);
        if (toolbar != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(homeEnabled);
            activity.getSupportActionBar().setTitle(string);
        }
    }
}