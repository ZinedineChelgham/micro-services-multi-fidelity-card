package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.Merchant;
import fr.univcotedazur.polytech.multifidelity.entities.Offer;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundMerchantException;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundOfferException;
import fr.univcotedazur.polytech.multifidelity.exceptions.OwnerShipException;
import fr.univcotedazur.polytech.multifidelity.exceptions.VerifFailedException;

import java.util.List;

public interface CatalogManagement {

    Offer addOfferToCatalog(Merchant merchant, Offer offer) throws VerifFailedException;
    void removeOfferFromCatalog(Merchant merchant, Offer offer) throws OwnerShipException, NotFoundOfferException, NotFoundMerchantException;

    Offer updateOffer(Merchant merchant, Offer offer) throws OwnerShipException, NotFoundMerchantException, NotFoundOfferException;


    List<Offer> getCatalogByMerchant(String id) throws NotFoundMerchantException;

}
