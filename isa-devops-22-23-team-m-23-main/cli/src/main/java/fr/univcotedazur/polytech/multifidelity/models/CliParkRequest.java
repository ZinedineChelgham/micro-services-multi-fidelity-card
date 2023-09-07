package fr.univcotedazur.polytech.multifidelity.models;

public class CliParkRequest {

    private long custId;

    private String licensePlate;

    private String phoneNumber;

    public CliParkRequest(Long id, String licensePlate, String phoneNumber){
        this.custId = id;
        this.licensePlate = licensePlate;
        this.phoneNumber = phoneNumber;
    }


    public long getId() {
        return custId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
