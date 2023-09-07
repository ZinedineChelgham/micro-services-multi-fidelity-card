package fr.univcotedazur.polytech.multifidelity.components;


import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.exceptions.NotVfpException;
import fr.univcotedazur.polytech.multifidelity.exceptions.ParkException;
import fr.univcotedazur.polytech.multifidelity.interfaces.Parking;
import fr.univcotedazur.polytech.multifidelity.interfaces.Parker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParkingGuard implements Parker {

    @Autowired
    private Parking parking;

    @Override
    public boolean parkCustomer(Customer customer) throws NotVfpException, ParkException {
        if(!customer.getIsVFP()) throw new NotVfpException();
        boolean status;
        status = parking.free30mnParkRequest(customer);
        if(!status) throw new ParkException();
        return true;
    }

    @Override
    public int getCustomerParkRemainingTime(Customer customer) throws NotVfpException {
        if(!customer.getIsVFP()) throw new NotVfpException();
        return parking.remainingParkTime(customer.getLicencePlate());
    }
}
