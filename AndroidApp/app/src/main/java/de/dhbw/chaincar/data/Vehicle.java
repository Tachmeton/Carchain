package de.dhbw.chaincar.data;

public class Vehicle {
    public String id;
    public String name;
    public String homeCountry;
    public String homeCity;
    public String homePostCode;
    public String homeStreet;
    public String homeStreetNumber;
    public float pricePerHour;
    public float minRentDuration;
    public float maxRentDuration;

    public Vehicle(String id, String name, String homeCountry, String homeCity, String homePostCode, String homeStreet, String homeStreetNumber, float pricePerHour, float minRentDuration, float maxRentDuration) {
        this.id = id;
        this.name = name;
        this.homeCountry = homeCountry;
        this.homeCity = homeCity;
        this.homePostCode = homePostCode;
        this.homeStreet = homeStreet;
        this.homeStreetNumber = homeStreetNumber;
        this.pricePerHour = pricePerHour;
        this.minRentDuration = minRentDuration;
        this.maxRentDuration = maxRentDuration;
    }
}
