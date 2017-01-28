package com.example.rodrigo.weatherapp.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.WeatherApplication;
import com.example.rodrigo.weatherapp.model.api.WeatherService;
import com.example.rodrigo.weatherapp.presenter.di.components.DaggerMainTestComponent;
import com.example.rodrigo.weatherapp.presenter.di.module.MainTestModule;
import com.example.rodrigo.weatherapp.presenter.utils.ReactiveUtils;
import com.example.rodrigo.weatherapp.view.activity.test.TestMainActivity;

import javax.inject.Inject;

/**
 * LoadingDialogApiFragment.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
public class LoadingDialogApiFragment extends DialogFragment {

    //--------------------------------------------------
    // Constants
    //--------------------------------------------------

    public static final String TAG = "LoadingApi";

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    @Inject
    protected WeatherService mApiService;

    //--------------------------------------------------
    // Constructor
    //--------------------------------------------------

    public LoadingDialogApiFragment() {
    }

    //--------------------------------------------------
    // Dialog Fragment
    //--------------------------------------------------

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TestMainActivity activity = (TestMainActivity)getActivity();

        DaggerMainTestComponent.builder()
            .applicationComponent(((WeatherApplication)activity.getApplication()).getApplicationComponent())
            .mainTestModule(new MainTestModule(this))
            .build().inject(this);

        ReactiveUtils.getWeather(activity, mApiService, "Houston", this);

        return new AlertDialog.Builder(getActivity())
            .setTitle(R.string.loading)
            .setMessage(R.string.please_wait)
            .create();
    }
}