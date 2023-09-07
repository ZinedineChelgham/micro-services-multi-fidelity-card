package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.MultiLoyaltyCard;
import fr.univcotedazur.polytech.multifidelity.interfaces.VFPManaging;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Date;

@SpringBootTest
class VFPManagerTest {

    @Autowired
    VFPManaging vfpManaging;
    MultiLoyaltyCard card;
    Customer customer;
    @BeforeEach
    public void init(){
        card = new MultiLoyaltyCard();
        customer = new Customer(card,"jojo","jo@jo","mdpjo",0);
    }
    /**
     * test que le customer incremente bien sont cumul d'achat et a bien une date
     * de derniere transaction
     */
    @Test
    public void testGoodIncrementationAndDate(){
        Date date = new Date(2023, 01, 12);
        assertFalse(vfpManaging.VFPCheck(customer,date));
        assertEquals(1,customer.getNbTransaction());
        assertEquals(date,customer.getDateOfLastTransaction());
        assertFalse(customer.isVfp());
    }

    /**
     * test que le cumul d'achat est bien remis a zero (donc egale a 1, on comptabilise l'achat en cours
     * si le client n'a pas fait d'achat depuis plus de 24h
     */
    @Test
    public void testResetCumul(){
        Date dateTest = new Date(2023, 01, 14);
        Date date = new Date(2023, 01, 12);
        customer.setDateOfLastTransaction(date);
        customer.setNbTransaction(2);
        assertFalse(vfpManaging.VFPCheck(customer,dateTest));
        assertEquals(1,customer.getNbTransaction());
        assertFalse(customer.isVfp());
    }

    /**
     * test que le customer deviens bien vfp si il avait deja 2 cumul d'achat et qu'il
     * fait un achat dans les 24h, regarde aussi que la derniere date de transaction et mise a jour
     */
    @Test
    public void testBecomeVfp(){
        Date dateTest = new Date(2023, 01, 13);
        Date date = new Date(2023, 01, 12);
        customer.setDateOfLastTransaction(date);
        customer.setNbTransaction(2);
        assertTrue(vfpManaging.VFPCheck(customer,dateTest));
        assertEquals(0,customer.getNbTransaction());//il est devenu vfp
        assertEquals(dateTest,customer.getDateOfLastTransaction());
        assertTrue(customer.isVfp());
    }

    /**
     * cas ou il est deja VFP et qu'il fait un achat dans les 168h
     */
    @Test
    public void testDateMisAJourCasVFP(){
        Date dateTest = new Date(2023, 01, 19);
        Date date = new Date(2023, 01, 12);
        customer.setDateOfLastTransaction(date);
        customer.setVFP(true);
        assertTrue(vfpManaging.VFPCheck(customer,dateTest));
        assertTrue(customer.isVfp());
        assertEquals(dateTest,customer.getDateOfLastTransaction());
    }

    /**
     * cas ou il est deja VFP et qu'il fait un achat 1 mois apres, perte de status VFP
     * et renvoie false
     * test que le customer qui est VFP perd ce status avec un achat de plus de 7 jours
     * puis test que sa date est null, et que si il refait un achat, il est toujours
     * pas VFP et a un nombre de transaction egale a 1
     */
    @Test
    public void testNotMoreVFP(){
        Date dateTest = new Date(2023, 01, 20);
        Date date = new Date(2023, 01, 12);
        customer.setDateOfLastTransaction(date);
        customer.setVFP(true);
        assertFalse(vfpManaging.VFPCheck(customer,dateTest));
        assertFalse(customer.isVfp());
        assertNull(customer.getDateOfLastTransaction());
        assertEquals(0,customer.getNbTransaction());

        Date newDate = new Date(2023, 01, 21); // premier achat
        assertFalse(vfpManaging.VFPCheck(customer,newDate));
        assertEquals(1,customer.getNbTransaction());
        assertEquals(newDate,customer.getDateOfLastTransaction());

        Date newDate2 = new Date(2023, 01, 21);// seconde achat
        assertFalse(vfpManaging.VFPCheck(customer,newDate2));
        assertEquals(2,customer.getNbTransaction());
        assertEquals(newDate2,customer.getDateOfLastTransaction());

        Date newDate3 = new Date(2023, 01, 22);// deviens VFP
        assertTrue(vfpManaging.VFPCheck(customer,newDate3));
        assertEquals(0,customer.getNbTransaction());
        assertEquals(newDate3,customer.getDateOfLastTransaction());
        assertTrue(customer.isVfp());
    }
}