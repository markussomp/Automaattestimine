import Model.*;
import Repository.OpenWeatherMapApi;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    public static FileReader fileReader;

    @BeforeClass
    public static void setUpClass() throws IOException {

        if (Constants.mock) {
            repository = Mockito.mock(OpenWeatherMapApi.class);
            fileReader = Mockito.mock(FileReader.class);
        } else {
            repository = new OpenWeatherMapApi();
            fileReader = new FileReader();
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
    public void doesFileReaderCompilesExpectedRequestTest() throws IOException {
        Request expectedRequest = new Request("Tallinn", "EE", Units.metric);
        if (Constants.mock) {
            when(fileReader.fileReader("input.txt"))
                    .thenReturn(new ArrayList<String>(Arrays.asList("Tallinn", "EE")));
        }
        Request request = new Request(fileReader.fileReader("input.txt").get(0), fileReader.fileReader("input.txt").get(1), Constants.Units.metric);
        assertEquals(expectedRequest.City, request.City);

    }
}

