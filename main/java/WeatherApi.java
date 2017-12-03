import Model.Constants;
import Model.CurrentWeatherReport;
import Model.Request;
import Model.ThreeDayWeatherForecast;
import Repository.OpenWeatherMapApi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Markus on 3.11.2017.
 */
public class WeatherApi {
    public static void main(String[] args) throws IOException, ParseException {
        String city;
        String countryCode;

        Path inputPath = Paths.get("input.txt");
        List<String> cityList = new ArrayList<>();
        List<String> countryCodeList = new ArrayList<>();
        if (Files.exists(inputPath)) {
            List<String> items = Files.readAllLines(inputPath);
            for (int countingIndex = 0; countingIndex < items.size(); countingIndex++) {
                if (countingIndex % 2 == 0) {
                    city = items.get(countingIndex);
                    city = city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();
                    cityList.add(city);
                } else {
                    countryCode = items.get(countingIndex);
                    countryCode = countryCode.toUpperCase();
                    countryCodeList.add(countryCode);
                }
            }

        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please insert city name: ");
            city = scanner.nextLine();
            city = city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();
            cityList.add(city);
            System.out.println("And city code: ");
            countryCode = scanner.nextLine();
            countryCode = countryCode.toUpperCase();
            countryCodeList.add(countryCode);
        }

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

