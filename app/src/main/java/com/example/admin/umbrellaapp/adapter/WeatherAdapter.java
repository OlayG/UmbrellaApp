package com.example.admin.umbrellaapp.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.umbrellaapp.R;
import com.example.admin.umbrellaapp.injection.sharedpreference.MySharedPreferences;
import com.example.admin.umbrellaapp.model.HourlyForecast;
import com.example.admin.umbrellaapp.model.CustomWeatherModel;

import java.util.ArrayList;
import java.util.List;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<CustomWeatherModel> hourlyForecasts = new ArrayList<>();
    MySharedPreferences unitsSharedPreferences;

    public WeatherAdapter(List<CustomWeatherModel> hourlyForecasts, MySharedPreferences unitsSharedPreferences) {
        this.hourlyForecasts = hourlyForecasts;
        this.unitsSharedPreferences = unitsSharedPreferences;
    }

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CustomWeatherModel hourlyForecast = hourlyForecasts.get(position);

        if (position == 0) {
            holder.tvDate.setText("Today");
        } else if (position == 1) {
            holder.tvDate.setText("Tommorrow");
        } else {
            holder.tvDate.setText(hourlyForecast.getDailyWeather().getFCTTIME().getWeekdayNameUnlang());
        }

        List<HourlyForecast> hourlyList = hourlyForecast.getHourlyForecasts();

        //holder.rvHourly.setItemAnimator(new DefaultItemAnimator());
        holder.rvHourly.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(), 4));
        holder.rvHourly.setItemViewCacheSize(20);
        holder.rvHourly.setDrawingCacheEnabled(true);
        holder.rvHourly.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        HoursAdapter hoursAdapter = new HoursAdapter(hourlyList, unitsSharedPreferences);
        hoursAdapter.setHasStableIds(true);
        holder.rvHourly.setAdapter(hoursAdapter);

    }

    @Override
    public int getItemCount() {
        return hourlyForecasts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        RecyclerView rvHourly;


        public ViewHolder(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            rvHourly = itemView.findViewById(R.id.rvHourlyList);

        }
    }
}
