package Model;

/**
 * Created by Markus on 7.10.2017.
 */
public class CurrentWeatherReport {
    public final double CurrentTemp;
    public final String City;
    public final double Longitude;
    public final double Latitude;
    public final int Date;
    public final int ResponseStatusCode;


    public CurrentWeatherReport(double currentTemp, String city, double longitude, double latitude, int date, int responseStatusCode) {
        CurrentTemp = currentTemp;
        City = city;
        Longitude = longitude;
        Latitude = latitude;
        Date = date;
        ResponseStatusCode = responseStatusCode;

    }
}
