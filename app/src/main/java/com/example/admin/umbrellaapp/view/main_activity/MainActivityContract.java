package com.example.admin.umbrellaapp.view.main_activity;

import android.location.Location;

import com.example.admin.umbrellaapp.BasePresenter;
import com.example.admin.umbrellaapp.BaseView;
import com.example.admin.umbrellaapp.model.CustomWeatherModel;
import com.example.admin.umbrellaapp.model.HourlyForecast;

import java.util.List;

/**
 * Created by Admin on 10/2/2017.
 */

public interface MainActivityContract {

    interface view extends BaseView {

        void getZipCodeFromUser();

        void saveZip(String zip);

        void getUnitsFromUser();

    }

    interface presenter extends BasePresenter<view> {

        void checkDetaultOptions(String zipcode, String units);

        void getCurrentWeather(String zipcode);

        List<CustomWeatherModel> arrangeHourlyForecast(List<HourlyForecast> hourlyTenDay);

        void loadCurrentLocation(Location location);
    }
}
