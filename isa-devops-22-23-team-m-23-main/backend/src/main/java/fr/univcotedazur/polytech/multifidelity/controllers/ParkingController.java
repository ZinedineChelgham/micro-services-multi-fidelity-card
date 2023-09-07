package fr.univcotedazur.polytech.multifidelity.controllers;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.interfaces.CustomerFinder;
import fr.univcotedazur.polytech.multifidelity.interfaces.ParkingAnswerer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.http.MediaType.*;


@RestController
@RequestMapping(path = CustomerController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class ParkingController {

    public static final String BASE_URI = "/{customerId}/parking";

    @Autowired
    private ParkingAnswerer parkingAnswerer;

    @Autowired
    private CustomerFinder finder;

    @PostMapping(path = BASE_URI + "/start-park", consumes = ALL_VALUE)
    public ResponseEntity<String> parkCustomer(@PathVariable("customerId")  Long customerId, @RequestBody String parkInfos) throws Exception {
        String[] infos = parkInfos.split(", ");
        System.out.println("PARK REQUEST " + Arrays.toString(infos));
        return ResponseEntity.ok().body(
                parkingAnswerer.parkCustomerStatus(getCustomer(customerId), infos[0], infos[1]) //0=licensePlate 1=phoneNumber
        );
    }

    @GetMapping(path = BASE_URI + "/remaining-minutes/{licensePlate}")
    public ResponseEntity<String> getRemainingParkTime(@PathVariable("customerId")  Long customerId, @PathVariable("licensePlate") String licensePlate) throws Exception {
        System.out.println("PARK TIME REQUEST");
        return ResponseEntity.ok().body(
                parkingAnswerer.getCustomerParkRemainingTimeStatus(getCustomer(customerId))
        );
    }

    private Customer getCustomer(Long customerId) throws fr.unice.polytech.multifidelity.exceptions.CustomerIdNotFoundException {
        Optional<Customer> cust = finder.findById(customerId);
        if (cust.isEmpty()) {
            throw new fr.unice.polytech.multifidelity.exceptions.CustomerIdNotFoundException(customerId);
        }
        return cust.get();
    }




}
