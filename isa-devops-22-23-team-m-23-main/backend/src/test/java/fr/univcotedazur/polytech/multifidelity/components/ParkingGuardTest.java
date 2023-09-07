package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.MultiLoyaltyCard;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotVfpException;
import fr.univcotedazur.polytech.multifidelity.exceptions.ParkException;
import fr.univcotedazur.polytech.multifidelity.exceptions.TransferFailedException;
import fr.univcotedazur.polytech.multifidelity.interfaces.Parking;
import fr.univcotedazur.polytech.multifidelity.repositories.CustomerRepository;
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
class ParkingGuardTest {

    @Autowired
    CustomerRepository customerRepository;

    @MockBean
    Parking parking;
    @Autowired
    ParkingGuard parkingGuard;
    Customer bobVfp;
    Customer foo;

    private final String validLicensePlate = "AZ-106-AA";

    @BeforeEach
    public void setupContext() {
        bobVfp = new Customer();
        bobVfp.setLoyaltyCard(new MultiLoyaltyCard());

        bobVfp.setVFP(true);
        bobVfp.setLicensePlate(validLicensePlate);
        customerRepository.save(bobVfp);

        foo = new Customer();
        foo.setLoyaltyCard(new MultiLoyaltyCard());
        foo.setVFP(false);
        foo.setLicensePlate("BY-106-AA");
        customerRepository.save(foo);

        when(parking.free30mnParkRequest(eq(bobVfp))).thenReturn(true);
        when(parking.free30mnParkRequest(eq(foo))).thenReturn(false);
    }


    @Test
    void validParkRequest() throws NotVfpException, ParkException {
        boolean status = parkingGuard.parkCustomer(bobVfp);
        assertTrue(status);
    }
    @Test
    void parkReqFromVfpButWithNotValidLP(){
        when(parking.free30mnParkRequest(eq(bobVfp))).thenReturn(false);
        Assertions.assertThrows(ParkException.class, () -> {
            parkingGuard.parkCustomer(bobVfp);
        });
    }

    @AfterEach
    public void cleanUpContext() throws Exception {
        customerRepository.delete(bobVfp);
        customerRepository.delete(foo);
    }

    @Test
    public void identifyParkError() {
        Assertions.assertThrows(NotVfpException.class, () -> {
            parkingGuard.parkCustomer(foo);
        });
    }



}