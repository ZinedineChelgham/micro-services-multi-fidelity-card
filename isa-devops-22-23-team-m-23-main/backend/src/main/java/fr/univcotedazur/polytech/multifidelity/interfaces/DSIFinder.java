package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.DSIMember;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface DSIFinder {
    Optional<DSIMember> findByEmail(String name);

    Optional<DSIMember> findById(Long id);

    DSIMember[] findAll();
}
