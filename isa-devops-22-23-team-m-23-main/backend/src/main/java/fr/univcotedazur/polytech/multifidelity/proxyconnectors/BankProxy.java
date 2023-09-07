package fr.univcotedazur.polytech.multifidelity.proxyconnectors;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.exceptions.TransferFailedException;
import fr.univcotedazur.polytech.multifidelity.interfaces.Bank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import fr.univcotedazur.polytech.multifidelity.proxyconnectors.externaldto.PaymentDTO;

@Component
public class BankProxy implements Bank {

    @Value("${bank.host.baseurl}")
    private String bankHostPort;

    private RestTemplate restTemplate = new RestTemplate();


    public boolean pay(Customer customer, double value) {
        try {
            ResponseEntity<PaymentDTO> result = restTemplate.postForEntity(
                    bankHostPort + "/cctransactions",
                    new PaymentDTO(customer.getPaymentCard().getCode(), value),
                    PaymentDTO.class
            );
            return (result.getStatusCode().equals(HttpStatus.CREATED));
        }
        catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                return false;
            }
            throw errorException;
        }
    }

    @Override
    public boolean transfer(Customer customer, double value){
        return this.pay(customer, value);
    }
}
