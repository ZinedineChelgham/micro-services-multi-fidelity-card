package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotVfpException;
import fr.univcotedazur.polytech.multifidelity.exceptions.ParkException;


public interface Parker {
    boolean parkCustomer(Customer customer) throws NotVfpException, ParkException;

    int getCustomerParkRemainingTime(Customer customer) throws NotVfpException;
}
