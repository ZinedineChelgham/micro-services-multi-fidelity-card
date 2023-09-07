package fr.univcotedazur.polytech.multifidelity.models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.univcotedazur.polytech.multifidelity.models.paymentdetails.PaymentCard;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private Long id;
    private final String name;
    private final String email;
    private final String password;
    private final List<Merchant> merchants = new ArrayList<>();
    private final Address address;
    private final PaymentCard paymentCard;
    private final MultiLoyaltyCard loyaltyCard;

    private String phoneNumber;

    private String licensePlate;

    private double cumulatedpoints;
    private int points;
    @JsonCreator
    public Customer(@JsonProperty("name") String name, @JsonProperty("email") String email,
                    @JsonProperty("password") String password, @JsonProperty("address") Address address,
                    @JsonProperty("paymentCard") PaymentCard paymentCard, @JsonProperty("loyaltyCard") MultiLoyaltyCard loyaltyCard) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.paymentCard = paymentCard;
        this.loyaltyCard = loyaltyCard;
    }


    public List<Merchant> getMerchants() {
        return merchants;
    }

    public Address getAddress() {
        return address;
    }

    public PaymentCard getPaymentCard() {
        return paymentCard;
    }

    public MultiLoyaltyCard getLoyaltyCard() {
        return loyaltyCard;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setVFP(boolean vfp){
        this.loyaltyCard.setVFP(vfp);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", merchants=" + merchants +
                ", address=" + address +
                ", paymentCard=" + paymentCard +
                ", loyaltyCard=" + loyaltyCard +
                '}';
    }

    public double getCumulatedpoints() {
        return cumulatedpoints;
    }

    public void setCumulatedpoints(double cumulatedpoints) {
        this.cumulatedpoints = cumulatedpoints;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Long getId() {
        return id;
    }
}
