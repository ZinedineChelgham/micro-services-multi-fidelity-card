package fr.univcotedazur.polytech.multifidelity.cucumber;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.MultiLoyaltyCard;
import fr.univcotedazur.polytech.multifidelity.exceptions.NoSuchCode;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotEnoughMoneyException;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundException;
import fr.univcotedazur.polytech.multifidelity.interfaces.ScanCard;
import fr.univcotedazur.polytech.multifidelity.repositories.CustomerRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ScanOfferMerchantDefinition {

    @Autowired
    ScanCard scanCard;
    @Autowired
    CustomerRepository repository;
    Customer customer;
    MultiLoyaltyCard card;
    long id;
    @BeforeEach()
    public void setup(){
        repository.deleteAll();
    }
    @Given("Le client se presente")
    public void clientP(){
        card = new MultiLoyaltyCard(0);
        customer = new Customer(card,"jojo","jo@jo","mdpjojo",12);
    }
    @When("il a un code valide")
    public void clientCodeOK(){
        customer.getCodeBoughtOffer().add(12000);
        repository.save(customer);
        id = customer.getId();
    }

    @When("il a un code NON valide")
    public void scanCode(){
        customer.getCodeBoughtOffer().add(12001);
        repository.save(customer);
        id = customer.getId();
    }

    @Then("le marchant scan et obtient bien l'offre ne question")
    public void scanCodeOK() throws NotFoundException, NoSuchCode {
        int code = 12000;
        assertTrue(scanCard.scanCode(code, id));
    }

    @Then("le marchant scan et obtient une erreur")
    public void error(){
        int code = 12000;
        assertThrows(NoSuchCode.class, () -> scanCard.scanCode(code,customer.getId()));
    }
}

