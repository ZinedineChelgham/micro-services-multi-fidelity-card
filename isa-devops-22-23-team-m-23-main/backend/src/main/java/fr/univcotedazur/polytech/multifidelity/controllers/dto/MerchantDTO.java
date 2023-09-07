package fr.univcotedazur.polytech.multifidelity.controllers.dto;

import fr.univcotedazur.polytech.multifidelity.entities.Zone;

public class MerchantDTO {
    private String name;
    private String address;
    private String workingHours;

    public MerchantDTO() {
    }

    public MerchantDTO(String name, String address, String workingHours) {
        this.name = name;
        this.address = address;
        this.workingHours = workingHours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setZone(String address) {
        this.address = address;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

}