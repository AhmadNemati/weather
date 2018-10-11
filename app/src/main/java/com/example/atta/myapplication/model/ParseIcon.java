package com.example.atta.myapplication.model;

import com.example.atta.myapplication.R;

/**
 * Created by Ahmad Nemati on 10/11/18.
 */
public class ParseIcon {

    private static int getMaterialIcon(String icon) {
        int type;


        switch (icon) {
            case "partly-cloudy-night":
                type = 3;

                break;
            case "thunderstorm":
                type = 10;

                break;
            case "cloudy":
                type = 9;

                break;
            case "clear-day":
                type = 0;

                break;
            case "tornado":
                type = 12;

                break;
            case "fog":
                type = 8;

                break;
            case "hail":
                type = 11;

                break;
            case "rain":
                type = 4;

                break;
            case "snow":
                type = 6;

                break;
            case "wind":
                type = 7;

                break;
            case "sleet":
                type = 5;

                break;
            case "clear-night":
                type = 1;
                break;
            case "partly-cloudy-day":
                type = 2;

                break;
            default:
                type = -1;
                break;
        }


        switch (type) {
            case 0:
                return R.drawable.clear_day;
            case 1:
                return R.drawable.clear_night;
            case 2:
                return R.drawable.partly_cloudy;
            case 3:
                return R.drawable.partly_cloudy_night;
            case 4:
                return R.drawable.rainy_weather;
            case 5:
            case 6:
                return R.drawable.snow_weather;
            case 7:
                return R.drawable.windy_weather;
            case 8:
                return R.drawable.haze_weather;
            case 9:
                return R.drawable.cloudy_weather;
            default:
                return R.drawable.cloudy_weather;
        }
    }
}
