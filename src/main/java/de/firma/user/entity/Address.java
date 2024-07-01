package de.firma.user.entity;

public class Address {

    String street;
    String suite;
    String city;
    String zipcode;
    Location geo;

    public Address(String street, String suite, String city, String zipcode, Location geo) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
        this.geo = geo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Location getGeo() {
        return geo;
    }

    public void setGeo(Location geo) {
        this.geo = geo;
    }
}
