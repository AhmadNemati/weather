package com.example.atta.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atta.myapplication.adapter.DailyAdapter;
import com.example.atta.myapplication.model.WeatherModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<WeatherModel>, PermissionListener,View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {
    private TextView curentTemp;
    private TextView maxTemp;
    private TextView minMaxTemp;
    private TextView precipProbability;
    private TextView windSpeed;
    private TextView pressure;
    private TextView humidity;
    private TextView uvIndex;
    private TextView cloudCover;
    private TextView cityPosition;
    private RecyclerView recyclerView;
    private Button searchCity;
    LocationManager locationManager;
    int PLACE_PICKER_REQUEST = 1;
    private SwipeRefreshLayout swipeContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        curentTemp = findViewById(R.id.current_temp);
        maxTemp = findViewById(R.id.feels_like);
        minMaxTemp = findViewById(R.id.min_max_temp);
        precipProbability = findViewById(R.id.precip_probability);
        windSpeed = findViewById(R.id.wind_speed);
        pressure = findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);
        uvIndex = findViewById(R.id.uv_index);
        cloudCover = findViewById(R.id.cloud_cover);
        recyclerView = findViewById(R.id.list);
        cityPosition=findViewById(R.id.city_position);
        searchCity=findViewById(R.id.search_city);
        swipeContainer =  findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setRefreshing(true);


        searchCity.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TedPermission.with(this)
                .setPermissionListener(this)
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();

        updateData(35.712049,51.407204);






    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                updateData(place.getLatLng().latitude,place.getLatLng().longitude);
               // cityPosition.setText(place.getName());
            }
        }
    }


    private void updateData(double lat,double lng)
    {
        cityPosition.setText(cityNameByLatLong(lat,lng));
        Service.getInstance()
                .getService()
                .getWeather(lat, lng)
                .enqueue(this);

    }


    @Override
    public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {

        WeatherModel model = response.body();
        swipeContainer.setRefreshing(false);

        curentTemp.setText(model.getCurrently().getTemperature() + "°");

        maxTemp.setText(model.getDaily().getData().get(0).getTemperatureHigh() + "°");
        String maxTemp = model.getDaily().getData().get(0).getTemperatureHigh();
        String minTemp = model.getDaily().getData().get(0).getTemperatureMin();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(maxTemp)
                .append("°")
                .append(" | ")
                .append(minTemp)
                .append("°");
        minMaxTemp.setText(stringBuilder.toString());
        precipProbability.setText(model.getDaily().getData().get(0).getPrecipIntensity() + "%");
        windSpeed.setText("km/h " + model.getDaily().getData().get(0).getWindSpeed());
        pressure.setText("hPa " + model.getDaily().getData().get(0).getPressure());
        humidity.setText(model.getDaily().getData().get(0).getHumidity() + "%");
        uvIndex.setText(model.getDaily().getData().get(0).getUvIndex());
        cloudCover.setText(model.getDaily().getData().get(0).getCloudCover() + "%");
        model.getDaily().getData().remove(0);
        DailyAdapter adapter = new DailyAdapter(model.getDaily().getData());
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onFailure(Call<WeatherModel> call, Throwable t) {
        Log.e("Error", t.getMessage());

    }

    private String cityNameByLatLong(double lat,double lng)
    {
        Geocoder geocoder = new Geocoder(this, new Locale("fa_IR"));
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
            return addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onPermissionGranted() {

         locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

                updateData(location.getLatitude(),location.getLongitude());
                locationManager.removeUpdates(this);

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.e("Location", "Not Enable");
            updateData(35.712049,51.407204);

        }



        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onPermissionDenied(List<String> deniedPermissions) {
        Log.e("Tag","onPermissionDenied");
       updateData(35.712049,51.407204);
    }

    @Override
    public void onClick(View view) {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(MainActivity.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        updateData(35.712049,51.407204);
    }
}
