package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;

public interface ParkingAnswerer {
    String parkCustomerStatus(Customer customer, String licensePlate, String phoneNumber);
    String getCustomerParkRemainingTimeStatus(Customer customer);

}
