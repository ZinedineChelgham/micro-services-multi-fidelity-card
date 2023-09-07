package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotVfpException;
import fr.univcotedazur.polytech.multifidelity.exceptions.ParkException;
import fr.univcotedazur.polytech.multifidelity.interfaces.CustomerFinder;
import fr.univcotedazur.polytech.multifidelity.interfaces.Parker;
import fr.univcotedazur.polytech.multifidelity.interfaces.ParkingAnswerer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class ParkingHandler implements ParkingAnswerer {
    @Autowired
    private Parker parker;


    @Override
    public String parkCustomerStatus(Customer customer, String licensePlate, String phoneNumber) {
        customer.setLicensePlate(licensePlate);
        customer.setPhoneNumber(phoneNumber);
        String res;
        boolean status;
        try{
            status = parker.parkCustomer(customer);
        }catch (Exception | NotVfpException | ParkException e){
            return e.getMessage();
        }
        if (status) res = customer.getName() + " has been parked with licence plate: " + customer.getLicencePlate();
        else res = customer.getName() + " can't be parked";
        return res;
    }

    @Override
    public String getCustomerParkRemainingTimeStatus(Customer customer) {
        String res;
        int remainingTime;
        try{
            remainingTime = parker.getCustomerParkRemainingTime(customer);
        }catch (Exception | NotVfpException e){
            return e.getMessage();
        }
        res = customer.getName() + " has " + remainingTime + " minutes remaining";
        return res;
    }
}
