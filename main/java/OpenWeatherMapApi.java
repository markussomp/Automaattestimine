import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenWeatherMapApi {

    public static String getWeatherApiResponse(String url) throws IOException {
        String response = "response";
        return response;
    }

    public static int getWeatherApiResponseStatus(String url) throws IOException {
        int responseStatus = 200;
        return responseStatus;
        dasdsad
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
