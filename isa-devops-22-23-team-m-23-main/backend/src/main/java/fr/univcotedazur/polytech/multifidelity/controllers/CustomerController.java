package fr.univcotedazur.polytech.multifidelity.controllers;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.exceptions.BadInformationException;
import fr.univcotedazur.polytech.multifidelity.exceptions.CustomerExistsException;
import fr.univcotedazur.polytech.multifidelity.interfaces.CustomerFinder;
import fr.univcotedazur.polytech.multifidelity.interfaces.CustomerRegistration;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = CustomerController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class CustomerController {
    static final String BASE_URI = "/customers";
    static Logger logger = org.slf4j.LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    CustomerRegistration customerRegistration;

    @Autowired
    CustomerFinder customerFinder;

    @PostMapping(path = "register", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> register(@RequestBody @Valid Customer customer) throws CustomerExistsException {
        System.out.println(customer.getAddress().toString());
        Customer newCustomer = customerRegistration.register(customer);
        System.out.println(newCustomer.getAddress().toString());
        logger.info("Customer registered: " + newCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }

    @PostMapping(path = "login", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> login(@RequestBody @Valid Map<String, String> data) throws BadInformationException {
        Customer customer = customerRegistration.login(data.get("mail"), data.get("password"));
        logger.info("Customer logged in: " + customer);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customer);

    }

    @GetMapping(path = "getAll")
    public ResponseEntity<Customer[]> getAll() {
        Customer[] customers = customerFinder.findAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customers);
    }
}
