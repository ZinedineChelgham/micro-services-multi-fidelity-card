package fr.univcotedazur.polytech.multifidelity.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class MultiLoyaltyCard {


    private double balance;
    private int points;
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

    public MultiLoyaltyCard(int points){
        this.balance = 0;
        this.points = points;
        this.VFP = false;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void increaseBalance(double amount){
        this.balance += amount;
    }
    public void decreaseBalance(double amount){
        this.balance -= amount;
    }

    public boolean getVFP() {
        return VFP;
    }

    @Override
    public String toString() {
        return "MultiLoyaltyCard{" +
                "balance=" + balance +
                ", points=" + points +
                ", VFP=" + VFP +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiLoyaltyCard that = (MultiLoyaltyCard) o;
        return Double.compare(that.balance, balance) == 0 && points == that.points && VFP == that.VFP;
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, points, VFP);
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

    public void setDateLastTransaction(Date VFPDate) {
        this.DateLastTransaction = VFPDate;
    }

    public boolean isVFP() {
        return VFP;
    }
    public void setVFP(boolean VFP) {
        this.VFP = VFP;
    }
}
