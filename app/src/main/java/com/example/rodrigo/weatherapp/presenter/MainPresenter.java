package com.example.rodrigo.weatherapp.presenter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.rodrigo.weatherapp.AppConfiguration;
import com.example.rodrigo.weatherapp.R;
import com.example.rodrigo.weatherapp.databinding.ActivityMainBinding;
import com.example.rodrigo.weatherapp.model.City;
import com.example.rodrigo.weatherapp.presenter.utils.ActivityUtils;
import com.example.rodrigo.weatherapp.presenter.utils.ReactiveUtils;
import com.example.rodrigo.weatherapp.presenter.utils.Utils;
import com.example.rodrigo.weatherapp.presenter.utils.dialog.DialogUtils;
import com.example.rodrigo.weatherapp.view.activity.MainActivity;
import com.example.rodrigo.weatherapp.view.activity.WeatherActivity;
import com.example.rodrigo.weatherapp.view.adapter.CityAdapter;
import com.example.rodrigo.weatherapp.view.adapter.DividerItemDecoration;
import com.example.rodrigo.weatherapp.view.listeners.RecyclerTouchListener;
import com.example.rodrigo.weatherapp.view.listeners.RecyclerViewListeners;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * MainPresenter.java.
 *
 * @author Rodrigo Cericatto
 * @since Jan 27, 2017
 */
public class MainPresenter {

    //--------------------------------------------------
    // Attributes
    //--------------------------------------------------

    private MainActivity mActivity;

    //--------------------------------------------------
    // Constructor
    //--------------------------------------------------

    @Inject
    public MainPresenter(MainActivity activity) {
        mActivity = activity;
    }

    //--------------------------------------------------
    // Public Methods
    //--------------------------------------------------

    public void getCityInfoFromApi(String cityName) {
        mActivity.setCityName(cityName);
        String message = mActivity.getString(R.string.activity_main__loading_data, mActivity.getCityName());
        Dialog dialog = DialogUtils.showProgressDialog(mActivity, message);

        ReactiveUtils.getWeather(mActivity, cityName, dialog);
    }

    public void getCityDialog() {
        if (Utils.checkConnection(mActivity)) {
            // Gets the city from the dialog.
            DialogUtils.showCustomDialog(mActivity, R.layout.custom_dialog,
                R.string.activity_main__add_city_dialog_title, (context, city) -> getCityInfoFromApi(city)
            );
        } else {
            DialogUtils.showNoConnectionDialog(mActivity);
        }
    }

    public void setAdapter(ActivityMainBinding binding) {
        binding.idActivityMainRecyclerView.addOnItemTouchListener(setRecyclerViewListener(binding));

        String message = mActivity.getString(R.string.reading_from_database);
        ProgressDialog dialog = DialogUtils.showProgressDialog(mActivity, message);
        ReactiveUtils.getCityList(mActivity, false, dialog);
    }

    public void citySearchedExists(String cityName) {
        Boolean cityRepeated = cityAlreadyInAdapter(cityName);
        if (cityRepeated) {
            DialogUtils.showSimpleAlert(mActivity, R.string.activity_main__repeated_city_dialog_title,
                    R.string.activity_main__repeated_city_dialog_message);
        } else {
            addCityInDatabase(cityName);
        }
    }

    public void changeAdapter(List<City> list, ActivityMainBinding binding, Boolean updateAdapter) {
        if (updateAdapter) {
            if (list == null || list.size() <= 0) {
                Toast.makeText(mActivity, R.string.database_error, Toast.LENGTH_LONG);
            } else {
                mActivity.getCityList().addAll(list);
                mActivity.getAdapter().notifyDataSetChanged();
            }
        } else {
            mActivity.setAdapter(new CityAdapter(mActivity, list));
            LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            binding.idActivityMainRecyclerView.setLayoutManager(layoutManager);
            binding.idActivityMainRecyclerView.setItemAnimator(new DefaultItemAnimator());
            binding.idActivityMainRecyclerView.setAdapter(mActivity.getAdapter());
            binding.idActivityMainRecyclerView.addItemDecoration(
                new DividerItemDecoration(mActivity, R.drawable.divider));
        }
        mActivity.setCityList(list);
    }

    public void addCityIntoAdapter(City newCity, ActivityMainBinding binding, Boolean result) {
        if (result) {
            binding.idActivityMainRecyclerView.getItemAnimator().setAddDuration(2000);
            mActivity.getAdapter().add(mActivity.getCityList().size(), newCity);
        } else {
            Toast.makeText(mActivity, R.string.database_error, Toast.LENGTH_LONG);
        }
    }

    public void removeCityFromAdapter(Boolean result, City city) {
        int position = 0;
        for (City item: mActivity.getCityList()) {
            if (city.getId() == item.getId()) {
                break;
            } else {
                position++;
            }
        }

        if (result) {
            mActivity.getAdapter().remove(position);
        } else {
            Toast.makeText(mActivity, R.string.database_error, Toast.LENGTH_LONG);
        }
    }

    //--------------------------------------------------
    // Private Methods
    //--------------------------------------------------

    private Boolean cityAlreadyInAdapter(String cityName) {
        Boolean isInside = false;
        String cityNameLowerCase = cityName.toLowerCase();

        for (City city : mActivity.getCityList()) {
            String currentCityName = city.getCity().toLowerCase();
            if (currentCityName.contains(cityNameLowerCase)) {
                isInside = true;
                break;
            }
        }
        return isInside;
    }

    private void addCityInDatabase(String cityName) {
        City lastElement = mActivity.getCityList().get(mActivity.getCityList().size() - 1);
        Integer index = lastElement.getId() + 1;
        City newCity = new City(index, cityName);
        List<City> list = new ArrayList<>();
        list.add(newCity);

        String message = mActivity.getString(R.string.inserting_in_database);
        ProgressDialog dialog = DialogUtils.showProgressDialog(mActivity, message);
        ReactiveUtils.insertCityList(mActivity, list, newCity, dialog);
    }

    private RecyclerTouchListener setRecyclerViewListener(ActivityMainBinding binding) {
        return new RecyclerTouchListener(mActivity, binding.idActivityMainRecyclerView,
        new RecyclerViewListeners() {
            @Override
            public void onClick(View view, final int position) {
                if (Utils.checkConnection(mActivity)) {
                    mActivity.setCityName(mActivity.getCityList().get(position).getCity());
                    ActivityUtils.startActivityExtras(mActivity, WeatherActivity.class,
                            AppConfiguration.CITY_NAME_EXTRA, mActivity.getCityName());
                } else {
                    DialogUtils.showNoConnectionDialog(mActivity);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                removeCityFromDatabase(position);
            }
        });
    }

    private void removeCityFromDatabase(Integer position) {
        DialogUtils.showConfirmDialog(mActivity, 0, R.string.activity_main__remove_city_dialog_title,
            confirmListener(position), cancelListener());
    }

    private DialogInterface.OnClickListener confirmListener(final int position) {
        return (context, which) -> {
            City city = mActivity.getCityList().get(position);

            String message = mActivity.getString(R.string.removing_from_database);
            ProgressDialog dialog = DialogUtils.showProgressDialog(mActivity, message);
            ReactiveUtils.removeCity(mActivity, city, dialog);
        };
    }

    private DialogInterface.OnClickListener cancelListener() {
        return (context, which) -> context.dismiss();
    }
}