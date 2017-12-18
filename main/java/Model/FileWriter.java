package Model;

import Repository.OpenWeatherMapApi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Markus on 18.12.2017.
 */
public class FileWriter {
    public static ArrayList<String> CityList;
    public static ArrayList<String> CountryCodeList;

    public static void fileWriter(ArrayList<String> cityList, ArrayList<String> countryCodeList) throws IOException, ParseException {
        for (int countingIndex = 0; countingIndex < cityList.size(); countingIndex++) {
            Request request = new Request(cityList.get(countingIndex), countryCodeList.get(countingIndex), Constants.Units.metric);
            OpenWeatherMapApi repository = new OpenWeatherMapApi();
            CurrentWeatherReport report = repository.GetCurrentWeatherReport(request);
            ThreeDayWeatherForecast forecast = repository.GetThreeDayWeatherForecast((request));

            String output = report.toString() + System.lineSeparator() + forecast.toString();

            String fileName = cityList.get(countingIndex);
            Path file = Paths.get(fileName + ".txt");
            Files.write(file, output.getBytes());
        }
    }
}
