package fr.univcotedazur.polytech.multifidelity.components;


import fr.univcotedazur.polytech.multifidelity.entities.Offer;
import fr.univcotedazur.polytech.multifidelity.interfaces.OfferFinder;
import fr.univcotedazur.polytech.multifidelity.repositories.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class OfferRegistry implements OfferFinder {

    private static final Logger logger = LoggerFactory.getLogger(OfferRegistry.class);

    private OfferRepository offerRepository;

    @Autowired
    public OfferRegistry(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public Optional<Offer> findByName(String name) {
        return offerRepository.findByName(name);
    }
    @Override
    public List<Offer> getAllOffers() {
        return StreamSupport.stream(offerRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public Optional<Offer> findById(Long id) {
        return offerRepository.findById(id);
    }





}
