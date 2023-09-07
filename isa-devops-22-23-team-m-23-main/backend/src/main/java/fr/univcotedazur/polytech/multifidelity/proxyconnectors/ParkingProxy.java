package fr.univcotedazur.polytech.multifidelity.proxyconnectors;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.interfaces.Parking;
import fr.univcotedazur.polytech.multifidelity.proxyconnectors.externaldto.ParkRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ParkingProxy implements Parking {

    @Value("${park.host.baseurl}")
    private String parkingHostandPort;

    private RestTemplate restTemplate = new RestTemplate();


    @Override
    public boolean free30mnParkRequest(Customer customer) {
        try{
            ResponseEntity<String> res = restTemplate.postForEntity(
                    parkingHostandPort + "/start-park",
                    new ParkRequestDTO(customer.getLicencePlate(), customer.getPhoneNumber()),
                    String.class
            );
            return (res.getStatusCode().equals(HttpStatus.CREATED));
        }catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                return false;
            }
            throw errorException;
        }
    }

    @Override
    public int remainingParkTime(String licensePlate) {
        try{
            ResponseEntity<String> res = restTemplate.getForEntity(
                    parkingHostandPort + "/remaining-minutes/" + licensePlate,
                    String.class
            );
            System.out.println("REAMINING PARK TIME"  + res);
            if(res.getStatusCode().equals(HttpStatus.OK)) {
                String remainingMinutes = res.getBody();
                if (remainingMinutes != null) return Integer.parseInt(remainingMinutes);
            }
        }catch (HttpClientErrorException errorException) {
            if (errorException.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                return -1;
            }
            throw errorException;
        }
        return -1;
    }


}
