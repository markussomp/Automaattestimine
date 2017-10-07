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
import java.util.ArrayList;
import java.util.List;

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
        double latitude = coordinates.getDouble("lon");

        HttpURLConnection http = (HttpURLConnection) currentWeatherURL.openConnection();
        int responseStatusCode = http.getResponseCode();

        return new CurrentWeatherReport(currentTemp, city, longitude, latitude, date, responseStatusCode);

    }

    @Override
    public ThreeDayWeatherForecast GetThreeDayWeatherForecast(Request request) throws IOException, ParseException {
        URL forecastURL = GetURLForWeatherForecast(request);
        System.out.println(forecastURL.toString());
        InputStream inputStream = forecastURL.openStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null)
            stringBuilder.append(line);

        JSONObject forecastJson = new JSONObject(stringBuilder.toString());

        JSONObject coordinates = forecastJson.getJSONObject("coord");
        double longitude = coordinates.getDouble("lon");
        double latitude = coordinates.getDouble("lon");
        String city = forecastJson.getString("name");

        ThreeDayWeatherForecast forecast = new ThreeDayWeatherForecast(city, longitude, latitude);

        JSONArray forecastList = forecastJson.getJSONArray("list");

        for (int i = 0; i < forecastList.length(); i++) {
            JSONObject hourlyWeather = forecastList.getJSONObject(i);
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

        String URLString = "http://api.openweathermap.org/data/2.5/weather?q=" + request.City + "," + request.country.name() + "&units=" + request.units.name() + "&appid=" + API_KEY;
        return new URL(URLString);
    }

    public URL GetURLForWeatherForecast(Request request) throws MalformedURLException {
        String URLString = "http://api.openweathermap.org/data/2.5/forecast?q=" + request.City + "," + request.country.name() + "&units=" + request.units.name() + "&appid=" + API_KEY;
        return new URL(URLString);
    }







    public static int getWeatherApiResponseStatus(String url) throws IOException {
        int responseStatus = 200;
        return responseStatus;
    }

    public static String getWeatherForecastDate(String city) throws IOException {
        String date = "2017-09-24";
        return date;
    }

    public static String getWeatherThreeDayForecast(String city) throws IOException {
        String threeDayForecast = "threeDayForecast";
        return threeDayForecast;
    }

    public static String getCityCoordinates(String city) throws IOException {
        String coordinates = "xxx:yyy";
        return coordinates;
    }

    public static List<String> getThreeDayForecastLowestAndHighestTemp(String city) throws IOException {
        String lowTemp = getWeatherThreeDayForecast(city);
        String highTemp = getWeatherThreeDayForecast(city);
        List<String> lowAndHighTemp = new ArrayList<String>();
        lowAndHighTemp.add(lowTemp);
        lowAndHighTemp.add(highTemp);
        return lowAndHighTemp;
    }
}
