package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.exceptions.NotEnoughMoneyException;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundException;
import fr.univcotedazur.polytech.multifidelity.exceptions.RejectedDemandException;
import fr.univcotedazur.polytech.multifidelity.interfaces.CustomerFinder;
import fr.univcotedazur.polytech.multifidelity.interfaces.ImpactMoney;
import fr.univcotedazur.polytech.multifidelity.interfaces.PayementProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MoneyManager implements PayementProcessor {

    private CustomerFinder customerManager;
    private ImpactMoney accountManager;

    @Autowired
    public MoneyManager(ImpactMoney accountManager,CustomerFinder customerManager) {
        this.accountManager = accountManager;
        this.customerManager = customerManager;
    }

    @Override
    public double ProcessPayement(Double amount, long customerId, Date date) throws NotEnoughMoneyException, NotFoundException {
        if(amount != null && date != null){
            if(customerManager.findById(customerId).isPresent()){
                return accountManager.gainPointsLessMoney(amount,customerId,date);
            }else throw new NotFoundException(customerId);
        }else throw new RuntimeException("Empty transaction or customer or date");
    }
}
