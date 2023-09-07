package fr.univcotedazur.polytech.multifidelity.cucumber;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.MultiLoyaltyCard;
import fr.univcotedazur.polytech.multifidelity.entities.Offer;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotEnoughMoneyException;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundException;
import fr.univcotedazur.polytech.multifidelity.interfaces.BenefitGift;
import fr.univcotedazur.polytech.multifidelity.repositories.CustomerRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UsePointsOfferDefinition {

    @Autowired
    BenefitGift benefitGift;
    @Autowired
    CustomerRepository repository;
    Offer offer;

    Customer customer;
    MultiLoyaltyCard card;

    long id;
    @Before()
    public void setup(){
        repository.deleteAll();
    }
    @Given("l'utilisateur a des points")
    public void customerHavePoints(){
        card = new MultiLoyaltyCard(10);
        customer = new Customer(card,"jojo","jo@jo","mdpjojo",2);
        repository.save(customer);
        id = customer.getId();
    }

    @Given("l'utilisateur a un certain nombre de points insuffisant")
    public void customerHaveNotEnoughPoints(){
        card = new MultiLoyaltyCard(1);
        customer = new Customer(card,"jojo","jo@jo","mdpjojo",2);
        repository.save(customer);
        id = customer.getId();
    }

    @When("il prend une offre")
    public void buyOfferOnlinee(){
        offer = new Offer("cafe",7);
    }
    @When("il achete sont offre en ligne")
    public void buyOfferOnline(){
        offer = new Offer("cafe",7);
    }

    @Then("il obtient sont code d'offre")
    public void userHaveCode() throws NotEnoughMoneyException, NotFoundException {
        int[] tab = benefitGift.payOfferGift(offer, id);
        customer = repository.findById(id).get();
        assertEquals(3,tab[0]);
        System.out.println(tab[1]);
        assertEquals(3,customer.getPoints());
        assertEquals(1,customer.getCodeBoughtOffer().size());
    }
    @Then("l'utilisateur ne recoit pas de code")
    public void userHaveNotCode(){
        assertThrows(NotEnoughMoneyException.class, () -> benefitGift.payOfferGift(offer, id));
    }
}
