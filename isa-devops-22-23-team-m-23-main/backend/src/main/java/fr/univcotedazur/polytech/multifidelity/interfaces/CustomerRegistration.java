package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.Address;
import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.MultiLoyaltyCard;
import fr.univcotedazur.polytech.multifidelity.entities.paymentdetails.PaymentCard;
import fr.univcotedazur.polytech.multifidelity.exceptions.BadInformationException;
import fr.univcotedazur.polytech.multifidelity.exceptions.CustomerExistsException;

public interface CustomerRegistration {

    Customer register(String name, String mail, String password, Address address, PaymentCard card, MultiLoyaltyCard loyaltyCard) throws CustomerExistsException;

    Customer register(Customer customer) throws CustomerExistsException;

    Customer login(String mail, String password) throws BadInformationException;

}
