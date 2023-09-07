package fr.univcotedazur.polytech.multifidelity.exceptions;

public class NotEnoughMoneyException extends Exception{

    private String name;
    private double purchaseAmount;
    private double amount;

    public NotEnoughMoneyException(String message) {
        super(message);
    }

    public NotEnoughMoneyException(String name,double purchaseAmount, double amount) {
        this.name = name;
        this.purchaseAmount = purchaseAmount;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }
}
