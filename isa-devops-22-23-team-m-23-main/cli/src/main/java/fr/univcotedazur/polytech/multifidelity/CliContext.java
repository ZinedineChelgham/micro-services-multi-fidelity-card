package fr.univcotedazur.polytech.multifidelity;

import fr.univcotedazur.polytech.multifidelity.models.Customer;
import fr.univcotedazur.polytech.multifidelity.models.DSIMember;
import fr.univcotedazur.polytech.multifidelity.models.Merchant;
import fr.univcotedazur.polytech.multifidelity.models.Offer;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CliContext {

    private final Map<Long, Customer> customers;
    private final Map<Long, DSIMember> dsiMemberMap;
    private final Map<Long, Merchant> merchants;
    /**
     * les offres qui sont enregistrer dans le systeme, quand un marchant ajoute une offre,
     * on l'ajoute dans ce map, comme ca juste avec le nom de l'offre on recupere l'offre
     * pour d'autre aspect logique
     */
    private final Map<String, Offer> offers = new HashMap<>();
    private Customer currentCustomer;
    private DSIMember currentDSIMember;

    public CliContext() {

        this.customers = new HashMap<>();
        this.dsiMemberMap = new HashMap<>();
        this.merchants = new HashMap<>();
    }

    public Map<Long, Merchant> getMerchants() {
        return merchants;
    }

    public void addMerchant(Merchant merchant) {
        merchants.put(merchant.getId(), merchant);
    }

    public Map<Long, Customer> getCustomers() {
        return customers;
    }

    public void addOfferToCatalog(Offer offer, Merchant merchant) {
        merchant.getOfferCatalog().getOffers().add(offer);
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public void addOffer(Offer offer) {
        offers.put(offer.getName(), offer);
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public Logger getLogger() {
        return org.slf4j.LoggerFactory.getLogger(CliContext.class);
    }

    public Customer findByName(String name) {
        return customers.values().stream().filter(customer -> customer.getName().equals(name)).findFirst().get();
    }

    public Offer findOfferByName(String name) {
        return offers.values().stream().filter(customer -> customer.getName().equals(name)).findFirst().get();
    }

    public void log(String message) {
        getLogger().info(message);
    }

    public Map<String, Offer> getOffers() {
        return offers;
    }

    public Map<Long, DSIMember> getDsiMember() {
        return dsiMemberMap;
    }

    public void addDSI(DSIMember dsiMember) {
        dsiMemberMap.put(dsiMember.getId(), dsiMember);
    }

    public DSIMember getCurrentDSIMember() {
        return currentDSIMember;
    }

    public void setCurrentDSIMember(DSIMember currentDSIMember) {
        this.currentDSIMember = currentDSIMember;
    }
}
