package fr.univcotedazur.polytech.multifidelity.commands;

import fr.univcotedazur.polytech.multifidelity.CliContext;
import fr.univcotedazur.polytech.multifidelity.models.CliParkRequest;
import fr.univcotedazur.polytech.multifidelity.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;
@ShellComponent

public class ParkingCommands {

    public static final String BASE_URI = "/customers";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext context;

    @ShellMethod("Start a Park for a customer (park CUSTOMER_NAME LICENSE_PLATE PHONE_NUMBER)")
    public void park(String customerName, String licensePlate, String phoneNumber){
        Customer cust = context.findByName(customerName);
        String res = restTemplate.postForObject(
            getUriForCustomer(cust) + "/start-park",
                licensePlate + ", " + phoneNumber, String.class
        );
        cust.setLicensePlate(licensePlate);
        cust.setPhoneNumber(phoneNumber);
        context.getLogger().info(res);

    }

    @ShellMethod("Get a Park remaining time for a customer (park-remaining-time CUSTOMER_NAME LICENSE_PLATE)")
    public void parkRemainingTime(String customerName, String licensePlate){
        Customer cust = context.findByName(customerName);
        String res = restTemplate.getForObject(getUriForCustomer(cust) + "/remaining-minutes/" + licensePlate, String.class);
        context.getLogger().info(res);
    }
    public String getUriForCustomer(Customer customer){

        return BASE_URI + "/" + customer.getId() + "/parking";
    }
}
