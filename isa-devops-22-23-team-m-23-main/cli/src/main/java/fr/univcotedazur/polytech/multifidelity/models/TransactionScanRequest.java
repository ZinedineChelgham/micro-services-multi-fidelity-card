package fr.univcotedazur.polytech.multifidelity.models;

import java.util.Date;

public class TransactionScanRequest {
    private Double amount;
    private Date date;

    public TransactionScanRequest(Double amount, Date date) {
        this.amount = amount;
        this.date = date;
    }
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
