import Model.*;
import Repository.OpenWeatherMapApi;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static Model.Constants.Units;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Markus on 11.09.2017.
 */
public class OpenWeatherMapApiTests {

    public static OpenWeatherMapApi repository;

    @BeforeClass
    public static void setUpClass() throws IOException {

        if (Constants.mock) {
            repository = Mockito.mock(OpenWeatherMapApi.class);
        } else {
            repository = new OpenWeatherMapApi();
        }
    }

    @Test
    public void doesRequestedCityEqualsReportedCity() throws IOException {
        Request request = new Request("Tallinn", "EE", Units.metric);
        if (Constants.mock) {
            when(repository.GetCurrentWeatherReport(request))
                    .thenReturn(new CurrentWeatherReport(11.0, "Tallinn", 22.2, 33.3, "2017-11-29 00:00:00", 200));
        }
        CurrentWeatherReport currentReport = repository.GetCurrentWeatherReport(request);
        if (Constants.mock) verify(repository).GetCurrentWeatherReport(request);
        assertEquals(request.City, currentReport.City);
    }

    @Test
    public void isCurrentWeatherResponseStatus200() throws IOException {
        Request request = new Request("Tallinn", "EE", Units.metric);
        if (Constants.mock) {
            when(repository.GetCurrentWeatherReport(request))
                    .thenReturn(new CurrentWeatherReport(11.0, "Tallinn", 22.2, 33.3, "2017-11-29 00:00:00", 200));
        }
        CurrentWeatherReport currentReport = repository.GetCurrentWeatherReport(request);
        if (Constants.mock) verify(repository).GetCurrentWeatherReport(request);
        assertEquals(200, currentReport.ResponseStatusCode);
    }

    @Test
    public void doesCurrentWeatherDateEqualsToday() throws IOException {
        Request request = new Request("Tallinn", "EE", Units.metric);
        if (Constants.mock) {
            when(repository.GetCurrentWeatherReport(request))
                    .thenReturn(new CurrentWeatherReport(11.0, "Tallinn", 22.2, 33.3, new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 200));
        }
        CurrentWeatherReport currentReport = repository.GetCurrentWeatherReport(request);

        String reportDate = currentReport.Date.substring(0, 10);
        String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        if (Constants.mock) verify(repository).GetCurrentWeatherReport(request);

        assertEquals(todayDate, reportDate);
    }

    @Test
    public void isForecastResponseStatus200() throws IOException, ParseException {
        Request request = new Request("Tallinn", "EE", Units.metric);
        if (Constants.mock) {
            when(repository.GetThreeDayWeatherForecast(request))
                    .thenReturn(new ThreeDayWeatherForecast("Tallinn", 22.2, 33.3, 200));
        }
        ThreeDayWeatherForecast forecastReport = repository.GetThreeDayWeatherForecast(request);
        if (Constants.mock) verify(repository).GetThreeDayWeatherForecast(request);
        assertEquals(200, forecastReport.ResponseStatusCode);
    }

    @Test
    public void doesForecastReturnCityCoordinates() throws IOException, ParseException {
        Request request = new Request("Tallinn", "EE", Units.metric);
        if (Constants.mock) {
            when(repository.GetThreeDayWeatherForecast(request))
                    .thenReturn(new ThreeDayWeatherForecast("Tallinn", 22.2, 33.3, 200));
        }
        ThreeDayWeatherForecast forecastReport = repository.GetThreeDayWeatherForecast(request);
        if (Constants.mock) verify(repository).GetThreeDayWeatherForecast(request);
        assertNotNull(forecastReport.Longitude);
        assertNotNull(forecastReport.Latitude);
    }

    @Test
    public void isForecastHighestTempGreaterThanLowestTemp() throws IOException, ParseException {
        Request request = new Request("Tallinn", "EE", Units.metric);
        if (Constants.mock) {

            ThreeDayWeatherForecast forecast = new ThreeDayWeatherForecast("Tallinn", 22.2, 33.3, 200);

            for (OneDayWeatherForecast item : forecast.Forecasts) {
                item.MaxTemp = 20.0;
                item.MinTemp = 1.0;
            }

            when(repository.GetThreeDayWeatherForecast(request))
                    .thenReturn(forecast);
        }
        ThreeDayWeatherForecast forecastReport = repository.GetThreeDayWeatherForecast(request);
        if (Constants.mock) verify(repository).GetThreeDayWeatherForecast(request);
        for (OneDayWeatherForecast oneDayWeatherForecast : forecastReport.Forecasts) {
            assertTrue(oneDayWeatherForecast.MaxTemp >= oneDayWeatherForecast.MinTemp);

        }
    }

    @Test
    public void isWritingAndReadingWorking() throws IOException {
        String output = "test";
        Path file = Paths.get("test.txt");
        Files.write(file, output.getBytes());
        assertEquals(output, Files.readAllLines(file).get(0).toString());

    }
}
