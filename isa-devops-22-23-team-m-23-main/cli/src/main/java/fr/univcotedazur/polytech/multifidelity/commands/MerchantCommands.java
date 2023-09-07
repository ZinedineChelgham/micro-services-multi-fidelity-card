package fr.univcotedazur.polytech.multifidelity.commands;

import fr.univcotedazur.polytech.multifidelity.CliContext;
import fr.univcotedazur.polytech.multifidelity.models.Merchant;
import fr.univcotedazur.polytech.multifidelity.models.Offer;
import fr.univcotedazur.polytech.multifidelity.models.OfferCatalog;
import fr.univcotedazur.polytech.multifidelity.models.TransactionScanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;

@ShellComponent
public class MerchantCommands {

    public static final String BASE_URI = "/merchants";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext context;

    @ShellMethod("Scan a bill customer so he earn points (scan_earn CUSTOMER_ID AMOUNT DAY_OF_PAYEMENT MONTH_OF_PAYEMENT YEAR_OF_PAYEMENT)")
    public void scan_earn(long customerId, double amount, int dayOfPay, int monthOfPay, int yearOfPay){
        Date dateOfPayement = new Date(yearOfPay,monthOfPay,dayOfPay);
        TransactionScanRequest transactionScanRequest = new TransactionScanRequest(amount,dateOfPayement);
        Integer point = restTemplate.postForObject(BASE_URI + "/" + customerId + "/scan",transactionScanRequest,int.class);
        context.getLogger().info("La Transaction du client a bien été enregistrer, Vous avez actuellement " + point + " points");
        if(point != null){
            context.getCustomers().get(customerId).setPoints(point);
        }else System.out.println(" les points sont null");
    }

    @ShellMethod("Scan a code to get the offer payed by an customer (scan_offer CUSTOMERID CODE)")
    public void scan_offer(long customerId, int code){
        String offre = restTemplate.postForObject(BASE_URI + "/" + customerId + "/code",code,String.class);
        context.getLogger().info("L'offre est acheter par le client est l'offre : " + offre);
    }




    @ShellMethod("A merchant can add an offer (add_offer MERCHANT_ID OFFER_NAME OFFER_DESCRIPTION OFFER_PRICE)")
    public void add_offer( String name, String description, int price) {
        Offer offer = new Offer(name, description, price);
        context.getLogger().info(offer.toString());
        Offer res = restTemplate.postForObject("/offers/register", offer, Offer.class);
        context.addOffer(res);

    }
    @ShellMethod("Register a merchant (register_merchant MERCHANT_NAME, MERCHANT_EMAIL, MERCHANT_PASSWORD, MERCHANT_ADDRESS, MERCHANT_WORKING_HOURS, MERCHANT_STORE_NAME)")
    public void register_merchant(String name, String email, String password, String address, String workingHours, String storeName){
        Merchant merchant = new Merchant(name, email, password, address, workingHours, new ArrayList<>(), new OfferCatalog(), storeName);
        context.getLogger().info(merchant.toString());

        Merchant res = restTemplate.postForObject(BASE_URI + "/register", merchant, Merchant.class);
        context.addMerchant(res);
        context.getMerchants().keySet().forEach(System.out::println);
        context.getLogger().info("The merchant has been registered " + res.toString());
    }

}
