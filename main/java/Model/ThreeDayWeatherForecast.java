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

    public ThreeDayWeatherForecast(String city, double longitude, double latitude) {
        City = city;
        Longitude = longitude;
        Latitude = latitude;
        Forecasts = new ArrayList<OneDayWeatherForecast>() {
        };

        for (int countingIndex = 0; countingIndex < 3; countingIndex++) {
            OneDayWeatherForecast oneDayForecast = new OneDayWeatherForecast();
            oneDayForecast.ForecastDate = LocalDate.now().plusDays(1 + countingIndex);
            oneDayForecast.MaxTemp = -Double.MAX_VALUE;
            oneDayForecast.MinTemp = Double.MAX_VALUE;

            Forecasts.add(oneDayForecast);
        }

    }
}
