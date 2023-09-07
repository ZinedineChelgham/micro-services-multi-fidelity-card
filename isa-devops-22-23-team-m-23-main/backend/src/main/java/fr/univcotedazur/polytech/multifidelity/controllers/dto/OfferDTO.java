package fr.univcotedazur.polytech.multifidelity.controllers.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class OfferDTO {
    @NotBlank(message = "name should not be blank")
    private String name;
    private String description;
    private int cost;
    private Long id;

    public OfferDTO() {}

    public OfferDTO(String name, String description, int cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "OfferDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}