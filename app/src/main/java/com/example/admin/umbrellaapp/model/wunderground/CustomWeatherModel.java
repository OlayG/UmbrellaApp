package com.example.admin.umbrellaapp.model.wunderground;

import java.util.List;

/**
 * Created by Admin on 9/4/2017.
 */

public class CustomWeatherModel {

    HourlyForecast dailyWeather;
    List<HourlyForecast> hourlyForecasts;

    public CustomWeatherModel(HourlyForecast dailyWeather, List<HourlyForecast> hourlyForecasts) {
        this.dailyWeather = dailyWeather;
        this.hourlyForecasts = hourlyForecasts;
    }

    public HourlyForecast getDailyWeather() {
        return dailyWeather;
    }

    public void setDailyWeather(HourlyForecast dailyWeather) {
        this.dailyWeather = dailyWeather;
    }

    public List<HourlyForecast> getHourlyForecasts() {
        return hourlyForecasts;
    }

    public void setHourlyForecasts(List<HourlyForecast> hourlyForecasts) {
        this.hourlyForecasts = hourlyForecasts;
    }

}
