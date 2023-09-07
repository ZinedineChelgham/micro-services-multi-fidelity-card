package fr.univcotedazur.polytech.multifidelity.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.TransferRequest;
import fr.univcotedazur.polytech.multifidelity.interfaces.CustomerFinder;
import fr.univcotedazur.polytech.multifidelity.interfaces.ParkingAnswerer;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.Set;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(ParkingController.class)
// start only the specified MVC front controller and no other Spring components nor the server -> Unit test of the controller
@AutoConfigureWebClient // Added to avoir error on RestTemplateBuilder missing
class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingAnswerer mockParkingAnswerer;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private CustomerFinder custFinder;


    @Test
    void parkCustomerSuccess() throws Exception {
        Customer cust = new Customer();
        cust.setId(1L);
        cust.setName("bob");
        cust.setLicensePlate("AZ-106-AA");
        cust.setPhoneNumber("05005050");

        when(custFinder.findById((any(Long.class)))).thenReturn(Optional.of(cust));
        when(mockParkingAnswerer.parkCustomerStatus(any(Customer.class), any(String.class), any(String.class)))
                .thenReturn("bob has been parked with licence plate: AZ-106-AA"
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/customers/{customerId}/parking/start-park", cust.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("AZ-106-AA, 05050505")))
                .andExpect(status().isOk()).andDo(print());

    }

    @Test
    void parkCustomerFail() throws Exception {
        Customer cust = new Customer();
        cust.setId(1L);
        cust.setName("bob");
        cust.setLicensePlate("AB-106-AA");
        cust.setPhoneNumber("05005050");

        when(custFinder.findById((any(Long.class)))).thenReturn(Optional.of(cust));
        when(mockParkingAnswerer.parkCustomerStatus(any(Customer.class), any(String.class), any(String.class)))
                .thenReturn("bob can't be parked"
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/customers/{customerId}/parking/start-park", cust.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("AB-106-AA, 05050505")))
                .andExpect(status().isOk()).andDo(print());

    }


    @Test
    void getRemainingParkTime() throws Exception {
        Customer cust = new Customer();
        cust.setId(1L);
        cust.setName("bob");
        cust.setLicensePlate("AZ-106-AA");
        cust.setPhoneNumber("05005050");

        when(custFinder.findById((any(Long.class)))).thenReturn(Optional.of(cust));
        when(mockParkingAnswerer.parkCustomerStatus(any(Customer.class), any(String.class), any(String.class)))
                .thenReturn("bob has 29 minutes remaining");

        mockMvc.perform(MockMvcRequestBuilders.get(
                "/customers/{customerId}/parking//remaining-minutes/{licensePlate}", cust.getId(), cust.getLicencePlate())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("AZ-106-AA, 05050505")))
                .andExpect(status().isOk()).andDo(print())
        ;

    }
}