package fr.univcotedazur.polytech.multifidelity.exceptions;

public class NotVfpException extends Throwable {
    public NotVfpException(){
        super("This customer is not a VFP");
    }
}
