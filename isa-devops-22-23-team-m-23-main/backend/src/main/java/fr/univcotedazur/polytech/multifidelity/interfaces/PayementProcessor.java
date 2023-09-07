package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.exceptions.NotEnoughMoneyException;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundException;
import fr.univcotedazur.polytech.multifidelity.exceptions.RejectedDemandException;

import java.util.Date;

public interface PayementProcessor {

    double ProcessPayement(Double amount, long customerId, Date date) throws NotEnoughMoneyException, NotFoundException;
}
