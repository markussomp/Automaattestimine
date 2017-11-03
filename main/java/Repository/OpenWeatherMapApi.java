package Repository;

import Model.CurrentWeatherReport;
import Model.OneDayWeatherForecast;
import Model.Request;
import Model.ThreeDayWeatherForecast;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static Model.Constants.API_KEY;

public class OpenWeatherMapApi implements WeatherApiInterface {
    private static final DateTimeFormatter ApiDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public CurrentWeatherReport GetCurrentWeatherReport(Request request) throws IOException {


        URL currentWeatherURL = GetURLForCurrentWeather(request);

        InputStream inputStream = currentWeatherURL.openStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            stringBuilder.append(line);

        JSONObject currentWeatherJson = new JSONObject(stringBuilder.toString());
        JSONObject main = currentWeatherJson.getJSONObject("main");

        String city = currentWeatherJson.getString("name");
        int date = currentWeatherJson.getInt("dt");
        Double currentTemp = main.getDouble("temp");
        JSONObject coordinates = currentWeatherJson.getJSONObject("coord");
        double longitude = coordinates.getDouble("lon");
        double latitude = coordinates.getDouble("lat");

        HttpURLConnection http = (HttpURLConnection) currentWeatherURL.openConnection();
        int responseStatusCode = http.getResponseCode();

        return new CurrentWeatherReport(currentTemp, city, longitude, latitude, date, responseStatusCode);

    }

    @Override
    public ThreeDayWeatherForecast GetThreeDayWeatherForecast(Request request) throws IOException, ParseException {

        URL forecastURL = GetURLForWeatherForecast(request);
        InputStream inputStream = forecastURL.openStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            stringBuilder.append(line);

        JSONObject forecastJson = new JSONObject(stringBuilder.toString());

        JSONObject cityInfo = forecastJson.getJSONObject("city");
        JSONObject coordinates = cityInfo.getJSONObject("coord");
        double longitude = coordinates.getDouble("lon");
        double latitude = coordinates.getDouble("lat");
        String city = cityInfo.getString("name");

        HttpURLConnection http = (HttpURLConnection) forecastURL.openConnection();
        int responseStatusCode = http.getResponseCode();

        ThreeDayWeatherForecast forecast = new ThreeDayWeatherForecast(city, longitude, latitude, responseStatusCode);

        JSONArray forecastList = forecastJson.getJSONArray("list");

        for (int countingIndex = 0; countingIndex < forecastList.length(); countingIndex++) {
            JSONObject hourlyWeather = forecastList.getJSONObject(countingIndex);
            String date = hourlyWeather.getString("dt_txt");
            JSONObject temperatures = hourlyWeather.getJSONObject("main");
            double maxTemp = temperatures.getDouble("temp_max");
            double minTemp = temperatures.getDouble("temp_min");


            LocalDate forecastDate = LocalDate.parse(date, ApiDateFormat);

            for (OneDayWeatherForecast item : forecast.Forecasts) {

                if (item.ForecastDate.equals(forecastDate)) {
                    if (maxTemp > item.MaxTemp) {
                        item.MaxTemp = maxTemp;
                    }
                    if (minTemp < item.MinTemp) {
                        item.MinTemp = minTemp;
                    }
                }
            }
        }
        return forecast;
    }

    public URL GetURLForCurrentWeather(Request request) throws MalformedURLException {

        String URLString = "http://api.openweathermap.org/data/2.5/weather?q=" + request.City + "," + request.country + "&units=" + request.units.name() + "&appid=" + API_KEY;
        return new URL(URLString);
    }

    public URL GetURLForWeatherForecast(Request request) throws MalformedURLException {
        String URLString = "http://api.openweathermap.org/data/2.5/forecast?q=" + request.City + "," + request.country + "&units=" + request.units.name() + "&appid=" + API_KEY;
        return new URL(URLString);
    }
}
