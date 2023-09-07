package fr.univcotedazur.polytech.multifidelity.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class DSIMember {

    private long id;
    private String name;
    private String email;

    private String password;


    public DSIMember(String name, String email, String crypt) {
        this.name = name;
        this.email = email;
        this.password = crypt;
        this.id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }
}
