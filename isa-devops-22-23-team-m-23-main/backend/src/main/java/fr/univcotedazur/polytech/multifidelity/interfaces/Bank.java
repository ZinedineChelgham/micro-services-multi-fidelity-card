package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;

public interface Bank {

    boolean pay(Customer customer, double value);

    boolean transfer(Customer customer, double value);

}
