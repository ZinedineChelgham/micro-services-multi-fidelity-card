package fr.univcotedazur.polytech.multifidelity.controllers;

import fr.univcotedazur.polytech.multifidelity.controllers.dto.OfferDTO;
import fr.univcotedazur.polytech.multifidelity.entities.Merchant;
import fr.univcotedazur.polytech.multifidelity.entities.Offer;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundMerchantException;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundOfferException;
import fr.univcotedazur.polytech.multifidelity.exceptions.OwnerShipException;
import fr.univcotedazur.polytech.multifidelity.exceptions.VerifFailedException;
import fr.univcotedazur.polytech.multifidelity.interfaces.CatalogManagement;
import fr.univcotedazur.polytech.multifidelity.interfaces.MerchantCheck;
import fr.univcotedazur.polytech.multifidelity.interfaces.OfferFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController

public class CatalogController {
    private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);
    @Autowired
    private CatalogManagement catalogManagement;
    @Autowired
    private MerchantCheck merchantCheck;
    @Autowired
    private OfferFinder offerManagment;

    @PostMapping(path = MerchantController.BASE_URI + "/{merchantId}/offer", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Offer> addOffer(@PathVariable("merchantId") Long merchantId, @RequestBody() @Valid OfferDTO offerDTO) throws VerifFailedException {

            Merchant merchant = getMerchantFromId(merchantId);
            Offer offer = convertOfferDTOToOffer(offerDTO);
            logger.info("merchant name: " + merchant.getName());
            catalogManagement.addOfferToCatalog(merchant, offer);
            logger.info("the merchant associated to the offer: " + offer.getMerchant() + "offer added : " + offer.getName());
            return ResponseEntity.status(201).body(offer);

    }

    @PutMapping(path = MerchantController.BASE_URI + "/{merchantId}/offer", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Offer> updateOffer(@PathVariable("merchantId") Long merchantId, @RequestBody() @Valid OfferDTO offerDto) throws NotFoundOfferException,
            OwnerShipException, NotFoundMerchantException {

            Merchant merchant = getMerchantFromId(merchantId);
            Offer offer = convertOfferDTOToOffer(offerDto);
            offer.setMerchant(merchant);
            offer  = catalogManagement.updateOffer(merchant, offer);
            return ResponseEntity.ok().body(offer);

    }


    @GetMapping(path = MerchantController.BASE_URI + "/{name}/offer", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Offer>> getOffersByMerchant(@PathVariable("name") String name) throws NotFoundMerchantException {

        return ResponseEntity.ok().body(catalogManagement.getCatalogByMerchant(name));

    }

    @DeleteMapping(path = MerchantController.BASE_URI + "/{merchantId}/offer/{offerName}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Offer> deleteOffer(@PathVariable("merchantId") Long merchantId, @PathVariable("offerName") String offerName) throws NotFoundOfferException, OwnerShipException, NotFoundMerchantException {
            Merchant merchant = getMerchantFromId(merchantId);
            Optional<Offer> offer = offerManagment.findByName(offerName);
            if(offer.isPresent()){
                catalogManagement.removeOfferFromCatalog(merchant, offer.get());
                return ResponseEntity.ok().build();
            }
            else{
                logger.error("Offer not found");
                return ResponseEntity.badRequest().build();
            }
    }


    private Merchant getMerchantFromId(Long merchantId) {
        return merchantCheck.findById(merchantId).orElseThrow(() -> new RuntimeException("Merchant not found"));
    }

    private Offer convertOfferDTOToOffer(OfferDTO offerDTO){
        Offer offer = new Offer();
        offer.setName(offerDTO.getName());
        offer.setDescription(offerDTO.getDescription());
        offer.setCost(offerDTO.getCost());
        return offer;
    }
}
