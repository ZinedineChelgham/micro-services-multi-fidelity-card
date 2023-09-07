package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundException;

import java.util.Date;

public interface ImpactAccount {

    int gainPoints(double amountPayed, long customer, Date date) throws NotFoundException;

    int retrancherCodeOffreScanner(int code, long customer) throws NotFoundException;

}
