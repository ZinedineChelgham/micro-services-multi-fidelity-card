package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.Offer;
import fr.univcotedazur.polytech.multifidelity.exceptions.AlreadyExistingOffer;
import fr.univcotedazur.polytech.multifidelity.repositories.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional

public class OfferRegisteryTest {
    @Autowired
    private OfferRegistry offerRegistry;
    @Mock
    @Autowired

    private OfferRepository offerRepositoryMocked;

    @Autowired
    private OfferRepository offerRepository;
    private static final String NAME = "Test Offer";
    private static final String DESCRIPTION = "This is a test offer";
    private static final int COST = 100;

    private static final Long ID = 1L;




    public OfferRegisteryTest() {
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        offerRegistry = new OfferRegistry(offerRepositoryMocked);
    }


    @Test
    public void testFindOfferById() {
        // Given
        Offer expectedOffer = new Offer(NAME, DESCRIPTION, COST);
        expectedOffer.setId(ID);
        Optional<Offer> expectedOptionalOffer = Optional.of(expectedOffer);

        // When
        Mockito.when(offerRepositoryMocked.findById(ID)).thenReturn(expectedOptionalOffer);
        Optional<Offer> actualOptionalOffer = offerRegistry.findById(ID);

        // Then
        assertTrue(actualOptionalOffer.isPresent());
        assertEquals(expectedOffer, actualOptionalOffer.get());
    }

    @Test
    public void testFindOfferById_NotFound() {
        // Given
        Optional<Offer> expectedOptionalOffer = Optional.empty();

        // When
        Mockito.when(offerRepositoryMocked.findById(ID)).thenReturn(expectedOptionalOffer);
        Optional<Offer> actualOptionalOffer = offerRegistry.findById(ID);

        // Then
        assertFalse(actualOptionalOffer.isPresent());
    }

    @Test
    public void testFindOfferByName() {
        // Given
        Offer expectedOffer = new Offer(NAME, DESCRIPTION, COST);
        Optional<Offer> expectedOptionalOffer = Optional.of(expectedOffer);


        // When
        Mockito.when(offerRepositoryMocked.findByName(NAME)).thenReturn(expectedOptionalOffer);
        Optional<Offer> actualOptionalOffer = offerRegistry.findByName(NAME);

        // Then
        assertTrue(actualOptionalOffer.isPresent());
        assertEquals(expectedOffer, actualOptionalOffer.get());
    }

    @Test
    public void testFindOfferByName_NotFound() {
        // Given
        Optional<Offer> expectedOptionalOffer = Optional.empty();

        // When
        Mockito.when(offerRepositoryMocked.findAll()).thenReturn(new ArrayList<>());
        Optional<Offer> actualOptionalOffer = offerRegistry.findByName(NAME);

        // Then
        assertFalse(actualOptionalOffer.isPresent());
    }
}