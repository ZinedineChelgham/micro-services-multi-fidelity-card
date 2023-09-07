package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.Merchant;
import fr.univcotedazur.polytech.multifidelity.entities.OfferCatalog;
import fr.univcotedazur.polytech.multifidelity.entities.Zone;
import fr.univcotedazur.polytech.multifidelity.exceptions.AlreadyExistingMerchant;
import fr.univcotedazur.polytech.multifidelity.exceptions.BadInformationException;
import fr.univcotedazur.polytech.multifidelity.exceptions.RejectedDemandException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MerchantCheck {

    Optional<Merchant> findByName(String name);

    Optional<Merchant> findById(Long id);

    Merchant register(String name, String email, String password, String address, String workingHours) throws AlreadyExistingMerchant;
    Merchant register (Merchant merchant) throws AlreadyExistingMerchant;
    Merchant[] findAll();

    Merchant login(String mail, String password) throws BadInformationException;
}
