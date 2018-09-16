package com.example.atta.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.atta.myapplication.model.WeatherModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView curentTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        curentTemp=findViewById(R.id.current_temp);

            Service.getInstance()
                    .getService()
                    .getWeather(35.712049, 51.407204)
                    .enqueue(new Callback<WeatherModel>() {
                        @Override
                        public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                            int temp=convertf2c(response.body().getCurrently().getTemperature());
                        curentTemp.setText(String.valueOf(temp)+"°");
                        }

                        @Override
                        public void onFailure(Call<WeatherModel> call, Throwable t) {
Log.e("Error",t.getMessage());
                        }
                    });

    }



    private int convertf2c(double f)
    {
      return (int) ((f-32)*100/180);
    }
}
