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
            oneDayWeatherForecast.MaxTemp = -Double.MAX_VALUE;
            oneDayWeatherForecast.MinTemp = Double.MAX_VALUE;

            Forecasts.add(oneDayWeatherForecast);
        }

    }
}
