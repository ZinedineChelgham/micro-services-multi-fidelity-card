package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;

public interface Parking {

    boolean free30mnParkRequest(Customer customer);

    int remainingParkTime(String licensePlate);

}
