package Model;

/**
 * Created by Markus on 7.10.2017.
 */
public class Request {
    public String City;
    public String country;
    public Constants.Units units;

    public Request(String city, String countryCode, Constants.Units requestUnits) {
        this.City = city;
        this.country = countryCode;
        this.units = requestUnits;
    }
}
