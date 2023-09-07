package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.exceptions.RejectedDemandException;

public interface Verifi {

    boolean check(long merchant) throws RejectedDemandException;

}
