package Model;

/**
 * Created by Markus on 7.10.2017.
 */
public class Request {
    public String City;
    public String Country;
    public Constants.Units Units;

    public Request(String city, String countryCode, Constants.Units requestUnits) {
        this.City = city;
        this.Country = countryCode;
        this.Units = requestUnits;
    }
}
