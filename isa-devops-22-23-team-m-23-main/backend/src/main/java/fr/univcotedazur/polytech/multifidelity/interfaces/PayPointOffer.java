package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundException;

public interface PayPointOffer {

    int[] payPointDue(double amountPayed, long idCustomer) throws NotFoundException;

}
