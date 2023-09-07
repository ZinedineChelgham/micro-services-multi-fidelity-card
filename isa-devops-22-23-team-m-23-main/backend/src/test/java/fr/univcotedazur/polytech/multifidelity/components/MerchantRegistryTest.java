package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.Merchant;
import fr.univcotedazur.polytech.multifidelity.exceptions.AlreadyExistingMerchant;
import fr.univcotedazur.polytech.multifidelity.repositories.MerchantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest
@Transactional
public class MerchantRegistryTest {
    String name = "TestMerchant";
    Long merchantId = 1L;


    String address = "TestAddress";
    String workingHours = "9-5";
    @Autowired
    private MerchantRegistry merchantManagement;
    @Autowired
    @Mock
    private MerchantRepository merchantRepositoryMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        merchantManagement = new MerchantRegistry(merchantRepositoryMock);

    }
    public MerchantRegistryTest() {
    }
    @Test
    public void testFindByName() {

        Merchant merchant = new Merchant("Jhon", " Jhon@gmail.com",
                " Jhon123", " Nice", "9-5");;
        merchant.setId(merchantId);

        when(merchantRepositoryMock.findAll()).thenReturn(Collections.singletonList(merchant));

        Optional<Merchant> result = merchantManagement.findByName("Jhon");

        assertTrue(result.isPresent());
        assertEquals(merchantId, result.get().getId());
        assertEquals("Jhon", result.get().getName());
        assertEquals("9-5", result.get().getWorkingHours());
    }

    @Test
    public void testFindById(){

        Merchant expectedMerchant = new Merchant(" Jhon", " Jhon@gmail.com",
                " Jhon123", " Nice", " 9-5");;
        expectedMerchant.setId(merchantId);

        Optional<Merchant> expectedOptionalMerchant = Optional.of(expectedMerchant);

        when(merchantRepositoryMock.findById(merchantId)).thenReturn(expectedOptionalMerchant);

        Optional<Merchant> actualMerchant = merchantManagement.findById(merchantId);


        assertTrue(actualMerchant.isPresent());
        assertEquals(expectedOptionalMerchant, actualMerchant);

    }

    @Test
    public void testRegister() throws AlreadyExistingMerchant {

        merchantRepositoryMock.deleteAll();
        merchantManagement = new MerchantRegistry(merchantRepositoryMock);

        Merchant actualMerchant = merchantManagement.register("Jhon", "Jhon@gmail.com",
                "Jhon123", "Nice", "9-5");

        when(merchantRepositoryMock.findById(actualMerchant.getId())).thenReturn(Optional.of(actualMerchant));

        assertTrue(merchantRepositoryMock.findById(actualMerchant.getId()).isPresent());
        assertEquals(actualMerchant, merchantRepositoryMock.findById(actualMerchant.getId()).get());
    }


    @Test
    public void testRegisterThrowsAlreadyExistingMerchant() {
        String name = "TestMerchant";
        String email = "TestEmail";
        String password = "TestPassword";
        String workingHours = "9-5";
        String address = "TestAddress";


        when(merchantRepositoryMock.findAll()).thenReturn(Collections.singletonList(new Merchant(name,email,password, address, workingHours)));

        assertThrows(AlreadyExistingMerchant.class, () -> merchantManagement.register(name,email, password,address, workingHours));
    }

}

