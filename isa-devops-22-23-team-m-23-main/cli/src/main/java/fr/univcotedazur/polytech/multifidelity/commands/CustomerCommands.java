package fr.univcotedazur.polytech.multifidelity.commands;

import fr.univcotedazur.polytech.multifidelity.CliContext;
import fr.univcotedazur.polytech.multifidelity.models.*;

import fr.univcotedazur.polytech.multifidelity.models.paymentdetails.PaymentCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;

@ShellComponent
public class CustomerCommands {

    public static final String BASE_URI = "/customers";

    public static final String USE_URI = "/useApp";
    public static final String MERCHANT_URI = "/merchants";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CliContext context;

    @ShellMethod("Register a new customer (register CUSTOMER_NAME CUSTOMER_MAIL CUSTOMER_PASSWORD CUSTOMER_ADDRESS CUSTOMER_CARD")
    public void register(String name, String mail, String password, String address, String card) {
        Customer customer = new Customer(name, mail, password, Address.parse(address), PaymentCard.parse(card), new MultiLoyaltyCard());
        context.getLogger().info(customer.toString());
        Customer res = restTemplate.postForObject(BASE_URI + "/register", customer, Customer.class);
        System.out.println(res);
        context.addCustomer(res);
    }

    @ShellMethod("Login as a customer (login CUSTOMER_MAIL CUSTOMER_PASSWORD)")
    public void login(String mail, String password) {
        HashMap<String, String> data = new HashMap<>();
        data.put("mail", mail);
        data.put("password", password);
        Customer res = restTemplate.postForObject(BASE_URI + "/login", data, Customer.class);
        context.setCurrentCustomer(res);
    }

    @ShellMethod("Logout as a customer (logout)")
    public void logout() {
        context.setCurrentCustomer(null);
    }

    @ShellMethod("Get the current customer (current)")
    public void current() {
        if (context.getCurrentCustomer() != null) {
            context.getLogger().info(context.getCurrentCustomer().toString());
        } else {
            context.getLogger().info("No customer logged in");
        }
    }

    @ShellMethod("Get the list of customers (customers)")
    public void customers() {
        context.getLogger().info(context.getCustomers().toString());
    }

    @ShellMethod("Get the list of customers (customers)")
    public void customers2() {
        Customer[] customers = restTemplate.getForObject(BASE_URI + "/getAll", Customer[].class);
        for (Customer customer : customers) {
            context.getLogger().info(customer.toString());
        }
    }

    @ShellMethod("Pay for a offer gift (pay_gift OFFER CUSTOMERID)")
    public void pay_gift(String offerName, long customerId){
        Offer offer = context.findOfferByName(offerName);
        int[] pointEtCode = restTemplate.postForObject(USE_URI + "/" + customerId + "/payGift", offer, int[].class);
        context.getLogger().info("Le client a payer " + pointEtCode[0] + " points et le code est " + pointEtCode[1]);
    }


    @ShellMethod("Get the list of Store related to our appli (getStores)")
    public void get_stores(){
        context.getLogger().info("Les magasins partenaire sont disponible sont : ");
        context.getMerchants().values().forEach(merchant -> context.getLogger().info("merchant name :"+merchant.getName()+"merchant Store : "+merchant.getStoreName()));
    }

    @ShellMethod("Get catalog by a merchant (get_catalog_by_merchant MERCHANT_ID)")
    public void get_catalog_by_merchant(String name) {
        Offer[] offers = restTemplate.getForObject(MERCHANT_URI + "/"+name+"/offer", Offer[].class);
        for (Offer offer : offers) {
            context.getLogger().info(offer.toString());

        }
    }
    @ShellMethod("Get the list of merchants (merchants)")
    public void get_merchants() {
        Merchant[] merchants = restTemplate.getForObject(MERCHANT_URI + "/getAll", Merchant[].class);
        for (Merchant merchant : merchants) {
            context.getLogger().info(merchant.toString());
        }
    }


    @ShellMethod("Pay with The multifidelity Card (multi-fi-pay CUSTOMER_ID AMOUNT DAY_OF_PAYEMENT MONTH_OF_PAYEMENT YEAR_OF_PAYEMENT)")
    public void multifidelity_pay(long customerId, double amount, int dayOfPay, int monthOfPay, int yearOfPay){
        Date dateOfPayement = new Date(yearOfPay,monthOfPay,dayOfPay);
        TransactionScanRequest transactionScanRequest = new TransactionScanRequest(amount,dateOfPayement);
        Double amountRestant = restTemplate.postForObject(USE_URI + "/" + customerId + "/payMultiFi", transactionScanRequest, Double.class);
        context.getLogger().info("Il vous reste " + amountRestant + " sur votre compte ");
    }

}
