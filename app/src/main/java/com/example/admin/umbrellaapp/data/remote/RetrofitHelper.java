package com.example.admin.umbrellaapp.data.remote;

import com.example.admin.umbrellaapp.model.wunderground.GeoCodeZipcode;
import com.example.admin.umbrellaapp.model.wunderground.WeatherUnderground;
import com.example.admin.umbrellaapp.util.Constant;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RetrofitHelper {

    //http://api.wunderground.com/api/28dd03e955d5a1ca/conditions/hourly/q/19055.json
    public static final String API_KEY = "28dd03e955d5a1ca";
    public static final String BASE_URL = "http://api.wunderground.com/";
    public static final String PATH = "api/" + API_KEY + "/conditions/hourly/q/{zipcode}.json";
    public static final String PATH10DAY = "api/" + API_KEY + "/conditions/hourly10day/q/{zipcode}.json";
    public static final String GEOCODE_BASE_URL = "https://maps.googleapis.com/";
    public static final String GEOCODE_PATH = "maps/api/geocode/json?";

    public static Retrofit create(String baseUrl) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    public static Call<WeatherUnderground> callWeather(String zipcode) {

        Retrofit retrofit = create(BASE_URL);
        ApiService apiService = retrofit.create(ApiService.class);

        return apiService.getWeather(zipcode);
    }

    public static Call<GeoCodeZipcode> callGetZipcode(String latlng) {

        Retrofit retrofit = create(GEOCODE_BASE_URL);
        ApiService apiService = retrofit.create(ApiService.class);

        return apiService.getGeocodeAddress(latlng, Constant.GEOCODE_API_KEY);
    }

    public ApiService getWeather(){
        final Retrofit retrofit = create(BASE_URL);
        return retrofit.create(ApiService.class);
    }

    public interface ApiService {

        @GET(PATH)
        Call<WeatherUnderground> getWeather(@Path("zipcode") String zipcode);

        @GET(PATH)
        Single<WeatherUnderground> getWeather2(@Path("zipcode") String zipcode);

        @GET(GEOCODE_PATH)
        Call<GeoCodeZipcode> getGeocodeAddress(@Query("latlng") String latlng, @Query("key") String key);
    }

}
