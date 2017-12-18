import Model.FileReader;
import Model.FileWriter;

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
    public static FileReader fileReader;

    public static void main(String[] args) throws IOException, ParseException {
        String city;
        String countryCode;

        fileReader = new FileReader();

        String inputName = "input.txt";
        Path inputPath = Paths.get(inputName);
        ArrayList<String> cityList = new ArrayList<>();
        ArrayList<String> countryCodeList = new ArrayList<>();
        if (Files.exists(inputPath)) {
            List<String> items = fileReader.fileReader(inputName);
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

        FileWriter.fileWriter(cityList, countryCodeList);

    }
}

