package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.Merchant;
import fr.univcotedazur.polytech.multifidelity.entities.MultiLoyaltyCard;
import fr.univcotedazur.polytech.multifidelity.entities.Transaction;
import fr.univcotedazur.polytech.multifidelity.exceptions.*;
import fr.univcotedazur.polytech.multifidelity.interfaces.ScanCard;
import fr.univcotedazur.polytech.multifidelity.repositories.CustomerRepository;
import fr.univcotedazur.polytech.multifidelity.repositories.MerchantRepository;
import fr.univcotedazur.polytech.multifidelity.repositories.MerchantRepositoryLong;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MerchantManagementTest {
    @Autowired
    ScanCard scanCard;

    @Autowired
    CustomerRepository customerRepository;
    Customer customer;
    MultiLoyaltyCard card;
    Transaction transaction;
    Date date;

    /**
     * client a 12 pts interne, + 15 donne 27-20, il a 1 point et 7 pts interne a la fin
     */
    @Test
    public void scanTestOK() throws RejectedDemandException, InvalideCardException, NotFoundException {
        card = new MultiLoyaltyCard(0);
        date = new Date(2022,12,12);
        customer = new Customer(card,"jojo","jo@jo","mdpjojo",12);
        customerRepository.save(customer);
        long id = customer.getId();
        transaction = new Transaction(15.0);
        assertEquals(1,scanCard.scan(transaction,id,date));
        assertEquals(7, customer.getCumulatedpoints());
    }

    @Test
    public void scanCodeTestOk() throws NoSuchCode, NotFoundException {
        int code = 12000;
        card = new MultiLoyaltyCard(0);
        customer = new Customer(card,"jojo","jo@jo","mdpjojo",12);
        customer.getCodeBoughtOffer().add(12000);
        customerRepository.save(customer);
        long id = customer.getId();
        assertTrue(scanCard.scanCode(code,id));
    }

    @Test
    public void scanCodeTestNoSuch() throws NoSuchCode, NotFoundException {
        int code = 12001;
        card = new MultiLoyaltyCard(0);
        customer = new Customer(card,"jojo","jo@jo","mdpjojo",12);
        customer.getCodeBoughtOffer().add(12000);
        customerRepository.save(customer);
        long id = customer.getId();
        assertThrows(NoSuchCode.class, () -> scanCard.scanCode(code,id));
    }
}