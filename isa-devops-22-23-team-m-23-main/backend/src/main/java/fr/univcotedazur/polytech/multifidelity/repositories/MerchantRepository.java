package fr.univcotedazur.polytech.multifidelity.repositories;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repositories.BasicRepositoryImpl;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    Optional<Merchant> findByName(String name);

    Optional<Merchant> findByEmail(String email);

}
