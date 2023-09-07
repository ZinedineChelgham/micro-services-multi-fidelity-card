package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;

import java.util.Date;

public interface VFPManaging {

    boolean VFPCheck(Customer customer, Date dateDePayement);
}
