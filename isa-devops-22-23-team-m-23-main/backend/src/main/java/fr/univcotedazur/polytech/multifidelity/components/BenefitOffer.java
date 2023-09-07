package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.Offer;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotEnoughMoneyException;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundException;
import fr.univcotedazur.polytech.multifidelity.interfaces.BenefitGift;
import fr.univcotedazur.polytech.multifidelity.interfaces.PayPointOffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BenefitOffer implements BenefitGift {

    PayPointOffer payPointOffer;

    @Autowired
    public BenefitOffer(PayPointOffer payPointOffer) {
        this.payPointOffer = payPointOffer;
    }


    /**
     * @return tableau 0 les points et 1 le code de l'offre OU -1 si pas assez d'argent
     */
    @Override
    public int[] payOfferGift(Offer offer, long idCustomer) throws NotEnoughMoneyException, NotFoundException {
        int[] pointsEtCode = payPointOffer.payPointDue(offer.getCost(), idCustomer);
        if(pointsEtCode[0] == -1) throw new NotEnoughMoneyException(" pas assez d'argent pour payer l'offre");
        else return new int[]{pointsEtCode[0],pointsEtCode[1]};
    }

}
