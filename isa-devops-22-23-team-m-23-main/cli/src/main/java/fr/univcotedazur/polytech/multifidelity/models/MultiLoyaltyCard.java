package fr.univcotedazur.polytech.multifidelity.models;

import java.util.Date;

public class MultiLoyaltyCard {
    private  double balance;
    private final int points;
    private boolean VFP;
    private int nombreTransaction;

    private Date DateLastTransaction;


    public MultiLoyaltyCard() {
        this.balance = 0;
        this.points = 0;
        this.VFP = false;
        this.nombreTransaction = 0;
        this.DateLastTransaction = null;
    }

    public double getBalance() {
        return balance;
    }

    public int getPoints() {
        return points;
    }

    public void increaseBalance(double amount){
        this.balance += amount;
    }

    public void setVFP(boolean VFP) {
        this.VFP = VFP;
    }

    @Override
    public String toString() {
        return "MultiLoyaltyCard{" +
                "balance=" + balance +
                ", points=" + points +
                ", VFP=" + VFP +
                '}';
    }

    public int getNombreTransaction() {
        return nombreTransaction;
    }

    public void setNombreTransaction(int nombreTransaction) {
        this.nombreTransaction = nombreTransaction;
    }

    public Date getDateLastTransaction() {
        return DateLastTransaction;
    }

    public void setDateLastTransaction(Date dateLastTransaction) {
        DateLastTransaction = dateLastTransaction;
    }
}
