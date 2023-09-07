package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerFinder {
    Optional<Customer> findByEmail(String name);

    Optional<Customer> findById(Long id);

    Customer[] findAll();
}
