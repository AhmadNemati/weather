package com.example.atta.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.atta.myapplication.R;
import com.example.atta.myapplication.calendar.PersianCalendar;
import com.example.atta.myapplication.model.Datum_;

import java.util.List;

/**
 * Created by Ahmad Nemati on 10/5/18.
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {
    private List<Datum_> model;

    public DailyAdapter(List<Datum_> model) {
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.daily_list, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String maxTemp = model.get(i).getTemperatureHigh();
        String minTemp = model.get(i).getTemperatureMin();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(maxTemp)
                .append("°")
                .append(" | ")
                .append(minTemp)
                .append("°");
        viewHolder.temperature.setText(stringBuilder.toString());
        viewHolder.humidity.setText(model.get(i).getHumidity() + "%");


        viewHolder.rain.setText(model.get(i).getPrecipIntensity() + "%");
        viewHolder.pressure.setText("hPa " + model.get(i).getPressure());
        viewHolder.uvIndex.setText(model.get(i).getUvIndex());
        PersianCalendar persianCalendar=new PersianCalendar(Long.parseLong(model.get(i).getTime()));
        Log.e("Tag",model.get(i).getTime());
        viewHolder.date.setText(persianCalendar.getPersianWeekDayName());
       



    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView temperature;
        TextView rain;
        TextView pressure;
        TextView uvIndex;
        TextView windSpeed;
        TextView humidity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            temperature = itemView.findViewById(R.id.temperature);
            rain = itemView.findViewById(R.id.rain);
            pressure = itemView.findViewById(R.id.pressure);
            uvIndex = itemView.findViewById(R.id.uv_index);
            windSpeed = itemView.findViewById(R.id.wind_speed);
            humidity = itemView.findViewById(R.id.humidity);
        }
    }

}
