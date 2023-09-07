package fr.univcotedazur.polytech.multifidelity.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;
    private int cost;
    @OneToOne
    private Merchant merchant;

    public Offer() {
    }


    public Offer(Merchant merchant,String name, String description, int cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.merchant = merchant;

    }

    public Offer(String name,String description, int cost) {
        this.name = name;
        this.cost = cost;
        this.description = description;

    }
    public Offer(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }
    public Merchant getMerchant() {
        return merchant;
    }
    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return cost == offer.cost && Objects.equals(id, offer.id) && Objects.equals(name, offer.name) && Objects.equals(description, offer.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, cost);
    }
}




