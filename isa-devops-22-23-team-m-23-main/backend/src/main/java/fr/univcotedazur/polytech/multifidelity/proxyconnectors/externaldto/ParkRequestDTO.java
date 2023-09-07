package fr.univcotedazur.polytech.multifidelity.proxyconnectors.externaldto;

public class ParkRequestDTO {

    private String licensePlate;

    private String phoneNumber;

    public ParkRequestDTO(String licensePlate, String phoneNumber){
        this.licensePlate = licensePlate;
        this.phoneNumber = phoneNumber;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
