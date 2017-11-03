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
        if (Files.exists(inputPath)) {
            List<String> items = Files.readAllLines(inputPath);
            city = items.get(0);
            city = city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();
            countryCode = items.get(1);
            countryCode = countryCode.toUpperCase();

        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please insert city name: ");
            city = scanner.nextLine();
            city = city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();
            System.out.println("And city code: ");
            countryCode = scanner.nextLine();
            countryCode = countryCode.toUpperCase();
        }

        Request request = new Request(city, countryCode, Constants.Units.metric);
        OpenWeatherMapApi repository = new OpenWeatherMapApi();
        CurrentWeatherReport report = repository.GetCurrentWeatherReport(request);
        ThreeDayWeatherForecast forecast = repository.GetThreeDayWeatherForecast((request));

        String output = report.toString() + System.lineSeparator() + forecast.toString();

        Path file = Paths.get("output.txt");
        Files.write(file, output.getBytes());

    }
}

