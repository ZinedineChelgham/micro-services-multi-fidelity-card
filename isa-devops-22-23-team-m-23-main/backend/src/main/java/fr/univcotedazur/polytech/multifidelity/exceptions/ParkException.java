package fr.univcotedazur.polytech.multifidelity.exceptions;

public class ParkException extends Throwable {

    public ParkException(){
        super("A problem occurred during park, try again");
    }
}
