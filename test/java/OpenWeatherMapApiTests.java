import Model.CurrentWeatherReport;
import Model.OneDayWeatherForecast;
import Model.Request;
import Model.ThreeDayWeatherForecast;
import Repository.OpenWeatherMapApi;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Model.Constants.Units;
import static org.junit.Assert.*;

/**
 * Created by Markus on 11.09.2017.
 */
public class OpenWeatherMapApiTests {

    @Test
    public void doesRequestedCityEqualsReportedCity() throws IOException {
        OpenWeatherMapApi repository = new OpenWeatherMapApi();
        Request request = new Request("Tallinn", "EE", Units.metric);
        CurrentWeatherReport currentReport = repository.GetCurrentWeatherReport(request);
        assertEquals(request.City, currentReport.City);
    }

    @Test
    public void isCurrentWeatherResponseStatus200() throws IOException {
        OpenWeatherMapApi repository = new OpenWeatherMapApi();
        Request request = new Request("Tallinn", "EE", Units.metric);
        CurrentWeatherReport currentReport = repository.GetCurrentWeatherReport(request);
        assertEquals(200, currentReport.ResponseStatusCode);
    }

    @Test
    public void doesCurrentWeatherDateEqualsToday() throws IOException {
        OpenWeatherMapApi repository = new OpenWeatherMapApi();
        Request request = new Request("Tallinn", "EE", Units.metric);
        CurrentWeatherReport currentReport = repository.GetCurrentWeatherReport(request);

        String reportDate = new SimpleDateFormat("yyyy.MM.dd").format(currentReport.Date * 1000L);
        String todayDate = new SimpleDateFormat("yyyy.MM.dd").format(new Date());

        assertEquals(todayDate, reportDate);
    }

    @Test
    public void isForecastResponseStatus200() throws IOException, ParseException {
        OpenWeatherMapApi repository = new OpenWeatherMapApi();
        Request request = new Request("Tallinn", "EE", Units.metric);
        ThreeDayWeatherForecast forecastReport = repository.GetThreeDayWeatherForecast(request);
        assertEquals(200, forecastReport.ResponseStatusCode);
    }

    @Test
    public void doesForecastReturnCityCoordinates() throws IOException, ParseException {
        OpenWeatherMapApi repository = new OpenWeatherMapApi();
        Request request = new Request("Tallinn", "EE", Units.metric);
        ThreeDayWeatherForecast forecastReport = repository.GetThreeDayWeatherForecast(request);
        assertNotNull(forecastReport.Longitude);
        assertNotNull(forecastReport.Latitude);
    }

    @Test
    public void isForecastHighestTempGreaterThanLowestTemp() throws IOException, ParseException {

        OpenWeatherMapApi repository = new OpenWeatherMapApi();
        Request request = new Request("Tallinn", "EE", Units.metric);
        ThreeDayWeatherForecast forecastReport = repository.GetThreeDayWeatherForecast(request);

        for (OneDayWeatherForecast oneDayWeatherForecast : forecastReport.Forecasts) {
            assertTrue(oneDayWeatherForecast.MaxTemp >= oneDayWeatherForecast.MinTemp);

        }
    }
}
