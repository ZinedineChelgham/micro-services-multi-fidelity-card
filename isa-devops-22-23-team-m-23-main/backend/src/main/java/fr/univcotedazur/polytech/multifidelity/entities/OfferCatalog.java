package fr.univcotedazur.polytech.multifidelity.entities;

import javax.persistence.*;
import java.util.ArrayList;
        import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "offer_catalogs")
public class OfferCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany()
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferCatalog that = (OfferCatalog) o;
        return Objects.equals(id, that.id) && Objects.equals(offers, that.offers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, offers);
    }

    @Override
    public String toString() {
        return "OfferCatalog{" +
                "id=" + id +
                ", offers=" + offers +
                '}';
    }
}
