package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferFinder {


    Optional<Offer> findById(Long id);

    Optional<Offer> findByName(String name);

     List<Offer> getAllOffers() ;
}
