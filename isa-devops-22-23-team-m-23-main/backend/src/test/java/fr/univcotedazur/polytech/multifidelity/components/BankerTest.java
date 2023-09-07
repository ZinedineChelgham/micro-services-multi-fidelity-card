package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.MultiLoyaltyCard;
import fr.univcotedazur.polytech.multifidelity.entities.TransferRequest;
import fr.univcotedazur.polytech.multifidelity.entities.paymentdetails.PaymentCard;
import fr.univcotedazur.polytech.multifidelity.exceptions.TransferFailedException;
import fr.univcotedazur.polytech.multifidelity.interfaces.Bank;
import fr.univcotedazur.polytech.multifidelity.interfaces.Payment;
import fr.univcotedazur.polytech.multifidelity.repositories.CustomerRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class BankerTest {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    Payment banker;

    @MockBean
    private Bank mockBank;

    Customer bob;

    Customer foo;


    @BeforeEach
    public void setupContext(){
        bob = new Customer();
        bob.setPaymentCard(new PaymentCard("1234896983", "12/07", "377"));
        bob.setLoyaltyCard(new MultiLoyaltyCard());
        customerRepository.save(bob);
        foo = new Customer();
        foo.setPaymentCard(new PaymentCard("1234899433", "12/07", "377"));
        customerRepository.save(foo);

        when(mockBank.pay(eq(bob), anyDouble())).thenReturn(true);
        when(mockBank.pay(eq(foo), anyDouble())).thenReturn(false);
    }

    @AfterEach
    public void cleanUpContext() throws Exception {
        customerRepository.delete(bob);
        customerRepository.delete(foo);
    }

    @Test
    void payTest() {
      boolean status  = banker.pay(bob, 100);
      assertTrue(status);

      status = banker.pay(foo, 100);
      assertFalse(status);

    }

    @Test
    void transferTest() throws TransferFailedException {
        TransferRequest req = banker.transferFoundsToCard(bob, 100);
        assertEquals(req, new TransferRequest(100, true));
        assertEquals( 100, bob.getLoyaltyCardBalance());
    }

    @Test
    public void identifyPaymentError() {
        Assertions.assertThrows(TransferFailedException.class, () -> {
            banker.transferFoundsToCard(foo, 100);
        });
    }

}