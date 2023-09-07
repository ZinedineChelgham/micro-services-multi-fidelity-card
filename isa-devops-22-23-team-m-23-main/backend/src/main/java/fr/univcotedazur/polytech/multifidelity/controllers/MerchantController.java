package fr.univcotedazur.polytech.multifidelity.controllers;

import fr.univcotedazur.polytech.multifidelity.controllers.dto.MerchantDTO;
import fr.univcotedazur.polytech.multifidelity.controllers.dto.TransactionScanRequest;
import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.Merchant;
import fr.univcotedazur.polytech.multifidelity.entities.Transaction;
import fr.univcotedazur.polytech.multifidelity.exceptions.*;
import fr.univcotedazur.polytech.multifidelity.interfaces.MerchantCheck;
import fr.univcotedazur.polytech.multifidelity.interfaces.ScanCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = MerchantController.BASE_URI,produces = APPLICATION_JSON_VALUE)
public class MerchantController {

    private static final Logger logger = LoggerFactory.getLogger(OfferController.class);
    public static final String BASE_URI = "/merchants";
    public static final String SCAN_URI = "{customerid}/scan";
    public static final String SCANCODE_URI = "{customerid}/code";
    @Autowired
    private MerchantCheck merchantCheck;

    @Autowired
    private ScanCard scannage;


    @PostMapping("register")
    public ResponseEntity<Merchant> registerMerchant(@RequestBody Merchant merchant) throws AlreadyExistingMerchant {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(merchantCheck.register(merchant));
    }
    @PostMapping(path = SCAN_URI, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> scanUserBill(@PathVariable("customerid") long customerId, @RequestBody TransactionScanRequest transactionScanRequest) throws RejectedDemandException, NotFoundException{
        try{
            double amount = transactionScanRequest.getAmount();
            Date date = transactionScanRequest.getDate();
            Transaction transaction = new Transaction(amount);
            int point = scannage.scan(transaction,customerId,date);
            logger.info("Scan passed ok, customer have now: " + point+" point(s)");
            return ResponseEntity.status(HttpStatus.CREATED).body(point);
        } catch (InvalideCardException e) {
            logger.error("Scan failed, error : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping(path = SCANCODE_URI, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> scanCodeCustomer(@PathVariable("customerid") long customerId, @RequestBody int code) throws NoSuchCode, NotFoundException {
        boolean offre = scannage.scanCode(code,customerId);
        String value = offre ? "accepted" : "rejected, no such code";
        logger.info("State scan : " + offre);
        return ResponseEntity.status(HttpStatus.CREATED).body(value);
    }
    @GetMapping(path = "getAll")
    public ResponseEntity<Merchant[]> getAll() {
        Merchant[] customers = merchantCheck.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customers);
    }

    public MerchantDTO convertMerchantToDto(Merchant merchant) {
        return new MerchantDTO(merchant.getName(), merchant.getAddress(), merchant.getWorkingHours());
    }

}
