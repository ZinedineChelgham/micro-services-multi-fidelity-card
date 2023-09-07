package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.exceptions.NotEnoughMoneyException;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundException;

import java.util.Date;

public interface ImpactMoney {

    double gainPointsLessMoney(double amountPayed, long id, Date date) throws NotFoundException, NotEnoughMoneyException;
}
