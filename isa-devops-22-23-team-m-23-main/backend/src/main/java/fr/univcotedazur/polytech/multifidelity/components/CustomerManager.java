package fr.univcotedazur.polytech.multifidelity.components;

import fr.univcotedazur.polytech.multifidelity.entities.Address;
import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.MultiLoyaltyCard;
import fr.univcotedazur.polytech.multifidelity.entities.paymentdetails.PaymentCard;
import fr.univcotedazur.polytech.multifidelity.exceptions.BadInformationException;
import fr.univcotedazur.polytech.multifidelity.exceptions.CustomerExistsException;
import fr.univcotedazur.polytech.multifidelity.interfaces.CustomerFinder;
import fr.univcotedazur.polytech.multifidelity.interfaces.CustomerRegistration;
import fr.univcotedazur.polytech.multifidelity.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class CustomerManager implements CustomerRegistration, CustomerFinder {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerManager(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Optional<Customer> findByEmail(String name) {
        return customerRepository.findCustomerByEmail(name);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer[] findAll() {
        List<Customer> customers = new ArrayList<>(customerRepository.findAll());
        return customers.toArray(new Customer[0]);
    }

    @Override
    public Customer register(String name, String mail, String password, Address address, PaymentCard card, MultiLoyaltyCard loyaltyCard) throws CustomerExistsException {
        if (findByEmail(mail).isPresent()) throw new CustomerExistsException("Customer with this email already exist");
        Customer customer = new Customer(name, mail, crypt(password), address, card, new MultiLoyaltyCard());
        if (!card.paymentValid()) {
            throw new CustomerExistsException("Payment detail is not valid");
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer register(Customer customer) throws CustomerExistsException {
        return this.register(customer.getName(), customer.getEmail(), customer.getPassword(), customer.getAddress(), customer.getPaymentCard(), customer.getLoyaltyCard());
    }

    String crypt(String password) {
        // hash password with SHA-256
        try {
            System.out.println("password = " + password);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder s = new StringBuilder();
            for (byte b : hash) s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            return s.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer login(String mail, String password) throws BadInformationException {
        Optional<Customer> customer = customerRepository.findCustomerByEmail(mail);
        if (customer.isPresent() && customer.get().getPassword().equals(crypt(password))) {
            return customer.get();
        }
        throw new BadInformationException("Bad credentials");
    }
}
