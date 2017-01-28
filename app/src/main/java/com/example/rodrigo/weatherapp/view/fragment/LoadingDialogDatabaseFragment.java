package com.example.rodrigo.weatherapp.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.model.City;
import com.example.rodrigo.weatherapp.presenter.utils.ReactiveUtils;
import com.example.rodrigo.weatherapp.view.activity.test.TestDatabaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * LoadingDialogDatabaseFragment.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
public class LoadingDialogDatabaseFragment extends DialogFragment {

    //--------------------------------------------------
    // Constants
    //--------------------------------------------------

    public static final String TAG = "LoadingDatabase";

    //--------------------------------------------------
    // Constructor
    //--------------------------------------------------

    public LoadingDialogDatabaseFragment() {
    }

    //--------------------------------------------------
    // Dialog Fragment
    //--------------------------------------------------

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TestDatabaseActivity activity = (TestDatabaseActivity)getActivity();
        getCityListFromDatabase(activity);

        return new AlertDialog.Builder(getActivity())
            .setTitle(R.string.loading)
            .setMessage(R.string.please_wait)
            .create();
    }

    private void getCityListFromDatabase(TestDatabaseActivity activity) {
        List<City> list = new ArrayList<>();
        for (int i = 0; i < AppConfiguration.DEFAULT_DATA.length; i++) {
            list.add(new City(i + 1, AppConfiguration.DEFAULT_DATA[i]));
        }
        ReactiveUtils.insertCityList(activity, list, this);
    }
}