package fr.univcotedazur.polytech.multifidelity.exceptions;

public class RejectedDemandException extends Exception {

    private long id;

    public RejectedDemandException(String message) {
        super(message);
    }

    public RejectedDemandException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
