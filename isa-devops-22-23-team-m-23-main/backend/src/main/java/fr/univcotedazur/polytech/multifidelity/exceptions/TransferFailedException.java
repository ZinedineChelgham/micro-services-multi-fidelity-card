package fr.univcotedazur.polytech.multifidelity.exceptions;

import fr.univcotedazur.polytech.multifidelity.entities.Customer;

public class TransferFailedException extends Exception {

    private String name;
    private double amount;

    public TransferFailedException(String customerName, double amount){
        this.name = customerName;
        this.amount = amount;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
