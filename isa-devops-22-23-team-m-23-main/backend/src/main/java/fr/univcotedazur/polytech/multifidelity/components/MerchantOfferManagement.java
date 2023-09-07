package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.Merchant;
import fr.univcotedazur.polytech.multifidelity.entities.Offer;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundMerchantException;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundOfferException;
import fr.univcotedazur.polytech.multifidelity.exceptions.OwnerShipException;
import fr.univcotedazur.polytech.multifidelity.exceptions.VerifFailedException;
import fr.univcotedazur.polytech.multifidelity.interfaces.CatalogManagement;
import fr.univcotedazur.polytech.multifidelity.repositories.MerchantRepository;
import fr.univcotedazur.polytech.multifidelity.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class MerchantOfferManagement implements CatalogManagement {
    private final OfferRepository offerRepository;
    private final MerchantRepository merchantRepository;

    @Autowired
    public MerchantOfferManagement(OfferRepository offerRepository, MerchantRepository merchantRepository) {
        this.offerRepository = offerRepository;
        this.merchantRepository = merchantRepository;
    }

    @Override
    public Offer addOfferToCatalog(Merchant merchant, Offer offer) throws VerifFailedException {
        if (offer == null) {
            throw new VerifFailedException("offer is null");
        }
        for (Offer o : offerRepository.findAll()) {
            if (o.getName().equals(offer.getName()) && o.getMerchant().equals(merchant)) {
                throw new VerifFailedException("offer already exists");
            }
        }

        if (!merchantRepository.existsById(merchant.getId())) {
            throw new VerifFailedException("merchant is not registered");
        }
        offer.setMerchant(merchant);
        return offerRepository.save(offer);
    }

    @Override
    public void removeOfferFromCatalog(Merchant merchant, Offer offer) throws OwnerShipException, NotFoundOfferException, NotFoundMerchantException {
        if (doesMerchantOwnsOffer(merchant, offer)) {
            offerRepository.deleteById(offer.getId());
        } else {
            throw new OwnerShipException("Merchant can't modify the offer since he's not the owner");
        }


    }

    @Override
    public Offer updateOffer(Merchant merchant, Offer offer) throws OwnerShipException, NotFoundOfferException, NotFoundMerchantException {
        if (doesMerchantOwnsOffer(merchant, offer)) {
            return offerRepository.save(offer);
        } else {
            throw new OwnerShipException("Merchant can't modify the offer since he's not the owner");
        }

    }


    private boolean doesMerchantOwnsOffer(Merchant merchant, Offer offer) throws NotFoundMerchantException, NotFoundOfferException {
        if (merchantRepository.findById(merchant.getId()).isEmpty())
            throw new NotFoundMerchantException("merchant not found");
        Offer foundOffer = offerRepository.findByName(offer.getName()).orElseThrow(() -> new NotFoundOfferException("offer not found"));
        return foundOffer.getMerchant().getId().equals(merchant.getId());
    }



    @Override
    public List<Offer> getCatalogByMerchant(String name) throws NotFoundMerchantException {
        Optional<Merchant> merchant = merchantRepository.findByName(name);
        List<Offer> offers = new ArrayList<>();
        if (merchant.isEmpty()) {
            throw new NotFoundMerchantException("Merchant not found");
        }
        for (Offer o : offerRepository.findAll()) {
            if (o.getMerchant() != null && o.getMerchant().getName().equals(name)) {
                offers.add(o);
            }
        }
        return offers;
    }
}