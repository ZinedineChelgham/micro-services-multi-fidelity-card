package fr.univcotedazur.polytech.multifidelity.entities;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {
    String number;
    String street;
    String zipCode;
    String city;
    String country;

    public Address(String number, String street, String zipCode, String city, String country) {
        this.number = number;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public Address() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(number, address.number) && Objects.equals(street, address.street) && Objects.equals(zipCode, address.zipCode) && Objects.equals(city, address.city) && Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, street, zipCode, city, country);
    }
}
