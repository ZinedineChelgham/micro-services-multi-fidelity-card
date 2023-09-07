package fr.univcotedazur.polytech.multifidelity.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.polytech.multifidelity.entities.Customer;
import fr.univcotedazur.polytech.multifidelity.entities.TransferRequest;
import fr.univcotedazur.polytech.multifidelity.entities.paymentdetails.PaymentCard;
import fr.univcotedazur.polytech.multifidelity.exceptions.TransferFailedException;
import fr.univcotedazur.polytech.multifidelity.interfaces.Bank;
import fr.univcotedazur.polytech.multifidelity.interfaces.CustomerFinder;
import fr.univcotedazur.polytech.multifidelity.interfaces.Payment;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(BankController.class)
@AutoConfigureWebClient
class BankControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    Payment mockedBanker;

    @MockBean
    Bank bank;

    @MockBean
    private CustomerFinder custFinder;

    @Test
    void transferToCustomerCardSuccess() throws Exception {
        Customer cust = new Customer();
        cust.setId(1L);
        cust.setName("bob");
        cust.setPaymentCard(new PaymentCard("1896983890128755", "12/22", "123"));


        when(custFinder.findById((any(Long.class)))).thenReturn(Optional.of(cust));
        when(mockedBanker.transferFoundsToCard(any(Customer.class), any(Double.class))).thenReturn(new TransferRequest(100, true));
        when(bank.transfer(any(Customer.class), any(Double.class))).thenReturn(true);

        mockMvc.perform(post("/customers/{customerId}/my-card/transfer", cust.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new TransferRequest(100, false))))
                .andExpect(status().isOk()).andDo(print());

    }

    @Test
    void transferToCustomerCardFail() throws Exception {
        Customer cust = new Customer();
        cust.setId(1L);
        cust.setName("bob");
        cust.setPaymentCard(new PaymentCard("55551111122", "12/22", "123"));

        when(custFinder.findById((any(Long.class)))).thenReturn(Optional.of(cust));
        when(mockedBanker.transferFoundsToCard(any(Customer.class), any(Double.class))).thenReturn(new TransferRequest(100, false));
        when(bank.transfer(any(Customer.class), any(Double.class))).thenReturn(false);

        mockMvc.perform(post("/customers/{customerId}/my-card/transfer", cust.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new TransferRequest(100, false))))
                        .andExpect(status().isOk()).andDo(print());

    }


}