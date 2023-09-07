package fr.univcotedazur.polytech.multifidelity.controllers;

import fr.univcotedazur.polytech.multifidelity.controllers.dto.ErrorDTO;
import fr.univcotedazur.polytech.multifidelity.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {UseAppController.class,CustomerController.class, MerchantController.class,CatalogController.class})
public class GlobalControllerAdvice {

    @ExceptionHandler({NotEnoughMoneyException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDTO handleExceptions(NotEnoughMoneyException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Not enough money");
        errorDTO.setDetails("For the Customer : " + e.getName() +
                ", your tried to purchase an amount of " + e.getPurchaseAmount()+
                " witch is lower than your current balance : " + e.getAmount() + ".");
        return errorDTO;
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleExceptions(NotFoundException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Customer not found");
        errorDTO.setDetails("The customer with id : " + e.getId() + " is not found.");
        return errorDTO;
    }

    @ExceptionHandler({CustomerExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDTO handleExceptions(CustomerExistsException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Customer registration failed");
        errorDTO.setDetails("Customer registration failed : " + e.getMessage());
        return errorDTO;
    }
    @ExceptionHandler({NotFoundMerchantException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleExceptions(NotFoundMerchantException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Merchant not found");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @ExceptionHandler({NotFoundOfferException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleExceptions(NotFoundOfferException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Offer not found");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @ExceptionHandler({BadInformationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleExceptions(BadInformationException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Customer login failed:");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @ExceptionHandler({OwnerShipException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleExceptions(OwnerShipException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Merchant can't modify the offer since he's not the owner");
        return errorDTO;
    }

    @ExceptionHandler({AlreadyExistingMerchant.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handleExceptions(AlreadyExistingMerchant e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("Merchant registration failed:");
        errorDTO.setDetails(e.getMessage());
        return errorDTO;
    }

    @ExceptionHandler({RejectedDemandException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handleExceptions(RejectedDemandException e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("demand of scan rejected");
        errorDTO.setDetails("Customer with id : "+ e.getId()+" is not registred in the system.");
        return errorDTO;
    }

    @ExceptionHandler({NoSuchCode.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handleExceptions(NoSuchCode e) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setError("The Given code does not exist");
        errorDTO.setDetails("The code that has been gived is not valide :"+e.getMessage());
        return errorDTO;
    }

}
