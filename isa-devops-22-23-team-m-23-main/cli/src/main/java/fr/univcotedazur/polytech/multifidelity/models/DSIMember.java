package fr.univcotedazur.polytech.multifidelity.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DSIMember {

    private long id;
    private String name;
    private String email;
    private String password;
    @JsonCreator
    public DSIMember(@JsonProperty("name") String name,
                     @JsonProperty("email") String mail,
                     @JsonProperty("password") String password) {
        this.name = name;
        this.email = mail;
        this.password = password;
    }

    public long getId() {
        return id;
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
}
