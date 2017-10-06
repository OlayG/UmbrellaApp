package com.example.admin.umbrellaapp.view.mainactivity;

import android.location.Location;

import com.example.admin.umbrellaapp.BasePresenter;
import com.example.admin.umbrellaapp.BaseView;
import com.example.admin.umbrellaapp.model.wunderground.CustomWeatherModel;
import com.example.admin.umbrellaapp.model.wunderground.HourlyForecast;
import com.example.admin.umbrellaapp.model.wunderground.WeatherUnderground;

import java.util.List;

/**
 * Created by Admin on 10/2/2017.
 */

public interface MainActivityContract {

    interface view extends BaseView {

        void getZipCodeFromUser();

        void saveZip(String zip);

        void getUnitsFromUser();

        void showWeather(WeatherUnderground weatherUnderground);

    }

    interface presenter extends BasePresenter<view> {

        void checkDetaultOptions(String zipcode, String units);

        void getWeather(String zipcode);

        List<CustomWeatherModel> arrangeHourlyForecast(List<HourlyForecast> hourlyTenDay);

        void loadCurrentLocation(Location location);
    }
}
