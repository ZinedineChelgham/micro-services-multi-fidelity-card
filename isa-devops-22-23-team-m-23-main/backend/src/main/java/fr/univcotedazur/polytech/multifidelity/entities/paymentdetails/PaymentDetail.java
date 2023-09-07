package fr.univcotedazur.polytech.multifidelity.entities.paymentdetails;

public interface PaymentDetail {
    boolean paymentValid();

    boolean pay(double amount);

}
