package fr.univcotedazur.polytech.multifidelity.cucumber;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.MultiLoyaltyCard;
import fr.univcotedazur.polytech.multifidelity.entities.Transaction;
import fr.univcotedazur.polytech.multifidelity.exceptions.InvalideCardException;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundException;
import fr.univcotedazur.polytech.multifidelity.exceptions.RejectedDemandException;
import fr.univcotedazur.polytech.multifidelity.interfaces.ScanCard;
import fr.univcotedazur.polytech.multifidelity.repositories.CustomerRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.platform.engine.Cucumber;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

/*@CucumberContextConfiguration
@ContextConfiguration(classes = RunCucumberConfig.class)*/
@SpringBootTest
@Transactional
public class EarnPointsDefinition {
    Transaction transaction;
    Customer customer;
    MultiLoyaltyCard card;

    @Autowired
    ScanCard scanCard;
    @Autowired
    CustomerRepository repository;

    long id;

    @Before
    public void setup(){
        repository.deleteAll();
    }

    @Given("l'utilisateur a une carte de fidélité valide")
    public void userHaveFidelityOk(){
        card = new MultiLoyaltyCard(0);
        customer = new Customer(card,"jojo","jo@jo","mdpjojo",12);
        repository.save(customer);
        id = customer.getId();
    }

    @Given("l'utilisateur a une carte de fidélité non valide")
    public void userHaveFidelityNoOk(){
        card = new MultiLoyaltyCard(0);
        customer = new Customer(card,"jojo","jo@jo","mdpjojo",12);
        //repository.save(customer,1L); client pas dans la BD
    }

    @When("il le présente au commerçant qui scan un montant 15 euro")
    public void scanForEarn(){
        transaction = new Transaction(15.0);
    }

    @Then("le compte utilisateur gagne des points")
    public void userEarnPts() throws NotFoundException, RejectedDemandException, InvalideCardException {
        assertEquals(1,scanCard.scan(transaction,id,new Date(2022, 12, 12)));
        System.out.println(repository.findById(id).get());
        assertEquals(7, repository.findById(id).get().getCumulatedpoints());
    }

    @Then("le compte utilisateur ne gagne pas de points")
    public void userNoEarnPts() throws NotFoundException, RejectedDemandException, InvalideCardException {
        try{
            Date date = new Date(2022, 12, 12);
            assertNotNull(date);
            scanCard.scan(transaction,id,date) ;
        }catch (RejectedDemandException e){
            assertTrue(true);
        }
    }
}
