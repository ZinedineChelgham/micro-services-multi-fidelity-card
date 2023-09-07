package fr.univcotedazur.polytech.multifidelity.repositories;

import fr.univcotedazur.polytech.multifidelity.entities.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repositories.BasicRepositoryImpl;

import java.util.UUID;
@Repository
public interface MerchantRepositoryLong extends JpaRepository<Merchant, Long> {
}
