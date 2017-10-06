package com.example.admin.umbrellaapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.admin.umbrellaapp.R;
import com.example.admin.umbrellaapp.injection.sharedpreference.MySharedPreferences;
import com.example.admin.umbrellaapp.model.wunderground.HourlyForecast;
import com.example.admin.umbrellaapp.util.Constant;
import java.util.ArrayList;
import java.util.List;

public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.ViewHolder> {

    List<HourlyForecast> hourlyList = new ArrayList<>();
    MySharedPreferences unitsSharedPreferences;
    String units;
    int maxPos, minPos = 0;

    public HoursAdapter(List<HourlyForecast> hourlyList, MySharedPreferences unitsSharedPreferences) {
        this.hourlyList = hourlyList;
        this.unitsSharedPreferences = unitsSharedPreferences;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        findMaxMin();
        units = unitsSharedPreferences.getStringData(Constant.UNITS_KEY_SP);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_list_item, parent, false);
        return new ViewHolder(v);
    }

    private void findMaxMin() {
        int min = Integer.parseInt(hourlyList.get(0).getTemp().getEnglish());
        int max = 0;
        for (int i = 0; i < hourlyList.size(); i++) {
            int degree = Integer.parseInt(hourlyList.get(i).getTemp().getEnglish());
            if (degree > max) {
                max = degree;
                maxPos = i;
            }
            if (degree < min) {
                min = degree;
                if(minPos < i)
                    minPos = i;
            }
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HourlyForecast hourlyForecast = hourlyList.get(position);

        holder.tvTime.setText(hourlyForecast.getFCTTIME().getCivil());


        if (units.equalsIgnoreCase(Constant.FAHRENHEIT)) {
            holder.tvDegree.setText(hourlyForecast.getTemp().getEnglish());
        } else {
            holder.tvDegree.setText(hourlyForecast.getTemp().getMetric());
        }

        if (position ==maxPos) {
            holder.tvWeatherImage.setImageResource(R.drawable.ic_day);
        } else if (position == minPos) {
            holder.tvWeatherImage.setImageResource(R.drawable.ic_cloudy_animated);
        } else {
            holder.tvWeatherImage.setImageResource(R.drawable.ic_night);
        }
    }

    @Override
    public int getItemCount() {
        return hourlyList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime, tvDegree;
        ImageView tvWeatherImage;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTime = itemView.findViewById(R.id.tvTime);
            tvDegree = itemView.findViewById(R.id.tvDegree);
            tvWeatherImage = itemView.findViewById(R.id.tvWeatherImage);
        }
    }
}
