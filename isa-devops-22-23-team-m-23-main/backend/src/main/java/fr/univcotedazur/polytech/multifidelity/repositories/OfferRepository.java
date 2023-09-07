package fr.univcotedazur.polytech.multifidelity.repositories;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repositories.BasicRepositoryImpl;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

     Optional<Offer> findByName(String name);

}
