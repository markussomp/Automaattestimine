import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Markus on 11.09.2017.
 */
public class OpenWeatherMapApiTests {

    @Test
    public void testWeatherApiConnection() throws IOException {
        assertEquals(OpenWeatherMapApi.getWeatherApiResponse("http://www.example.com/"), "response");
    }

    @Test
    public void isWeatherApiResponseStatus200() throws IOException {
        assertEquals(OpenWeatherMapApi.getWeatherApiResponseStatus("http://www.example.com/"), 200);
    }

    @Test
    public void isTodayWeatherForecast() throws IOException {
        assertEquals(OpenWeatherMapApi.getWeatherForecastDate("Helsinki"), "2017-09-24");
    }

    @Test
    public void doesWeatherApiReturnThreeDayForecast() throws IOException {
        assertEquals(OpenWeatherMapApi.getWeatherThreeDayForecast("Helsinki"), "threeDayForecast");
    }

    @Test
    public void doesWeatherApiReturnCityCoordinates() throws IOException {
        assertEquals(OpenWeatherMapApi.getCityCoordinates("Helsinki"), "xxx:yyy");
    }

    @Test
    public void doesWeatherApiReturnLowestAndHighestTemp() throws IOException {

        boolean hasLowestAndHighestTemp;

        if (OpenWeatherMapApi.getThreeDayForecastLowestAndHighestTemp("Helsinki").isEmpty()) {
            hasLowestAndHighestTemp = false;
        } else {
            hasLowestAndHighestTemp = true;
        }

        assertEquals(hasLowestAndHighestTemp, true);
    }
}
