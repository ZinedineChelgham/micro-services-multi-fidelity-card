package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.Merchant;
import fr.univcotedazur.polytech.multifidelity.entities.Transaction;
import fr.univcotedazur.polytech.multifidelity.exceptions.InvalideCardException;
import fr.univcotedazur.polytech.multifidelity.exceptions.NoSuchCode;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundException;
import fr.univcotedazur.polytech.multifidelity.exceptions.RejectedDemandException;

import java.util.Date;
import java.util.UUID;

public interface ScanCard {

    int scan (Transaction bill, long customerid, Date date) throws InvalideCardException, RejectedDemandException, NotFoundException;

    Boolean scanCode (int code, long customerid) throws NotFoundException, NoSuchCode;

}
