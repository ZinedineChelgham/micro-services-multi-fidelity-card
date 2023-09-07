package fr.univcotedazur.polytech.multifidelity.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Offer {

    private String name;
    private String description;
    private int cost;
    private Merchant merchant;

    public Offer(){}
    @JsonCreator
    public Offer(@JsonProperty("name") String name,@JsonProperty("description") String description,@JsonProperty("cost") int cost) {
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}
