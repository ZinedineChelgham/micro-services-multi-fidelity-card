package fr.univcotedazur.polytech.multifidelity.controllers;

import fr.univcotedazur.polytech.multifidelity.controllers.dto.OfferDTO;
import fr.univcotedazur.polytech.multifidelity.entities.Offer;
import fr.univcotedazur.polytech.multifidelity.interfaces.OfferFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/offers",produces = APPLICATION_JSON_VALUE)
public class OfferController {
    private static final Logger logger = LoggerFactory.getLogger(OfferController.class);
    @Autowired
    private OfferFinder offerManagment;

    @GetMapping("/{offerName}")
    public ResponseEntity<OfferDTO> getOfferByName(@PathVariable String offerName) {
        Optional<Offer> optionalOffer = offerManagment.findByName(offerName);
        if (optionalOffer.isPresent()) {
            Offer offer = optionalOffer.get();
            return ResponseEntity.ok(convertOfferToDto(offer));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Offer>> getOffers() {
        try{
            return ResponseEntity.ok().body(offerManagment.getAllOffers());
        } catch (Exception e) {
            logger.error("issue while updating offer : " + e.getMessage());

            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{offerId}")
    public ResponseEntity<OfferDTO> getOfferById(@PathVariable Long offerId) {
        Optional<Offer> optionalOffer = offerManagment.findById(offerId);
        if (optionalOffer.isPresent()) {
            Offer offer = optionalOffer.get();
            return ResponseEntity.ok(convertOfferToDto(offer));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private OfferDTO convertOfferToDto(Offer offer) {
        return new OfferDTO(offer.getName(), offer.getDescription(), offer.getCost());
    }


}