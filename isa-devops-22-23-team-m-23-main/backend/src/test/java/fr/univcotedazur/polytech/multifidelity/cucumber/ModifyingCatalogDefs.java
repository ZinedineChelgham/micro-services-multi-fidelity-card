package fr.univcotedazur.polytech.multifidelity.cucumber;

import fr.univcotedazur.polytech.multifidelity.entities.Merchant;
import fr.univcotedazur.polytech.multifidelity.entities.Offer;
import fr.univcotedazur.polytech.multifidelity.exceptions.*;
import fr.univcotedazur.polytech.multifidelity.interfaces.CatalogManagement;
import fr.univcotedazur.polytech.multifidelity.interfaces.MerchantCheck;
import fr.univcotedazur.polytech.multifidelity.repositories.MerchantRepository;
import fr.univcotedazur.polytech.multifidelity.repositories.OfferRepository;
import io.cucumber.java.Before;
import io.cucumber.java.fr.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ModifyingCatalogDefs {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private MerchantCheck merchantCheck;
    @Autowired
    private CatalogManagement catalogManagement;
//    @Autowired
//    private OfferCatalogRepository offerCatalogRepository;
    @Autowired
    private OfferRepository offerRepository;


    Offer offer ;
    Offer offer1;
    Offer offer2;
    Merchant validMerchant;
    Merchant validMerchant2;
    Merchant notRegistredMerchant ;

    VerifFailedException exception;
    OwnerShipException exception1;

    @Before()
    public void setup(){
        merchantRepository.deleteAll();
        offerRepository.deleteAll();

        notRegistredMerchant =new Merchant(" Jhon2", "Jhon2@gmail.com",
                "Jhon2123", " Nice", " 9-5");

    }
    @Étantdonnéque("le commerçant est enrigistré")
    public void leCommerçantEstConnecté() throws AlreadyExistingMerchant, BadInformationException {
        validMerchant = new Merchant(" Jhon", "Jhon@gmail.com",
                "Jhon123", " Nice", " 9-5");
        merchantRepository.save(validMerchant);

    }


    @Quand("le commerçant effectue les modifications souhaitées")
    public void leCommerçantEffectueLesModificationsSouhaitées() throws VerifFailedException, AlreadyExistingMerchant {
        offer = new Offer(validMerchant,"TestName", "TestDesc",69);

        catalogManagement.addOfferToCatalog(validMerchant, offer);

    }

    @Alors("le catalogue d'offres est mis à jour avec les nouvelles informations")
    public void leCatalogueDOffresEstMisÀJourAvecLesNouvellesInformations() {
        assertTrue(offerRepository.existsById(offer.getId()));
    }
    @Étantdonnéque("le commerçant n'est pas enrigistré")
    public void leCommerçantNEstPasEnrigistré() {
         notRegistredMerchant =new Merchant(" Jhon2", "Jhon2@gmail.com",
                "Jhon2123", " Nice", " 9-5");
    }



    @Quand("le commerçant tente de modifier le catalogue d'offres")
    public void leCommerçantTenteDeModifierLeCatalogueDOffres() throws AlreadyExistingMerchant {
        offer = new Offer(validMerchant,"TestName", "TestDesc",69);
        notRegistredMerchant.setId(5L);
        try {
            catalogManagement.addOfferToCatalog(notRegistredMerchant, offer);
        } catch (VerifFailedException e) {
            exception = e;
        }

    }

    @Alors("un message d{string}accès est non autorisé")
    public void unMessageDErreurEstAffichéIndiquantQueLAccèsEstNonAutorisé(String string) {
        assertNotNull(exception);
        assertEquals("merchant is not registered", exception.getMessage());

    }

    @Et("le catalogue d{string}est pas mis à jour")
    public void leCatalogueDOffresNEstPasMisÀJour(String string) {
        assertEquals(0, offerRepository.count());
    }

    @Quand("les données saisies sont invalides ou manquantes")
    public void lesDonnéesSaisiesSontInvalidesOuManquantes() throws VerifFailedException {
        try {
            catalogManagement.addOfferToCatalog(validMerchant, null);
        } catch (VerifFailedException e) {
            exception = e;
        }

    }

    @Alors("un message d'erreur est affiché indiquant que les données sont invalides")
    public void unMessageDErreurEstAffichéIndiquantQueLesDonnéesSontInvalides() {
        assertEquals("offer is null", exception.getMessage());
        assertEquals(0, offerRepository.count());
    }

    @Étantdonné(": un commerçant enregistré")
    public void unCommerçantEnregistré() {
        validMerchant2 =new Merchant(" Jhon2", "Jhon2@gmail.com",
                "Jhon2123", " Nice", " 9-5");
        merchantRepository.save(validMerchant2);
        System.out.println("this is the id of 2 : "+ validMerchant2.getId());

        validMerchant =new Merchant(" Jhon", "Jhon@gmail.com",
                "Jhon123", " Nice", " 9-5");
        merchantRepository.save(validMerchant);
        System.out.println("this is the id of 1 : "+ validMerchant.getId());
    }

    @Quand(": le commerçant essaie de mettre à jour ou de supprimer une offre qui ne lui appartient pas")
    public void leCommerçantEssaieDeMettreÀJourOuDeSupprimerUneOffreQuiNeLuiAppartientPas() throws VerifFailedException, NotFoundOfferException, NotFoundMerchantException,OwnerShipException {
        Offer offer1 = new Offer(validMerchant,"TestName", "TestDesc",69);
        catalogManagement.addOfferToCatalog(validMerchant, offer1);
        try{

            catalogManagement.updateOffer(validMerchant2,offer1);
        }
        catch (OwnerShipException e){
            exception1 = e;
        }
    }

    @Alors(": les modifications ne peuvent pas être effectuées et un message d{string}il n'est pas le propriétaire.")
    public void lesModificationsNePeuventPasÊtreEffectuéesEtUnMessageDErreurEstAffichéIndiquantQuIlNEstPasLePropriétaire(String string) {
        assertEquals("Merchant can't modify the offer since he's not the owner", exception1.getMessage());


    }

    @Lorsque(": le commerçant essaie de mettre à jour ou de supprimer une offre qui lui appartient")
    public void leCommerçantEssaieDeMettreÀJourOuDeSupprimerUneOffreQuiLuiAppartient() throws NotFoundOfferException, NotFoundMerchantException, OwnerShipException, VerifFailedException, AlreadyExistingMerchant {
        offer1 = new Offer(validMerchant,"TestName", "TestDesc",69);
        offer2 = new Offer(validMerchant,"TestName2", "TestDesc2",10);

        catalogManagement.addOfferToCatalog(validMerchant, offer1);
        catalogManagement.addOfferToCatalog(validMerchant, offer2);

        offer1.setDescription("testDesc2");
        offer1.setCost(70);

        catalogManagement.updateOffer(validMerchant,offer1);
        catalogManagement.removeOfferFromCatalog(validMerchant,offer2);

    }

    @Alors(": les modifications sont effectuées avec succès et un message de confirmation est affiché indiquant qu'il est le propriétaire.")
    public void lesModificationsSontEffectuéesAvecSuccèsEtUnMessageDeConfirmationEstAffichéIndiquantQuIlEstLePropriétaire() {
        assertEquals(1, offerRepository.count());
        assertEquals(70, offerRepository.findAll().iterator().next().getCost());

    }
}
