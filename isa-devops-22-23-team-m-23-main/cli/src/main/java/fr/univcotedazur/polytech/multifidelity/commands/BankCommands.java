package fr.univcotedazur.polytech.multifidelity.commands;

import fr.univcotedazur.polytech.multifidelity.CliContext;
import fr.univcotedazur.polytech.multifidelity.models.Customer;
import fr.univcotedazur.polytech.multifidelity.models.TransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@ShellComponent
public class BankCommands {
    public static final String BASE_URI = "/customers";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext context;

    @ShellMethod("Transfer money on the loyalty card (transfer-to-loyalty-card CUSTOMER_NAME AMOUNT)")
    public TransferRequest transferToLoyaltyCard(String customerName, int amount){
        Customer cust = context.findByName(customerName);
        TransferRequest req =   restTemplate.postForObject(
                getUriForCustomer(cust) + "/transfer",
                new TransferRequest(amount, false),
                TransferRequest.class
        );
        cust.getLoyaltyCard().increaseBalance(amount);
        return req;
    }

    @ShellMethod("Get the current loyalty card balance (get-customer-balance CUSTOMER_NAME")
    public String getCustomerBalance(String customerName){
        Customer cust = context.findByName(customerName);
        return restTemplate.getForObject(getUriForCustomer(cust) + "/balance", String.class);
    }




    public String getUriForCustomer(Customer customer){

        return BASE_URI + "/" + customer.getId() + "/my-card";
    }

}
