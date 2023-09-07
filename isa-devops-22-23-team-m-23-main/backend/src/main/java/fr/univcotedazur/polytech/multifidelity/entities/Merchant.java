package fr.univcotedazur.polytech.multifidelity.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="merchants")
public class Merchant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String storeName;
    @Column
    private String address;
    @Column
    private String workingHours;

    @ManyToOne
    private OfferCatalog offerCatalog;

    @ManyToMany()
    private List<Customer> customers = new ArrayList<>();

    public Merchant(String name, String email, String password, String address, String workingHours, OfferCatalog offerCatalog, List<Customer> customers) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.workingHours = workingHours;
        this.offerCatalog = offerCatalog;
        this.customers = customers;
    }

    public Merchant(String name, String email, String password, String address, String workingHours) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.workingHours = workingHours;
//        this.offerCatalog = new OfferCatalog();
//        this.customers = new ArrayList<>();
    }

    public Merchant() {

    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Merchant merchant = (Merchant) o;
        return Objects.equals(id, merchant.id) && Objects.equals(name, merchant.name) && Objects.equals(email, merchant.email) && Objects.equals(password, merchant.password) && Objects.equals(address, merchant.address) && Objects.equals(workingHours, merchant.workingHours) && Objects.equals(offerCatalog, merchant.offerCatalog) && Objects.equals(customers, merchant.customers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, address, workingHours, offerCatalog, customers);
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
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
                ", offerCatalog=" + offerCatalog +
                ", customers=" + customers +
                ", storeName=" + storeName +
                '}';
    }
}
