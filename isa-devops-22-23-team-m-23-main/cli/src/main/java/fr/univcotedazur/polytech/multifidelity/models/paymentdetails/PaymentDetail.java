package fr.univcotedazur.polytech.multifidelity.models.paymentdetails;

public interface PaymentDetail {
    boolean paymentValid();

    boolean pay(double amount);

}