package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.Transaction;
import fr.univcotedazur.polytech.multifidelity.exceptions.NoSuchCode;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotFoundException;
import fr.univcotedazur.polytech.multifidelity.exceptions.RejectedDemandException;
import fr.univcotedazur.polytech.multifidelity.interfaces.CustomerFinder;
import fr.univcotedazur.polytech.multifidelity.interfaces.CustomerRegistration;
import fr.univcotedazur.polytech.multifidelity.interfaces.ImpactAccount;
import fr.univcotedazur.polytech.multifidelity.interfaces.ScanCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;

@Component
@Transactional
public class MerchantManagement implements ScanCard {
    private CustomerFinder customerManager;

    private ImpactAccount impactAccount;

    @Autowired
    public MerchantManagement(CustomerFinder customerManager, ImpactAccount impactAccount){
        this.customerManager = customerManager;
        this.impactAccount = impactAccount;
    }

    @Override
    public int scan(Transaction bill, long customerid, Date date) throws RejectedDemandException, NotFoundException {
        if(bill != null && date != null){
            if(customerManager.findById(customerid).isPresent()){
                return impactAccount.gainPoints(bill.getAmount(),customerid,date);
            }else throw new RejectedDemandException(customerid);
        }else throw new RuntimeException("Empty transaction or customer or date");
    }

    @Override
    public Boolean scanCode(int code, long customerid) throws NotFoundException, NoSuchCode {
        int codeError = impactAccount.retrancherCodeOffreScanner(code,customerid);
        if(codeError == -1) throw new NoSuchCode(" code fourni n'existe pas pour ce client");
        else return true;
    }
}
