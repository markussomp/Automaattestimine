package Model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by Markus on 7.10.2017.
 */
public class ThreeDayWeatherForecast {
    public final String City;
    public final double Longitude;
    public final double Latitude;
    public ArrayList<OneDayWeatherForecast> Forecasts;
    public final int ResponseStatusCode;

    public ThreeDayWeatherForecast(String city, double longitude, double latitude, int responseStatusCode) {
        City = city;
        Longitude = longitude;
        Latitude = latitude;
        Forecasts = new ArrayList<OneDayWeatherForecast>() {
        };
        ResponseStatusCode = responseStatusCode;

        for (int countingIndex = 0; countingIndex < 3; countingIndex++) {
            OneDayWeatherForecast oneDayWeatherForecast = new OneDayWeatherForecast();
            oneDayWeatherForecast.ForecastDate = LocalDate.now().plusDays(1 + countingIndex);
            oneDayWeatherForecast.MaxTemp = -1;
            oneDayWeatherForecast.MinTemp = 1;

            Forecasts.add(oneDayWeatherForecast);
        }

    }

    @Override
    public String toString() {
        return "Forecast report:" + System.lineSeparator() + "City: " + City + "; Forecast info: " + Forecasts.get(0).ForecastDate + "[min: " + Forecasts.get(0).MinTemp +
                "; max: " + Forecasts.get(0).MaxTemp + "] " + Forecasts.get(1).ForecastDate + "[min: " + Forecasts.get(1).MinTemp +
                "; max: " + Forecasts.get(1).MaxTemp + "] " + Forecasts.get(2).ForecastDate + "[min: " + Forecasts.get(2).MinTemp +
                "; max: " + Forecasts.get(2).MaxTemp + "]; Latitude: " + Latitude + "; Longitude: " + Longitude;
    }
}
