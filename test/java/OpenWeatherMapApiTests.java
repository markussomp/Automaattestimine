import Model.CurrentWeatherReport;
import Model.Request;
import Repository.OpenWeatherMapApi;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Model.Constants.CountryCode;
import static Model.Constants.Units;
import static org.junit.Assert.assertEquals;

/**
 * Created by Markus on 11.09.2017.
 */
public class OpenWeatherMapApiTests {

    @Test
    public void doesRequestedCityEqualsReportedCity() throws IOException {
        OpenWeatherMapApi repository = new OpenWeatherMapApi();
        Request request = new Request("Tallinn", CountryCode.EE, Units.metric);
        CurrentWeatherReport report = repository.GetCurrentWeatherReport(request);
        assertEquals(request.City, report.City);
    }

    @Test
    public void isWeatherApiResponseStatus200() throws IOException {
        OpenWeatherMapApi repository = new OpenWeatherMapApi();
        Request request = new Request("Tallinn", CountryCode.EE, Units.metric);
        CurrentWeatherReport report = repository.GetCurrentWeatherReport(request);
        assertEquals(200, report.ResponseStatusCode);
    }

    @Test
    public void doesCurrentWeatherDateEqualsToday() throws IOException {
        OpenWeatherMapApi repository = new OpenWeatherMapApi();
        Request request = new Request("Tallinn", CountryCode.EE, Units.metric);
        CurrentWeatherReport report = repository.GetCurrentWeatherReport(request);

        String reportDate = new SimpleDateFormat("yyyy.MM.dd").format(report.Date * 1000L);
        String todayDate = new SimpleDateFormat("yyyy.MM.dd").format(new Date());

        assertEquals(todayDate, reportDate);
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
