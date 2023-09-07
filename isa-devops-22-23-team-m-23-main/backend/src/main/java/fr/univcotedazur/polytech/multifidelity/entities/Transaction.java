package fr.univcotedazur.polytech.multifidelity.entities;

import java.util.Date;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private Date date;
    private Double amount;
    private Merchant merchant;

    public Transaction(Double amount){
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }
}
