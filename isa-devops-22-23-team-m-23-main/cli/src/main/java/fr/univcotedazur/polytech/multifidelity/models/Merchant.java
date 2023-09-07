package fr.univcotedazur.polytech.multifidelity.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Merchant {
    private Long id;

    private String name;
    private String email;
    private String password;
    private String address;
    private String workingHours;
    private List<Customer> customers = new ArrayList<>();

    private OfferCatalog offerCatalog;
    private String storeName;

    public Merchant() {
    }

    public Merchant(@JsonProperty("name")String name, @JsonProperty("email")String email, @JsonProperty("password")String password,
                    @JsonProperty("address")String address, @JsonProperty("workingHours")String workingHours,
                    @JsonProperty("customers")List<Customer> customers, @JsonProperty("offerCatalog")OfferCatalog offerCatalog,
                    @JsonProperty("storeName")String storeName) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.workingHours = workingHours;
        this.customers = customers;
        this.offerCatalog = offerCatalog;
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setZone(String address) {
        this.address = address;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public OfferCatalog getOfferCatalog() {
        return offerCatalog;
    }

    public void setOfferCatalog(OfferCatalog offerCatalog) {
        this.offerCatalog = offerCatalog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Merchant merchant = (Merchant) o;
        return Objects.equals(id, merchant.id) && Objects.equals(name, merchant.name) && Objects.equals(email, merchant.email) && Objects.equals(password, merchant.password) && Objects.equals(address, merchant.address) && Objects.equals(workingHours, merchant.workingHours) && Objects.equals(customers, merchant.customers) && Objects.equals(offerCatalog, merchant.offerCatalog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, address, workingHours, customers, offerCatalog);
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", workingHours='" + workingHours + '\'' +
                ", customers=" + customers +
                ", offerCatalog=" + offerCatalog +
                ", storeName=" + storeName +
                '}';
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
