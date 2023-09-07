package fr.univcotedazur.polytech.multifidelity.controllers;

import fr.unice.polytech.multifidelity.exceptions.CustomerIdNotFoundException;
import fr.univcotedazur.polytech.multifidelity.components.Banker;
import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.TransferRequest;
import fr.univcotedazur.polytech.multifidelity.interfaces.CustomerFinder;
import fr.univcotedazur.polytech.multifidelity.interfaces.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = CustomerController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class BankController {

    public static final String BASE_URI = "/{customerId}/my-card";

    @Autowired
    private Payment banker;

    @Autowired
    private CustomerFinder finder;

    @PostMapping(path = BASE_URI + "/transfer", consumes = APPLICATION_JSON_VALUE)
     public ResponseEntity<TransferRequest> transferToCustomerCard(@PathVariable("customerId")  Long customerId, @RequestBody @Valid TransferRequest transferReq) throws Exception {
        return ResponseEntity.ok(banker.transferFoundsToCard(getCustomer(customerId), transferReq.amount()));
    }

    @GetMapping(path = BASE_URI + "/balance")
    public ResponseEntity<String> getCustomerBalance(@PathVariable("customerId") Long customerId) throws Exception {
        Customer cust = getCustomer(customerId);
        return ResponseEntity.ok().body(
                "Customer " + cust.getName() + " has " +  cust.getLoyaltyCardBalance() + "€ in his card"
        );
    }




    private Customer getCustomer(Long customerId) throws CustomerIdNotFoundException {
        Optional<Customer> cust = finder.findById(customerId);
        if (cust.isEmpty()) {
            throw new CustomerIdNotFoundException(customerId);
        }
        return cust.get();
    }




}
