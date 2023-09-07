package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.TransferRequest;
import fr.univcotedazur.polytech.multifidelity.exceptions.PayFailedException;
import fr.univcotedazur.polytech.multifidelity.exceptions.TransferFailedException;

public interface Payment {

    boolean pay(Customer customer, double amount);

    TransferRequest transferFoundsToCard(Customer customer, double amount) throws TransferFailedException;
}
