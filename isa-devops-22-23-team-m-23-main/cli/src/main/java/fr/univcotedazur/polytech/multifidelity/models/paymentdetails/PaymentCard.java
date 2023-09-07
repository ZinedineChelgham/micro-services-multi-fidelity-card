package fr.univcotedazur.polytech.multifidelity.models.paymentdetails;

import java.util.Arrays;

public class PaymentCard implements PaymentDetail {

    String code; String expirationDate; String cvv;

    public PaymentCard(String code, String expirationDate, String cvv) {
        this.code = code;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public PaymentCard() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public boolean paymentValid() {
        return pay(0.0);
    }

    @Override
    public boolean pay(double amount) {
        return true;
    }

    public static PaymentCard parse(String card) {
        // exemple: 1234 5678 9012 3456, 12/22, 123
        String[] parts = card.split(", ");
        System.out.println(card + " " + Arrays.toString(parts));
        return new PaymentCard(parts[0], parts[1], parts[2]);
    }

    @Override
    public String toString() {
        return "PaymentCard{" +
                "code='" + code + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}
