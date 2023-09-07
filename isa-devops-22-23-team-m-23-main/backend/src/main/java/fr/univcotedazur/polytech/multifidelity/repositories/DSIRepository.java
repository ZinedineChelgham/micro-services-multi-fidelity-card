package fr.univcotedazur.polytech.multifidelity.repositories;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.DSIMember;
import org.springframework.stereotype.Repository;
import repositories.BasicRepositoryImpl;

import java.util.Optional;

@Repository
public class DSIRepository extends BasicRepositoryImpl<DSIMember, Long> {

    public Optional<DSIMember> findByEmail(String mail) {
        for (DSIMember dsiMember : findAll()) {
            if (dsiMember.getEmail().equals(mail)) {
                return Optional.of(dsiMember);
            }
        }
        return Optional.empty();
    }
}
