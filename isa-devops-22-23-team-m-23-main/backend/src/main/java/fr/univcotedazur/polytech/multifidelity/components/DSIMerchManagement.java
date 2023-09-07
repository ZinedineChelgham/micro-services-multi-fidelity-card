package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.exceptions.RejectedDemandException;
import fr.univcotedazur.polytech.multifidelity.interfaces.Verifi;
import fr.univcotedazur.polytech.multifidelity.repositories.CustomerRepository;
import fr.univcotedazur.polytech.multifidelity.repositories.MerchantRepositoryLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DSIMerchManagement implements Verifi {
    MerchantRepositoryLong merchantRepository;

    CustomerRepository customerRepository;
    @Autowired
    public DSIMerchManagement(MerchantRepositoryLong merchantRepository,CustomerRepository repository) {
        this.merchantRepository = merchantRepository;
        this.customerRepository = repository;
    }

    @Override
    public boolean check(long merchant) throws RejectedDemandException {
        if(merchantRepository.existsById(merchant))
            return true;
        else
            throw new RejectedDemandException("Merchant not found with this id : " + merchant);
    }
}
