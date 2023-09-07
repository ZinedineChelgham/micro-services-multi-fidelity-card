package fr.univcotedazur.polytech.multifidelity.controllers;

import fr.univcotedazur.polytech.multifidelity.components.MoneyManager;
import fr.univcotedazur.polytech.multifidelity.controllers.dto.OfferDTO;
import fr.univcotedazur.polytech.multifidelity.controllers.dto.TransactionScanRequest;
import fr.univcotedazur.polytech.multifidelity.entities.Offer;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotEnoughMoneyException;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundException;
import fr.univcotedazur.polytech.multifidelity.exceptions.RejectedDemandException;
import fr.univcotedazur.polytech.multifidelity.interfaces.BenefitGift;
import fr.univcotedazur.polytech.multifidelity.interfaces.PayementProcessor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = UseAppController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class UseAppController {

    final static String BASE_URI = "/useApp";

    static Logger logger = org.slf4j.LoggerFactory.getLogger(UseAppController.class);

    @Autowired
    BenefitGift benefitGift;
    @Autowired
    PayementProcessor processor;

    /**
     * @param offerDto : le DTO d'une offre, n'a pas d'id, est recuperer dans le repo associer via le nom de l'offre
     * @param customerId : id d'un customer
     * @return un tableau de long avec l'id de l'offre et le nombre de points restant
     */
    @PostMapping(path = "{customerid}/payGift", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<int[]> payGift(@RequestBody OfferDTO offerDto, @PathVariable("customerid") long customerId) throws NotEnoughMoneyException, NotFoundException {
        Offer offer = new Offer(offerDto.getName(), offerDto.getCost());
        int[] result = benefitGift.payOfferGift(offer,customerId);
        logger.info("Gift paid, code de votre commande a presenter au marchant : " + result[0]+" il vous reste : "+ result[1] + " points");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }

    @PostMapping(path = "{customerid}/payMultiFi", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> payWithMultuCard(@RequestBody TransactionScanRequest request, @PathVariable("customerid") long customerId) throws NotEnoughMoneyException, NotFoundException {

        double amountDue = request.getAmount();
        Date date = request.getDate();
        double result = processor.ProcessPayement(amountDue,customerId,date);
        logger.info("Payment with multy card done, you have now : " + result + " points");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);

    }
}
