package Repository;

import Model.CurrentWeatherReport;
import Model.Request;
import Model.ThreeDayWeatherForecast;

import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Markus on 7.10.2017.
 */
public interface WeatherApiInterface {
    CurrentWeatherReport GetCurrentWeatherReport(Request request) throws IOException;

    ThreeDayWeatherForecast GetThreeDayWeatherForecast(Request request0) throws IOException, ParseException;
}
