package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.MultiLoyaltyCard;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotEnoughMoneyException;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundException;
import fr.univcotedazur.polytech.multifidelity.interfaces.ImpactAccount;
import fr.univcotedazur.polytech.multifidelity.interfaces.ImpactMoney;
import fr.univcotedazur.polytech.multifidelity.interfaces.PayPointOffer;
import fr.univcotedazur.polytech.multifidelity.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
public class AccountManagerTest {
    @Autowired
    private ImpactAccount impactAccount;

    @Autowired
    private PayPointOffer payPointOffer;
    @Autowired
    private ImpactMoney impactMoney;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;
    private MultiLoyaltyCard card;
    private Date date;
    private Date date2;

    @BeforeEach
    public void init() {
        customerRepository.deleteAll();
    }
    @Test
    public void gainPointNormalTest() throws NotFoundException {
        card =  new MultiLoyaltyCard(0);
        date = new Date(2022,12,12);
        customer = new Customer(card,"jojo","jo@jo","mdpjojo",0);
        customerRepository.save(customer);
        long id = customer.getId();
        impactAccount.gainPoints(150,id,date);
        customer = customerRepository.findById(id).get(); // tentative de correction du bug avec la bd lancer
        assertEquals(7,customer.getPoints());
        assertEquals(10,customer.getCumulatedpoints());
        assertEquals(1,customer.getNbTransaction());
        assertEquals(date,customer.getDateOfLastTransaction());
        date2 = new Date(2022,12,13);
        impactAccount.gainPoints(10,id,date2);
        customer = customerRepository.findById(id).get(); // idem
        assertEquals(8,customer.getPoints());
        assertEquals(0,customer.getCumulatedpoints());
        assertEquals(2,customer.getNbTransaction());
        assertEquals(date2,customer.getDateOfLastTransaction());
    }

    @Test
    public void payPointNormalTest() throws NotFoundException {
        card =  new MultiLoyaltyCard(7);
        customer = new Customer(card,"jojo","jo@jo","mdpjojo",2);
        customerRepository.save(customer);
        long id = customer.getId();
        int[] aa = payPointOffer.payPointDue(5,id);
        customer = customerRepository.findById(id).get();
        assertEquals(2,customer.getPoints());
        assertEquals(2,customer.getCumulatedpoints());
        assertEquals(2,aa[0]);
        System.out.println(aa[1]);
        assertEquals(-1,payPointOffer.payPointDue(5,id)[0]);
        customer = customerRepository.findById(id).get();
        assertEquals(2,customer.getPoints());
        assertEquals(2,customer.getCumulatedpoints());
    }

    @Test
    public void testRetraitCode() throws NotFoundException {
        card =  new MultiLoyaltyCard(7);
        customer = new Customer(card,"jojo","jo@jo","mdpjojo",2);
        customer.getCodeBoughtOffer().add(12000);
        customerRepository.save(customer);
        long id = customer.getId();
        assertEquals(12000,impactAccount.retrancherCodeOffreScanner(12000,id));
        assertTrue(customerRepository.findById(1L).get().getCodeBoughtOffer().isEmpty());
        assertEquals(-1,impactAccount.retrancherCodeOffreScanner(12000,id));
    }

    @Test
    public void testGainPointLessMonney() throws NotEnoughMoneyException, NotFoundException {
        card =  new MultiLoyaltyCard(0);
        card.setBalance(20);
        date = new Date(2022,12,12);
        customer = new Customer(card,"jojo","jo@jo","mdpjojo",0);
        customerRepository.save(customer);
        long id = customer.getId();
        impactMoney.gainPointsLessMoney(12,id,date);
        assertEquals(0,customerRepository.findById(id).get().getPoints());
        assertEquals(12,customerRepository.findById(id).get().getCumulatedpoints());
        assertEquals(8,customerRepository.findById(id).get().getLoyaltyCardBalance());
    }

    @Test
    public void testNoMoneyGainPointLessMonney() throws NotEnoughMoneyException, NotFoundException {
        card =  new MultiLoyaltyCard(0);
        card.setBalance(20);
        date = new Date(2022,12,12);
        customer = new Customer(card,"jojo","jo@jo","mdpjojo",0);
        customerRepository.save(customer);
        long id = customer.getId();
        assertThrows(NotEnoughMoneyException.class, () -> { impactMoney.gainPointsLessMoney(22,id,date); });
        assertEquals(0,customerRepository.findById(id).get().getPoints());
        assertEquals(0,customerRepository.findById(id).get().getCumulatedpoints());
        assertEquals(20,customerRepository.findById(id).get().getLoyaltyCardBalance());
        // Simple comment
    }
}
