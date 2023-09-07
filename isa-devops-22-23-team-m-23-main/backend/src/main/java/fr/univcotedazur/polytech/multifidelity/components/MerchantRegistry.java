package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.*;
import fr.univcotedazur.polytech.multifidelity.exceptions.*;
import fr.univcotedazur.polytech.multifidelity.interfaces.ImpactAccount;
import fr.univcotedazur.polytech.multifidelity.interfaces.MerchantCheck;
import fr.univcotedazur.polytech.multifidelity.interfaces.ScanCard;
import fr.univcotedazur.polytech.multifidelity.repositories.MerchantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
@Component
public class MerchantRegistry implements MerchantCheck {

    private static final Logger logger = LoggerFactory.getLogger(OfferRegistry.class);

    private final MerchantRepository merchantRepository;


    @Autowired
    public MerchantRegistry(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }


    @Override
    public Optional<Merchant> findByName(String name) {
        return StreamSupport.stream(merchantRepository.findAll().spliterator(), false)
                .filter(merchant -> name.equals(merchant.getName())).findAny();
    }

    @Override
    public Optional<Merchant> findById(Long id) {
        return merchantRepository.findById(id);
    }

    @Override
    public Merchant register(String name, String email, String password, String address, String workingHours) throws AlreadyExistingMerchant {
        if (findByName(name).isPresent()) {
            throw new AlreadyExistingMerchant("Merchant with name " + name + " already exists");
        }

        Merchant merchant = new Merchant( name,  email,  password,  address,  workingHours);
        merchantRepository.save(merchant);
        return merchant;
    }
    @Override
    public Merchant register (Merchant merchant) throws AlreadyExistingMerchant {
        return this.register(merchant.getName(), merchant.getEmail(), merchant.getPassword(), merchant.getAddress(), merchant.getWorkingHours());
    }
    @Override
    public Merchant login(String mail, String password) throws BadInformationException {
        Optional<Merchant> merchant = merchantRepository.findByEmail(mail);
        if (merchant.isPresent() && merchant.get().getPassword().equals(password)) {
            return merchant.get();
        }
        throw new BadInformationException("Bad credentials");
    }
    @Override
    public Merchant[] findAll() {
        List<Merchant> customers = new ArrayList<>(merchantRepository.findAll());
        return customers.toArray(new Merchant[0]);
    }
}
