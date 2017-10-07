package Model;

/**
 * Created by Markus on 7.10.2017.
 */
public class Request {
    public String City;
    public Constants.CountryCode country;
    public Constants.Units units;

    public Request(String city, Constants.CountryCode countryCode, Constants.Units requestUnits) {
        this.City = city;
        this.country = countryCode;
        this.units = requestUnits;
    }
}
