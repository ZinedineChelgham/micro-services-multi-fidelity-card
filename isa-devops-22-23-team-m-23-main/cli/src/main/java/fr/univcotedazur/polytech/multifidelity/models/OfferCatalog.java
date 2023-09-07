package fr.univcotedazur.polytech.multifidelity.models;


import java.util.ArrayList;
import java.util.List;

public class OfferCatalog {
    private Long id;
    private List<Offer> offers;
    public OfferCatalog() {
        offers = new ArrayList<>();
    }

    public List<Offer> getOffers() {
        return offers;
    }
    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
