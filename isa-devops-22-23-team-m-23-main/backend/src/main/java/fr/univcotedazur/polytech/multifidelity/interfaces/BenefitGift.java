package fr.univcotedazur.polytech.multifidelity.interfaces;

import fr.univcotedazur.polytech.multifidelity.entities.Offer;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotEnoughMoneyException;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundException;

public interface BenefitGift {

    int[] payOfferGift(Offer offre, long idCustomer) throws NotFoundException, NotEnoughMoneyException;  // renvoie un code de confiramtion (id de l'offre) ou une exception
}
