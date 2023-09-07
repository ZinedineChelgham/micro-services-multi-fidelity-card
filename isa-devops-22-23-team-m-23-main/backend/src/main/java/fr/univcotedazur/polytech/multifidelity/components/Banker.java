package fr.univcotedazur.polytech.multifidelity.components;


import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.TransferRequest;
import fr.univcotedazur.polytech.multifidelity.exceptions.TransferFailedException;
import fr.univcotedazur.polytech.multifidelity.interfaces.Bank;
import fr.univcotedazur.polytech.multifidelity.interfaces.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class Banker implements Payment {

    private Bank bank;

    //TODO persist transactions

    @Autowired
    public Banker(Bank bank){
        this.bank = bank;
    }

    @Override
    public boolean pay(Customer customer, double amount) {
        return bank.pay(customer, amount);
    }

    @Override
    public TransferRequest transferFoundsToCard(Customer customer, double amount) throws TransferFailedException {
        boolean status  = bank.pay(customer, amount);
        if(!status) throw new TransferFailedException(customer.getName(), amount);
        customer.increaseCardBalanceBy(amount);
        TransferRequest transfer = new TransferRequest(amount, true);
        //TODO persist here
        return transfer;


    }
}
