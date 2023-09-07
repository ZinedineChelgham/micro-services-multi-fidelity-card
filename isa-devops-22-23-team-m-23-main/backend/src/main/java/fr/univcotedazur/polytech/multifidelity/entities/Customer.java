package fr.univcotedazur.polytech.multifidelity.entities;

import fr.univcotedazur.polytech.multifidelity.entities.paymentdetails.PaymentCard;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection
    private final List<Integer> codeBoughtOffer = new ArrayList<>();

    @ManyToMany()
    private List<Merchant> merchants = new ArrayList<>();

    @Embedded
    private Address address;

    @Embedded
    private PaymentCard paymentCard;

    @Embedded
    private MultiLoyaltyCard loyaltyCard;

    @NotBlank
    @Column
    private String name;

    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    @Column
    private String password;


    @Min(value = 0)
    private double cumulatedpoints;

    @Column(unique = true)
    private String licensePlate;
    @Column(unique = true)
    private String phoneNumber;


    /**
     * constructeur pour les tests en legende (obliger de passer name email et password a cause du notBlank ((╬ಠิ﹏ಠิ)))
     */
    public Customer(MultiLoyaltyCard card, String name, String email, String password, double cumulatedpoints) {
        this.cumulatedpoints = cumulatedpoints;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = null;
        this.paymentCard = null;
        this.loyaltyCard = card;
    }

    public Customer(String name, String email, String password, Address address, PaymentCard paymentCard, MultiLoyaltyCard loyaltyCard) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.paymentCard = paymentCard;
        this.loyaltyCard = loyaltyCard;
    }

    public Customer() {

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

    public double getLoyaltyCardBalance() {
        return loyaltyCard.getBalance();
    }

    public void increaseCardBalanceBy(double amount) {
        this.loyaltyCard.increaseBalance(amount);
    }

    public void decreaseCardBalanceBy(double amount) {
        this.loyaltyCard.decreaseBalance(amount);
    }

    public void setLoyaltyCard(MultiLoyaltyCard loyaltyCard) {
        this.loyaltyCard = loyaltyCard;
    }

    public void setPaymentCard(PaymentCard paymentCard) {
        this.paymentCard = paymentCard;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsVFP() {
        return loyaltyCard.getVFP();
    }

    public void setVFP(boolean vfp) {
        this.loyaltyCard.setVFP(vfp);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", codeBoughtOffer=" + codeBoughtOffer +
                ", merchants=" + merchants +
                ", address=" + address +
                ", paymentCard=" + paymentCard +
                ", loyaltyCard=" + loyaltyCard +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", cumulatedpoints=" + cumulatedpoints +
                '}';
    }

    public MultiLoyaltyCard getLoyaltyCard() {
        return loyaltyCard;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Double.compare(customer.cumulatedpoints, cumulatedpoints) == 0 && Objects.equals(id, customer.id) && Objects.equals(codeBoughtOffer, customer.codeBoughtOffer) && Objects.equals(merchants, customer.merchants) && Objects.equals(address, customer.address) && Objects.equals(paymentCard, customer.paymentCard) && Objects.equals(loyaltyCard, customer.loyaltyCard) && Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && Objects.equals(password, customer.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codeBoughtOffer, merchants, address, paymentCard, loyaltyCard, name, email, password, cumulatedpoints);
    }

    public int getPoints() {
        return loyaltyCard.getPoints();
    }

    public void setPoints(int points) {
        this.loyaltyCard.setPoints(points);
    }

    public void setCumulatedpoints(double cumulatedpoints) {
        this.cumulatedpoints = cumulatedpoints;
    }

    public double getCumulatedpoints() {
        return cumulatedpoints;
    }

    public List<Integer> getCodeBoughtOffer() {
        return codeBoughtOffer;
    }


    public String getLicencePlate() {
        return licensePlate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isVfp() {
        return loyaltyCard.isVFP();
    }


    public Date getDateOfLastTransaction() {
        return loyaltyCard.getDateLastTransaction();
    }

    public void setDateOfLastTransaction(Date date) {
        loyaltyCard.setDateLastTransaction(date);
    }

    public void setNbTransaction(int nb) {
        this.loyaltyCard.setNombreTransaction(nb);
    }

    public int getNbTransaction() {
        return this.loyaltyCard.getNombreTransaction();
    }
}


