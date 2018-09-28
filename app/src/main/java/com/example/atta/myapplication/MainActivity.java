package com.example.atta.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.atta.myapplication.model.WeatherModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<WeatherModel> {
    private TextView curentTemp;
    private TextView maxTemp;
    private TextView minMaxTemp;
    private TextView precipProbability;
    private TextView windSpeed;
    private TextView pressure;
    private TextView humidity;
    private TextView uvIndex;
    private TextView cloudCover;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        curentTemp = findViewById(R.id.current_temp);
        maxTemp = findViewById(R.id.feels_like);
        minMaxTemp = findViewById(R.id.min_max_temp);
        precipProbability=findViewById(R.id.precip_probability);
        windSpeed=findViewById(R.id.wind_speed);
        pressure=findViewById(R.id.pressure);
        humidity=findViewById(R.id.humidity);
        uvIndex=findViewById(R.id.uv_index);
        cloudCover=findViewById(R.id.cloud_cover);

        Service.getInstance()
                .getService()
                .getWeather(35.712049, 51.407204)
                .enqueue(this);
    }


    @Override
    public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
        WeatherModel model = response.body();
        curentTemp.setText(model.getCurrently().getTemperature() + "°");

        maxTemp.setText(model.getDaily().getData().get(0).getTemperatureHigh() + "°");
        String maxTemp=model.getDaily().getData().get(0).getTemperatureHigh();
        String minTemp=model.getDaily().getData().get(0).getTemperatureMin();
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder
                .append(maxTemp)
                .append("°")
                .append(" | ")
                .append(minTemp)
                .append("°");
        minMaxTemp.setText(stringBuilder.toString());
        precipProbability.setText(model.getDaily().getData().get(0).getPrecipIntensity()+"%");
        windSpeed.setText("km/h " +model.getDaily().getData().get(0).getWindSpeed() );
        pressure.setText( "hPa "+model.getDaily().getData().get(0).getPressure());
        humidity.setText(model.getDaily().getData().get(0).getHumidity()+"%");
        uvIndex.setText(model.getDaily().getData().get(0).getUvIndex());
        cloudCover.setText(model.getDaily().getData().get(0).getCloudCover()+"%");


    }

    @Override
    public void onFailure(Call<WeatherModel> call, Throwable t) {
        Log.e("Error", t.getMessage());

    }
}
