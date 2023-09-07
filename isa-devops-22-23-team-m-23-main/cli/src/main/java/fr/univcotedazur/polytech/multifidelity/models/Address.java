package fr.univcotedazur.polytech.multifidelity.models;

import java.util.Arrays;

public class Address {
    String number; String street; String zipCode; String city; String country;

    public Address() {
    }

    public Address(String number, String street, String zipCode, String city, String country) {
        this.number = number;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public static Address parse(String address) {
        // exemple: 4, rue de la paix, 75000, Paris, France
        String[] parts = address.split(", ");
        System.out.println("Address parse (" + address + ") " + Arrays.toString(parts));
        return new Address(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    @Override
    public String toString() {
        return "Address{" +
                "number='" + number + '\'' +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}
