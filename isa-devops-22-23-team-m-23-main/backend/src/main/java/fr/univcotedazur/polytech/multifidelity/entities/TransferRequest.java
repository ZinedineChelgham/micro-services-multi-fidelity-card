package fr.univcotedazur.polytech.multifidelity.entities;


public record TransferRequest(double amount, boolean accepted) {
    public TransferRequest {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
    }
}
