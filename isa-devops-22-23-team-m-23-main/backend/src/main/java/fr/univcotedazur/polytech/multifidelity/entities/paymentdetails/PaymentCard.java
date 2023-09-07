package fr.univcotedazur.polytech.multifidelity.entities.paymentdetails;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class PaymentCard implements PaymentDetail {

    String code;
    String expirationDate;
    String cvv;

    public PaymentCard(String code, String expirationDate, String cvv) {
        this.code = code;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public PaymentCard() {

    }

    @Override
    public boolean pay(double amount) {
        return true;
    }

    @Override
    public boolean paymentValid() {
        return pay(0.0);
    }

    public String getCode() {
        return code;
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

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentCard that = (PaymentCard) o;
        return Objects.equals(code, that.code) && Objects.equals(expirationDate, that.expirationDate) && Objects.equals(cvv, that.cvv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, expirationDate, cvv);
    }
}
