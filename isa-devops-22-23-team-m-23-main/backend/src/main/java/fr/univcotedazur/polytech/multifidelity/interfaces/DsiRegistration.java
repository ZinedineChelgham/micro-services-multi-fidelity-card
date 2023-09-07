package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.DSIMember;
import fr.univcotedazur.polytech.multifidelity.exceptions.BadInformationException;
import fr.univcotedazur.polytech.multifidelity.exceptions.VerifFailedException;

public interface DsiRegistration {

    DSIMember registerDSI(String name, String mail, String password) throws VerifFailedException;

    DSIMember loginDSI(String mail, String password) throws BadInformationException;

    DSIMember registerDSI(DSIMember dsiMember) throws VerifFailedException;
}
