package com.example.admin.umbrellaapp.view.mainactivity;

import android.location.Location;
import android.support.annotation.NonNull;

import com.example.admin.umbrellaapp.data.remote.RetrofitHelper;
import com.example.admin.umbrellaapp.model.AddressComponent;
import com.example.admin.umbrellaapp.model.CustomWeatherModel;
import com.example.admin.umbrellaapp.model.GeoCodeZipcode;
import com.example.admin.umbrellaapp.model.HourlyForecast;
import com.example.admin.umbrellaapp.model.WeatherUnderground;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Admin on 10/2/2017.
 */

public class MainActivityPresenter implements MainActivityContract.presenter {

    private static final String TAG = "MainActivityPresenter";
    MainActivityContract.view view;
    RetrofitHelper.ApiService apiService;
    @NonNull
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void attachView(MainActivityContract.view view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        compositeDisposable.dispose();
        this.view = null;
    }

    @Override
    public void checkDetaultOptions(String zipcode, String units) {

        if (zipcode == null) {
            view.getZipCodeFromUser();
        } else if (units == null) {
            view.getUnitsFromUser();
        } else {
            getWeather(zipcode);
        }

    }

    @Override
    public void getWeather(String zipcode) {

        apiService = new RetrofitHelper().getWeather();
        compositeDisposable.add(apiService.getWeather2(zipcode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<WeatherUnderground, WeatherUnderground>() {

                    @Override
                    public WeatherUnderground apply(WeatherUnderground weatherUnderground) throws Exception {
                        return weatherUnderground;
                    }
                })
                .subscribe(new Consumer<WeatherUnderground>() {
                    @Override
                    public void accept(WeatherUnderground weatherUnderground) throws Exception {
                        view.showWeather(weatherUnderground);
                    }
                }));
    }

    @Override
    public List<CustomWeatherModel> arrangeHourlyForecast(List<HourlyForecast> hourlyForecasts) {

        HourlyForecast day;
        List<HourlyForecast> hourlyList = new ArrayList<>();
        List<CustomWeatherModel> myWeather = new ArrayList<>();

        day = hourlyForecasts.get(0);
        hourlyList.add(hourlyForecasts.get(0));

        for (int i = 1; i < hourlyForecasts.size(); i++) {

            if (hourlyForecasts.get(i).getFCTTIME().getHour().equals("0")) {
                myWeather.add(new CustomWeatherModel(day, hourlyList));
                // Create new list and current forecast item to it
                hourlyList = new ArrayList<>();
                hourlyList.add(hourlyForecasts.get(i));
                // Add new day
                day = hourlyForecasts.get(i);
            } else {
                hourlyList.add(hourlyForecasts.get(i));
            }

        }

        return myWeather;
    }

    @Override
    public void loadCurrentLocation(Location location) {

        String latlng = location.getLatitude() + "," + location.getLongitude();

        retrofit2.Call<GeoCodeZipcode> getZip = RetrofitHelper.callGetZipcode(latlng);
        getZip.enqueue(new retrofit2.Callback<GeoCodeZipcode>() {
            @Override
            public void onResponse(Call<GeoCodeZipcode> call, Response<GeoCodeZipcode> response) {
                for (AddressComponent ac : response.body().getResults().get(0).getAddressComponents()) {

                    if (ac.getTypes().get(0).equals("postal_code")) {

                        view.saveZip(ac.getShortName());
                        getWeather(ac.getShortName());
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<GeoCodeZipcode> call, Throwable t) {

            }
        });

    }
}
