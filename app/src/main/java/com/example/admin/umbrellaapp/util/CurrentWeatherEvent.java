package com.example.admin.umbrellaapp.util;

import com.example.admin.umbrellaapp.model.wunderground.WeatherUnderground;

public class CurrentWeatherEvent {

    public final WeatherUnderground weatherUnderground;

    public CurrentWeatherEvent(WeatherUnderground weatherUnderground) {
        this.weatherUnderground = weatherUnderground;
    }

    public WeatherUnderground getWeatherUnderground() {
        return weatherUnderground;
    }
}
